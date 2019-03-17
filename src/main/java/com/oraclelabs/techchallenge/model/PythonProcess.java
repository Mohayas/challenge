package com.oraclelabs.techchallenge.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PythonProcess {

	private Process process;
	private String sessionId;
	private String output;
	private BufferedWriter stdInputWriter;
	private BufferedReader stdOutputReader;
	private BufferedReader errOutpuReader;

	public PythonProcess(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

	private String getOutput() {
		if (!(output = readOutput(errOutpuReader)).isEmpty()) {
			return output;
		} else {
			output = readOutput(stdOutputReader);
			return output;
		}

	}

	private String readOutput(BufferedReader breader) {
		String codeResult = "";
		try {
			String line;
			while ((line = breader.readLine()) != null) {
				System.out.println(line);
				codeResult += line + "\n";
			}
			breader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return codeResult;
	}

	public Process getProcess() {
		return process;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void init() throws IOException {
		if (process == null) {
			process = Runtime.getRuntime().exec("py");
		}
		stdInputWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		stdOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		errOutpuReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

	}

	public void close() {
		process.destroyForcibly();
	}

	public String runCode(String code) throws IOException {

		init();
		stdInputWriter.write(code);
		stdInputWriter.flush();
		stdInputWriter.close();

		return getOutput();
	}

}
