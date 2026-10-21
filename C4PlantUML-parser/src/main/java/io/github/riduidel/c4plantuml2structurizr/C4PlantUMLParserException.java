package io.github.riduidel.c4plantuml2structurizr;

public class C4PlantUMLParserException extends RuntimeException {

	public C4PlantUMLParserException() {
	}

	public C4PlantUMLParserException(String message) {
		super(message);
	}

	public C4PlantUMLParserException(Throwable cause) {
		super(cause);
	}

	public C4PlantUMLParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public C4PlantUMLParserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
