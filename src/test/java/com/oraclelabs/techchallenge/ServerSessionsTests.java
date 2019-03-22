package com.oraclelabs.techchallenge;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.oraclelabs.techchallenge.core.PythonSession;
import com.oraclelabs.techchallenge.core.ServerSessions;

@SpringBootTest
public class ServerSessionsTests {

	private final String sessionId = "FB2E77D47A0479900504CB3AB4A1F626D174D2D";

	@Test
	public void getProcess() {

		PythonSession pythonSession = ServerSessions.getProcess(sessionId);
		Assert.notNull(pythonSession, "Must not be null.");
	}

}
