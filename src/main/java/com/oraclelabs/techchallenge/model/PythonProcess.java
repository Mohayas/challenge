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


	public String getOutput() {
		if(!(output=getErrOutput()).isEmpty()){
			return output;
		}
		try {
			BufferedReader stdOutout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			output="";
			while ((line = stdOutout.readLine()) != null) {
				System.out.println(line);
				output += line + "\n";
			}
			stdOutout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}


	private String getErrOutput() {
		String errOutput="";
		try {
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;

			while ((line = stdError.readLine()) != null) {
				System.out.println(line);
				errOutput += line + "\n";
			}
			stdError.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return errOutput;
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
	}

	public void close() {
		process.destroyForcibly();
	}

	public void runCode(String code) {
		try {
			BufferedWriter pStdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			pStdin.write(code);
			pStdin.flush();
			pStdin.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
