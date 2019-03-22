package com.oraclelabs.techchallenge.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;

public class PythonProcess {

	private Process process;
	private BufferedWriter writer;
	private BufferedReader reader;

	public PythonProcess() {
		super();
	}

	/**
	 * getting the output of the python process using "readOutput" function
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getOutput() throws IOException {

		String output = readOutput(reader);

		// the subString just to remove ">>> " from the output,
		// that's just because we're merging the sdtout with errout streams
		if(!output.isEmpty())
		return output.substring(4, output.length()).trim();
		else return output;
	}

	/**
	 * Read the output of the python process
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	private String readOutput(BufferedReader reader) throws IOException {

		String lines = "", line = "";
		do {
			line = reader.readLine();
			if (!line.contains("[stop reading]"))
				lines = lines + line + System.getProperty("line.separator");

		} while (!line.contains("[stop reading]"));

		return lines;
	}

	/**
	 * To initiate the process
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void init() throws IOException, InterruptedException {

		// checking if the process is already initiated.
		if (this.process == null) {

			// start the python process in the interactive mode -i with
			// unbuffered
			// stdin, stdout and stderr
			ProcessBuilder builder = new ProcessBuilder("python", "-iu");

			// piping the streams
			builder.redirectInput(Redirect.PIPE);
			builder.redirectOutput(Redirect.PIPE);
			
			// merging the stdout with errou to read them all in once.
			builder.redirectErrorStream(true);

			this.process = builder.start();

			writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

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

	/**
	 * A function that to run code and returns the output.
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String runCode(String code) throws IOException, InterruptedException {

		// initiating the process
		init();
		execute(code);

		// just so we can tell the reader to stop reading.
		execute("print (\"[stop reading]\")");
		return getOutput();

	}

	/**
	 * a function that writes the code into the python process.
	 * 
	 * @param code
	 * @throws IOException
	 */
	private void execute(String code) throws IOException {

		writer.write(code);
		writer.newLine();
		writer.flush();

	}

}
