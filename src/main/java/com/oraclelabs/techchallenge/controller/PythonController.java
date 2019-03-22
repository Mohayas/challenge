package com.oraclelabs.techchallenge.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.oraclelabs.techchallenge.core.Code;
import com.oraclelabs.techchallenge.core.PythonInterpreter;
import com.oraclelabs.techchallenge.core.PythonParser;
import com.oraclelabs.techchallenge.core.PythonSession;
import com.oraclelabs.techchallenge.core.ServerSessions;
import com.oraclelabs.techchallenge.exception.UnparsableCodeException;
import com.oraclelabs.techchallenge.exception.UnsupportedInterpreterException;

@RestController
public class PythonController {

	@Autowired
	PythonInterpreter pythonInterpreter;

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> execute(@RequestBody Code code, HttpSession session) {
		try {
			String sessionId = session.getId();
			System.out.println(sessionId);

			PythonSession pythonSession = ServerSessions.getProcess(sessionId);
			String codeToRun = PythonParser.parse(code.getCode());
			String output = pythonInterpreter.runCode(codeToRun, pythonSession.getProcess());
			return new ResponseEntity<ApiResponse>(new ApiResponse(output), HttpStatus.OK);
			
		} catch (UnparsableCodeException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse("Unparsable code."), HttpStatus.BAD_REQUEST);
		} catch (UnsupportedInterpreterException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse("Unsupported interpreter[ "+e.getInterpreter()+" ]"), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * To format the response object
	 * 
	 * @author mohamedelasas
	 *
	 */
	public class ApiResponse {
		private Object result;

		public ApiResponse(Object result) {
			super();
			this.result = result;
		}

		public Object getResult() {
			return result;
		}

		public void setResult(Object result) {
			this.result = result;
		}

	}

}
