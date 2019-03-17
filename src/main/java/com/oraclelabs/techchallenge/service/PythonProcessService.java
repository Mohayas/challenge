package com.oraclelabs.techchallenge.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.oraclelabs.techchallenge.model.PythonProcess;
import com.oraclelabs.techchallenge.model.ServerProcesses;

@Service
public class PythonProcessService {

	public String runCode(String code, String sessionId) throws IOException {
		PythonProcess pythonProcess = ServerProcesses.getProcess(sessionId);
		if (pythonProcess == null) {
			pythonProcess = new PythonProcess(sessionId);
			ServerProcesses.addProcess(pythonProcess);
		}
		return pythonProcess.runCode(code);
		

	}

}
