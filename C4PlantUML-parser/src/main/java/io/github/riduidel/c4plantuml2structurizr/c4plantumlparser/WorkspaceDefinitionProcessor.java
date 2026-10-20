package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParserRuntimeException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.DefaultValueStack;
import org.parboiled.support.ParsingResult;

import com.github.fge.lambdas.Throwing;
import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser.C4PlantUMLParser;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ComponentDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContainerDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram;

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
		diagrams.containers().stream().forEach(Throwing.consumer(file -> parseContainersIn(returned, file)));
		diagrams.components().stream().forEach(Throwing.consumer(file -> parseComponentsIn(returned, file)));
		return returned;
	}

	private Object parseComponentsIn(Workspace returned, File component) throws IOException {
		return parseFile(new ComponentDiagram(returned, component));
	}

	private Object parseContainersIn(Workspace returned, File container) throws IOException {
		return parseFile(new ContainerDiagram(returned, container));
	}

	/**
	 * Parses a context diagram and add 
	 * @param context
	 * @return
	 * @throws IOException
	 */
	private Workspace parseContext(File context) throws IOException {
		return parseFile(new ContextDiagram(context));
	}

	private Workspace parseFile(C4PlantUMLDiagram root) throws IOException {
		File diagramFile = root.source;
		try {
			logger.info("Parsing file "+diagramFile.getAbsolutePath());
			String fileContent = Files.readString(diagramFile.toPath());
			ParsingResult<WorkspaceElementBuilder> result = new ReportingParseRunner(parser.diagram())
					.withValueStack(new DefaultValueStack<WorkspaceElementBuilder>(Arrays.asList(root)))
					.run(fileContent);
	        if (result.hasErrors()) {
	        	throw new RuntimeException(String.format("File %s has parsing errrors\n%s",
	        			diagramFile,
	        			ErrorUtils.printParseErrors(result.parseErrors)));
	        }
	        if(result.parseTreeRoot.getValue()==root) {
	        	return root.build(configuration);
	        } else {
	        	throw new RuntimeException(String.format("Parsing of file %s returned incorrect result (we do not have the same C4PlantUMLDiagram returned, but %s)", diagramFile, result.parseTreeRoot.getValue()));
	        }
		} catch(ParserRuntimeException e) {
			throw new RuntimeException(String.format("Unable to parse %s", diagramFile), e);
		}
	}
}
