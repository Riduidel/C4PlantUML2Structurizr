package io.github.riduidel.c4plantuml2structurizr;

import java.io.File;

public enum IncludeElements {
	ALL,
	FOLDER_AND_BELOW {
		@Override
		public boolean whenDeclaredIn(File diagramFile, File includeFile) {
			return includeFile.toPath().startsWith(diagramFile.getParentFile().toPath());
		}
	}
	;

	public boolean whenDeclaredIn(File diagramFile, File includeFile) {
		return true;
	}
	
}
