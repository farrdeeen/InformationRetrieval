package output;

import java.util.List;

/** Prints token lists in the format used by the exercise. */
public final class TokenPrinter {

    private TokenPrinter() {
    }

    /** Prints a label, token count, token list, and a blank line. */
    public static void print(String label, List<String> tokens) {
        System.out.println(label + " (" + tokens.size() + " tokens):");
        System.out.println(tokens);
        System.out.println();
    }
}
