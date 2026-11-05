package input;

import java.util.List;

/** Holds the shared input text and stop words for the exercises. */
public final class TextInput {

    /** Text processed by each tokenizer. */
    public static final String TEXT = "Today is sunny. She is a sunny girl. To be or not to be. She is in Berlin today. Sunny Berlin! Berlin is always exciting!";

    /** Stop words removed in the stop-filter and analyzer exercises. */
    public static final List<String> STOP_WORDS = List.of("was", "is", "in", "to", "be");

    private TextInput() {
    }
}
