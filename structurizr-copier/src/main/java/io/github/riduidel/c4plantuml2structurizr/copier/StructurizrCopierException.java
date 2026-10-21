package io.github.riduidel.c4plantuml2structurizr.copier;

public class StructurizrCopierException extends RuntimeException {

	public StructurizrCopierException() {
	}

	public StructurizrCopierException(String message) {
		super(message);
	}

	public StructurizrCopierException(Throwable cause) {
		super(cause);
	}

	public StructurizrCopierException(String message, Throwable cause) {
		super(message, cause);
	}

	public StructurizrCopierException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
