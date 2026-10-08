package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.DefaultValueStack;
import org.parboiled.support.ParsingResult;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WorkspaceElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser.C4PlantUMLParser;

public class WorkspaceDefinitionProcessor {
	private static final Logger logger = Logger.getLogger(WorkspaceDefinitionProcessor.class.getName());
	
	private DiagramTypeDetector detector = new DiagramTypeDetector();
	
	private C4PlantUMLParser parser = Parboiled.createParser(C4PlantUMLParser.class);
	
	private Configuration configuration = new Configuration();

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
		return parseFile(context);
	}

	private Workspace parseFile(File plantUmlDiagram) throws IOException {
		logger.info("Parsing file "+plantUmlDiagram.getAbsolutePath());
		String fileContent = Files.readString(plantUmlDiagram.toPath());
		C4PlantUMLDiagram diagram = new C4PlantUMLDiagram(plantUmlDiagram);
		ParsingResult<WorkspaceElementBuilder> result = new ReportingParseRunner(parser.diagram())
				.withValueStack(new DefaultValueStack<WorkspaceElementBuilder>(Arrays.asList(diagram)))
				.run(fileContent);
        if (result.hasErrors()) {
        	throw new RuntimeException(String.format("File %s has parsing errrors\n%s",
        			plantUmlDiagram,
        			ErrorUtils.printParseErrors(result.parseErrors)));
        }
        if(result.parseTreeRoot.getValue()==diagram) {
        	return diagram.build(configuration);
        } else {
        	throw new RuntimeException(String.format("Parsing of file %s returned incorrect result (we do not have the same C4PlantUMLDiagram returned, but %s)", plantUmlDiagram, result.parseTreeRoot.getValue()));
        }
	}
}
