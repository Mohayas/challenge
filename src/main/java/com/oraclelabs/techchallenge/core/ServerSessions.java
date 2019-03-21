package com.oraclelabs.techchallenge.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;



public class ServerSessions {

	private static List<PythonSession> processes;
	
	static{
	if(processes==null){
		processes = new ArrayList<PythonSession>();
	}
	
	}
	public static PythonSession getProcess(String sessionId) {
		
		PythonSession pythonSession = getProcessIfExist(sessionId);
		if (pythonSession == null) {
			pythonSession = new PythonSession(sessionId);
			ServerSessions.addProcess(pythonSession);
		}
		return pythonSession;
		
	}
	private static PythonSession getProcessIfExist(String sessionId) {

		return processes.stream().filter(p -> p.getSessionId().equals(sessionId)).findFirst().orElse(null);
	}

	public static void addProcess(PythonSession process) {

		processes.add(process);
	}

}
