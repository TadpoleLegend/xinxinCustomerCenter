package com.ls.exception;

public class NoMorePagesFoundException extends Exception{

	public NoMorePagesFoundException() {
		super();
	}

	public NoMorePagesFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoMorePagesFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoMorePagesFoundException(String message) {
		super(message);
	}

	public NoMorePagesFoundException(Throwable cause) {
		super(cause);
	}
}
