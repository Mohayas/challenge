package com.oraclelabs.techchallenge.core;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.oraclelabs.techchallenge.core.PythonSession;
import com.oraclelabs.techchallenge.core.ServerSessions;

@Service
public class PythonInterpreter {

	public static String runCode(String code, PythonProcess process) throws IOException, InterruptedException {
		
		return process.runCode(code);
		

	}

}
