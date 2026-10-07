package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.structurizr.Workspace;

import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.SourceFileReader;

public class Parser {
	private DiagramTypeDetector detector = new DiagramTypeDetector();

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
		diagrams.others().stream().forEach(file -> parseOpthersIn(returned, file));
		return returned;
	}

	private Object parseOpthersIn(Workspace returned, File file) {
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
		SourceFileReader reader = new SourceFileReader(true, context);
		for(BlockUml block : reader.getBlocks()) {
			System.out.println(block);
		}
		// TODO Auto-generated method stub
		return null;
	}
}
