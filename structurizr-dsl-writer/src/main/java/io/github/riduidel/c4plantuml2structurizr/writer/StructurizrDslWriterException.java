package io.github.riduidel.c4plantuml2structurizr.writer;

public class StructurizrDslWriterException extends RuntimeException {

	public StructurizrDslWriterException() {
	}

	public StructurizrDslWriterException(String message) {
		super(message);
	}

	public StructurizrDslWriterException(Throwable cause) {
		super(cause);
	}

	public StructurizrDslWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public StructurizrDslWriterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
