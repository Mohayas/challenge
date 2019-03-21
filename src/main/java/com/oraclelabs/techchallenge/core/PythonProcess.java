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

public class PythonProcess {

	private Process process;
	private BufferedWriter writer;
	private BufferedReader reader;

	public PythonProcess() {
		super();
	}

	private String getOutput() throws IOException {

		String lines = "", line = "";
		do {
			line = reader.readLine();
			if (!line.equals("[stop reading]"))
				lines = lines + line + "\n";

		} while (!line.equals("[stop reading]"));

		return lines;
	}

	public void init() throws IOException, InterruptedException {
		
		if (this.process == null) {
			
			ProcessBuilder builder = new ProcessBuilder("python", "-iu");
			
			builder.redirectInput(Redirect.PIPE);
			builder.redirectOutput(Redirect.PIPE);
			builder.redirectError(Redirect.PIPE);
			
			this.process = builder.start();
			
			writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		}
	}

	public void close() {
		this.process.destroyForcibly();
	}

	public String runCode(String code) throws IOException, InterruptedException {

		init();
		execute(code);
		return getOutput();

	}

	private void execute(String code) throws IOException {

		writer.write(code);
		writer.newLine();
		writer.flush();
		writer.write("print (\"[stop reading]\")");
		writer.newLine();
		writer.flush();

	}

}
