package com.oraclelabs.techchallenge.model;

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

public class PythonProcess {

	private Process process;
	private String sessionId;

	public PythonProcess(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

	private String getOutput() throws IOException {

		BufferedReader output = new BufferedReader(new InputStreamReader(this.process.getInputStream()));

		String lines="", line="";
		do {
			line = output.readLine();
			if(!line.equals("[stop reading]"))
			lines = lines + line + "\n";
			
		} while (!line.equals("[stop reading]"));

		return lines;
	}

	private String getErrOutput() throws IOException {

		BufferedReader output = new BufferedReader(new InputStreamReader(this.process.getInputStream()));

		String line;
		while (!(line = output.readLine()).equals("[stop reading]")) {
			System.out.println(line);
			line += line + "\n";
		}
		return line;

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

	public void init() throws IOException, InterruptedException {
		if (this.process == null) {
			ProcessBuilder builder = new ProcessBuilder("python", "-iu");
			builder.redirectInput(Redirect.PIPE);
			builder.redirectOutput(Redirect.PIPE);
			builder.redirectError(Redirect.PIPE);
			this.process = builder.start();

		}
	}

	public void close() {
		this.process.destroyForcibly();
	}

	public String runCode(String code) throws IOException, InterruptedException {

		init();
		execute(code);
		// getErrOutput();
		return getOutput();

	}

	private void execute(String code) throws IOException {

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

		writer.write(code);
		writer.newLine();
		writer.flush();
		writer.write("print (\"[stop reading]\")");
		writer.newLine();
		writer.flush();
		// writer.close();

	}

}
