package com.oraclelabs.techchallenge.model;

import java.util.List;

import org.springframework.context.annotation.Scope;

@Scope("singleton")
public class ServerProcesses {

	private static List<PythonProcess> processes;

	public static PythonProcess getProcess(String sessionId) {

		return processes.stream().filter(p -> p.getSessionId().equals(sessionId)).findFirst().orElse(null);
	}

	public static void addProcess(PythonProcess process) {

		processes.add(process);
	}

}
