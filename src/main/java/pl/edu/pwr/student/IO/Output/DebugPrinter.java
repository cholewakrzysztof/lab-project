package pl.edu.pwr.student.IO.Output;

/**
 * Debug class that prints a console log every time its input changes state.
 * Extends {@link BasicReceiver}
 */
public class DebugPrinter extends BasicReceiver {
    /**
     * Creates a new object of this class and gives it the names that's printed along with input state changes.
     * @param name text to print along with the new state of this class's input
     */
    public DebugPrinter(String name) {
        this.name = name;
    }

    /**
     * Text to print along with the new state of this class's input.
     */
    private final String name;

    /**
     * Method that is called every time the state of this element's input changes.
     * It prints "{the name of this element} state changed to: {new state of this element's input}".
     */
    @Override
    protected void react() {
        System.out.println(name + " state changed to: " + state);
    }
}
