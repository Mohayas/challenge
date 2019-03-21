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
	private BufferedReader errReader;

	public PythonProcess() {
		super();
	}

	private String getOutput() throws IOException {

		String output = readOutput(reader);

		// the subString just to remove ">>> " from the output,
		// that's just because we're merging the sdtout with errout streams
		return output.substring(4, output.length()).trim();
	}

	private String readOutput(BufferedReader reader) throws IOException {

		String lines = "", line = "";
		do {
			line = reader.readLine();
			if (!line.contains("[stop reading]"))
				lines = lines + line + System.getProperty("line.separator");

		} while (!line.contains("[stop reading]"));

		return lines;
	}

	public void init() throws IOException, InterruptedException {

		//checking if the process is already initiated
		if (this.process == null) {

			ProcessBuilder builder = new ProcessBuilder("python", "-iu");
			
			//piping the streams
			builder.redirectInput(Redirect.PIPE);
			builder.redirectOutput(Redirect.PIPE);
			builder.redirectErrorStream(true);
			

			this.process = builder.start();

			writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// importing sys because we need it to print errors
			execute("from sys import stderr");

			// printing [stop reading] to tell the reader to stop reading
			execute("print(\"[stop reading]\", file=stderr)");

			// we need to read before running the user code, so we clear the
			// output
			String str = readOutput(reader);
			System.out.println("first output" + str);

		}
	}

	public void close() {
		this.process.destroyForcibly();
	}

	public String runCode(String code) throws IOException, InterruptedException {
		
		//initiating the process
		init();
		execute(code);

		// just so we can tell the reader to stop reading.
		execute("print (\"[stop reading]\")");
		return getOutput();

	}

	private void execute(String code) throws IOException {

		writer.write(code);
		writer.newLine();
		writer.flush();

	}

}
