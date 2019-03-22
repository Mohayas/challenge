package com.oraclelabs.techchallenge.core;

public class PythonSession {

	private PythonProcess process;
	private String sessionId;
	
	
	public PythonSession(String sessionId) {
		super();
		this.sessionId = sessionId;
		process =new PythonProcess();
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public PythonProcess getProcess() {
		return process;
	}
	public void setProcess(PythonProcess process) {
		this.process = process;
	}
	
}
