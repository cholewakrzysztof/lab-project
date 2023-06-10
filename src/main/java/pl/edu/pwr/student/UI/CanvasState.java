package pl.edu.pwr.student.UI;

public abstract class CanvasState {
    /**
     * The state of the canvas. It can have the following values:
     * 0 - interacting with elements
     * 1 - creating new elements
     * 2 - adding or removing a new output
     * 3 - deleting elements
     * 4 - connecting elements.
     */
    private static int state = 0;

    /**
     * The last state of the canvas.
     */
    private static int lastState = 0;


    /**
     * Sets state of the canvas
     * @param state state
     */
    public static void setState(int state) {
        lastState = CanvasState.state;
        CanvasState.state = state;
    }

    /**
     * Gets state of the canvas
     * @return state
     */
    public static int getState() {
        return state;
    }

    public static void lastState() {
        state = CanvasState.lastState;
    }
}
