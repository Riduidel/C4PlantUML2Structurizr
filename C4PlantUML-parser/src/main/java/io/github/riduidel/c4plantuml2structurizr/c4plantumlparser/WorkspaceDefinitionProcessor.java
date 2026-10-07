package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import com.structurizr.Workspace;

public class WorkspaceDefinitionProcessor {
	private static final Logger logger = Logger.getLogger(WorkspaceDefinitionProcessor.class.getName());
	
	private DiagramTypeDetector detector = new DiagramTypeDetector();
	
	private C4PlantUMLParser parser = Parboiled.createParser(C4PlantUMLParser.class);

	/**
	 * Parses an indifferentiate list of files into a Structurizr workspace
	 * @param diagrams
	 * @return
	 */
	public Workspace parse(List<File> diagramFiles) throws IOException {
		// First, isolate interesting diagrams
		DetectedDiagrams diagrams = detector.detectDiagramsIn(diagramFiles);
		Workspace returned = parseContext(diagrams.context());
		diagrams.containers().stream().forEach(file -> parseContainersIn(returned, file));
		diagrams.components().stream().forEach(file -> parseComponentsIn(returned, file));
		diagrams.others().stream().forEach(file -> parseOthersIn(returned, file));
		return returned;
	}

	private Object parseOthersIn(Workspace returned, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object parseComponentsIn(Workspace returned, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object parseContainersIn(Workspace returned, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	private Workspace parseContext(File context) throws IOException {
		logger.info("Parsing file "+context.getAbsolutePath());
		String fileContent = Files.readString(context.toPath());
		ParsingResult<?> result = new ReportingParseRunner(parser.diagram()).run(fileContent);
		return null;
	}
}
