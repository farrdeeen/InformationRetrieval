package tokenizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/** Provides the tokenizer and analyzer pipelines used by the exercises. */
public final class TokenizerTasks {

    private TokenizerTasks() {
    }

    /** Tokenizes text with Lucene's standard tokenizer. */
    public static List<String> standardTokenizer(String text) throws IOException {
        StandardTokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader(text));
        try (tokenizer) {
            return collect(tokenizer);
        }
    }

    /** Splits text on whitespace without stripping attached punctuation. */
    public static List<String> whitespaceTokenizer(String text) throws IOException {
        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
        tokenizer.setReader(new StringReader(text));
        try (tokenizer) {
            return collect(tokenizer);
        }
    }

    /** Applies a case-sensitive stop filter after standard tokenization. */
    public static List<String> standardWithStopFilter(String text, List<String> stopWords) throws IOException {
        StandardTokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader(text));
        CharArraySet stopSet = new CharArraySet(stopWords, false);
        try (TokenStream stream = new StopFilter(tokenizer, stopSet)) {
            return collect(stream);
        }
    }

    /** Builds a lowercase, case-insensitive stop-word, Porter-stemming analyzer. */
    public static Analyzer buildAnalyzer(List<String> stopWords) {
        CharArraySet stopSet = new CharArraySet(stopWords, true);
        return new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                Tokenizer source = new StandardTokenizer();
                // Lowercase first so the case-insensitive stop set sees consistent terms.
                TokenStream chain = new LowerCaseFilter(source);
                // Remove stop words before stemming so filtering matches the original words.
                chain = new StopFilter(chain, stopSet);
                // Stem the remaining normalized terms as the final transformation.
                chain = new PorterStemFilter(chain);
                return new TokenStreamComponents(source, chain);
            }
        };
    }

    /** Runs text through the configured analyzer and returns its terms. */
    public static List<String> analyze(String text, List<String> stopWords) throws IOException {
        try (Analyzer analyzer = buildAnalyzer(stopWords);
             TokenStream stream = analyzer.tokenStream("content", text)) {
            return collect(stream);
        }
    }

    /** Collects terms from a stream after resetting it; the caller owns its closure. */
    private static List<String> collect(TokenStream stream) throws IOException {
        CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
        List<String> tokens = new ArrayList<>();
        stream.reset();
        while (stream.incrementToken()) {
            tokens.add(term.toString());
        }
        stream.end();
        return tokens;
    }
}
