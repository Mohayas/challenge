package com.oraclelabs.techchallenge.exception;

public class UnsupportedInterpreterException extends Exception {

	private String interpreter;

	public UnsupportedInterpreterException(String interpreter) {
		super("Unsupported Interpreter[ " + interpreter + " ]");
		this.interpreter = interpreter;

	}

	public String getInterpreter() {
		return interpreter;
	}
}
