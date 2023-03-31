package pl.edu.pwr.student.Tests;

import static org.junit.jupiter.api.Assertions.*;
import pl.edu.pwr.student.Gates.BasicGates.AND;

import org.junit.jupiter.api.Test;

class ANDTest {

	@Test
	void firstTest() {
		AND gate = new AND();
		assertTrue(gate.getState());
	}

}
