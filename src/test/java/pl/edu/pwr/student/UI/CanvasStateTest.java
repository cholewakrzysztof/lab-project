package pl.edu.pwr.student.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasStateTest {

    /**
     * Set CanvasState to default configuration
     */
    @BeforeEach
    void setCanvasState(){
        CanvasState.setState(CanvasState.State.INTERACTING);
    }

    /**
     * Test changing state
     */
    @Test
    void setState() {
        CanvasState.setState(CanvasState.State.OUTPUT);
        assertTrue(CanvasState.State.OUTPUT==CanvasState.getState());
    }

    /**
     * Test getting state
     */
    @Test
    void getState() {
        assertTrue(CanvasState.State.INTERACTING==CanvasState.getState());
    }

    /**
     * Test setting last state
     */
    @Test
    void lastState() {
        CanvasState.setState(CanvasState.State.OUTPUT);
        CanvasState.lastState();
        assertTrue(CanvasState.State.INTERACTING==CanvasState.getState());
    }
}