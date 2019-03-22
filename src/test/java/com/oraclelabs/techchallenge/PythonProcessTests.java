package com.oraclelabs.techchallenge;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.oraclelabs.techchallenge.controller.PythonController;
import com.oraclelabs.techchallenge.core.PythonProcess;

@WebMvcTest(PythonController.class)
public class PythonProcessTests {

	PythonProcess process;

	private final String codeWithOutput = "print(\"just printing\")";
	private final String codeWithNoOutput = "a = 9";
	private final String codeWithErrOutput = "print(b)";

	@Before
	public void init() {

		process = new PythonProcess();
	}

	@Test
	public void initProcessTest() {

		try {

			process.init();
			assertTrue("(y)", true);

		} catch (Exception e) {
			assertFalse("An exception has occurred.", false);
		}
	}

	@Test
	public void runCodeWithOutputTest() {

		try {

			String output = process.runCode(codeWithOutput);
			assertEquals("Something wrong's happened!", "just printing", output);

		} catch (Exception e) {
			assertFalse("An exception has occurred", false);
		}
	}

	// Traceback

	@Test
	public void runCodeWithErrOutputTest() {

		try {

			String output = process.runCode(codeWithErrOutput);

			assertTrue("Something wrong's happened!", output.startsWith("Traceback"));

		} catch (Exception e) {
			assertFalse("An exception has occurred", false);
		}
	}

	@Test
	public void runCodeWithNoOutputTest() {

		try {

			String output = process.runCode(codeWithNoOutput);
			assertEquals("Something wrong's happened!", "", output);

		} catch (Exception e) {
			assertFalse("An exception has occurred", false);
		}
	}
}
