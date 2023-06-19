package pl.edu.pwr.student.UI;

/**
 * Represents the state of the {@link pl.edu.pwr.student.UI.Canvas}.
 */
public abstract class CanvasState {
    /**
     * The state of the canvas.
     */
    private static State state = State.INTERACTING;

    /**
     * The last state of the canvas.
     */
    private static State lastState = State.INTERACTING;

    /**
     * Sets state of the {@link pl.edu.pwr.student.UI.Canvas}
     * @param state state
     */
    public static void setState(State state) {
        lastState = CanvasState.state;
        CanvasState.state = state;
    }

    /**
     * Gets  of the {@link pl.edu.pwr.student.UI.Canvas}
     * @return state
     */
    public static State getState() {
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
    public enum State {
        INTERACTING,
        CREATING,
        OUTPUT,
        DELETING,
        CONNECTING
    }
}
