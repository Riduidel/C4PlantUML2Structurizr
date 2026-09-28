package io.github.riduidel.structurizr;

import java.io.File;
import java.nio.file.Path;

import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParser;
import com.structurizr.dsl.StructurizrDslParserException;

public class StructurizrTestUtils {

	public static Workspace toWorkspace(Path path) throws StructurizrDslParserException {
		File file = path.toFile();
		return toWorkspace(file);
	}

	public static Workspace toWorkspace(File file) throws StructurizrDslParserException {
		StructurizrDslParser parser = new StructurizrDslParser();
		parser.parse(file);
		Workspace parsed = parser.getWorkspace();
		return parsed;
	}

}
