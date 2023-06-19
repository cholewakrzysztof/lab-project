package pl.edu.pwr.student.UI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasStateTest {

    @Test
    void setState() {
        CanvasState.setState(1);
        assertTrue(1==CanvasState.getState());
    }

    @Test
    void getState() {
        assertTrue(0==CanvasState.getState());
    }

    @Test
    void lastState() {
        CanvasState.setState(1);
        CanvasState.lastState();
        assertTrue(0==CanvasState.getState());
    }
}