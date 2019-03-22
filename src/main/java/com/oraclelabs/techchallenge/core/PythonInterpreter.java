package com.oraclelabs.techchallenge.core;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class PythonInterpreter {

	public  String runCode(String code, PythonProcess process) throws IOException, InterruptedException {
		
		return process.runCode(code);
		

	}

}
