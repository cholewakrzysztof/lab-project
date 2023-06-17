package pl.edu.pwr.student.UI;

/**
 * Represents the state of the {@link pl.edu.pwr.student.UI.Canvas}.
 */
public abstract class CanvasState {
    /**
     * The state of the canvas.
     */
    private static int state = 0;

    /**
     * The last state of the canvas.
     */
    private static int lastState = 0;

    /**
     * Sets state of the {@link pl.edu.pwr.student.UI.Canvas}
     * @param state state
     */
    public static void setState(int state) {
        lastState = CanvasState.state;
        CanvasState.state = state;
    }

    /**
     * Gets  of the {@link pl.edu.pwr.student.UI.Canvas}
     * @return state
     */
    public static int getState() {
        return state;
    }

    /**
     * Brings back last state of the {@link pl.edu.pwr.student.UI.Canvas}
     */
    public static void lastState() {
        state = CanvasState.lastState;
    }

    /**
     * Enum of states
     * It can have the following values:
     * 0 - interacting with elements
     * 1 - creating new elements
     * 2 - adding or removing a new output
     * 3 - deleting elements
     * 4 - connecting elements.
     */
    public static class States {
        public static final int INTERACTING = 0;
        public static final int CREATING = 1;
        public static final int OUTPUT = 2;
        public static final int DELETING = 3;
        public static final int CONNECTING = 4;
    }
}
