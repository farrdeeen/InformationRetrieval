import input.TextInput;
import output.TokenPrinter;
import tokenizer.TokenizerTasks;

import java.io.IOException;

/** Runs the three Lucene tokenization exercises. */
public class P01 {

    /** Runs each tokenization task and prints its tokens. */
    public static void main(String[] args) throws IOException {
        TokenPrinter.print("a) StandardTokenizer", TokenizerTasks.standardTokenizer(TextInput.TEXT));
        TokenPrinter.print("a) WhitespaceTokenizer", TokenizerTasks.whitespaceTokenizer(TextInput.TEXT));
        TokenPrinter.print("b) StandardTokenizer + StopFilter",
                TokenizerTasks.standardWithStopFilter(TextInput.TEXT, TextInput.STOP_WORDS));
        TokenPrinter.print("c) Custom Analyzer",
                TokenizerTasks.analyze(TextInput.TEXT, TextInput.STOP_WORDS));
    }
}
