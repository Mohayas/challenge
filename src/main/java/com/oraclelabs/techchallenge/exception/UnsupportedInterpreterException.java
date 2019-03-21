package com.oraclelabs.techchallenge.exception;

public class UnsupportedInterpreterException extends Exception {

	public UnsupportedInterpreterException(String interpreter) {
		super("Unsupported Interpreter[ " + interpreter + " ]");

	}

}
