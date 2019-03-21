package com.oraclelabs.techchallenge.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
