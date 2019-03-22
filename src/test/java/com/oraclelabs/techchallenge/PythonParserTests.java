package com.oraclelabs.techchallenge;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.constraints.AssertFalse;

import static org.junit.Assert.assertThat;

import com.oraclelabs.techchallenge.core.Code;
import com.oraclelabs.techchallenge.core.PythonParser;
import com.oraclelabs.techchallenge.core.PythonSession;
import com.oraclelabs.techchallenge.core.ServerSessions;
import com.oraclelabs.techchallenge.exception.UnparsableCodeException;
import com.oraclelabs.techchallenge.exception.UnsupportedInterpreterException;

@SpringBootTest
public class PythonParserTests {

	private final String sessionId = "FB2E77D47A0479900504CB3AB4A1F626D174D2D";

	private final Code parsableCode = new Code("%python print(\"just testing\")");
	private final Code unparsableCode = new Code("%pythUnparsableCode");
	private final Code unsupportedInterpreteCode = new Code("%perl print \"just testing\"");

	@Test
	public void parse() {

		try {
			String codeToRun = PythonParser.parse(parsableCode.getCode());
			assertEquals(codeToRun, "print(\"just testing\")");
		} catch (Exception e) {
			assertFalse("An exception has occurred", false);
		}
	}

	@Test
	public void parseUnparsableCode() {

		try {
			PythonParser.parse(unparsableCode.getCode());
		} catch (UnparsableCodeException e) {
			assertTrue("unparsable parsableCode.", true);
		} catch (UnsupportedInterpreterException e) {
			assertFalse("An exception has occurred", false);
		}
	}

	@Test
	public void parseUnsupportedInterpreter() {

		try {
			PythonParser.parse(unsupportedInterpreteCode.getCode());
		} catch (UnparsableCodeException e) {
			assertFalse("An exception has occurred", false);
		} catch (UnsupportedInterpreterException e) {
			assertTrue("Unsupported interpreter", true);
		}
	}
}
