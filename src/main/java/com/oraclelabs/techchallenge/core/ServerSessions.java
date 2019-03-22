package com.oraclelabs.techchallenge.core;

import java.util.ArrayList;
import java.util.List;



public class ServerSessions {

	/**
	 * list that contains all the server processes
	 */
	private static List<PythonSession> processes;
	
	static{
	if(processes==null){
		processes = new ArrayList<PythonSession>();
	}
	
	}
	/**
	 * create a new python session if it doesn't existsand returns it.
	 * @param sessionId
	 * @return
	 */
	public static PythonSession getProcess(String sessionId) {
		
		PythonSession pythonSession = getProcessIfExist(sessionId);
		if (pythonSession == null) {
			pythonSession = new PythonSession(sessionId);
			ServerSessions.addProcess(pythonSession);
		}
		return pythonSession;
		
	}
	/**
	 * Filtering process by its sessionId
	 * @param sessionId
	 * @return
	 */
	private static PythonSession getProcessIfExist(String sessionId) {

		return processes.stream().filter(p -> p.getSessionId().equals(sessionId)).findFirst().orElse(null);
	}

	public static void addProcess(PythonSession process) {
		processes.add(process);
	}

}
