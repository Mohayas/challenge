package com.oraclelabs.techchallenge.core;

import com.oraclelabs.techchallenge.exception.UnparsableCodeException;
import com.oraclelabs.techchallenge.exception.UnsupportedInterpreterException;

public class PythonParser {

	public static String parse(String code) throws UnparsableCodeException, UnsupportedInterpreterException {

		
		
		if (!code.startsWith("%"))
			throw new UnparsableCodeException();
		
		int indexOfFirstWhiteSpace = code.indexOf(' ');
		String interpreter = code.substring(0 , indexOfFirstWhiteSpace);
		String pythonCode = code.substring(indexOfFirstWhiteSpace + 1, code.length());
		
		if (!interpreter.equals("%python"))
			throw new UnsupportedInterpreterException(interpreter.substring(1));
		
		if (pythonCode.length() ==0)
			throw new UnparsableCodeException();


		return pythonCode;
	}
}
