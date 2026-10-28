package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;

public abstract class DiagramBuilderVisitor extends WorkspaceBuilderVisitor {

	public DiagramBuilderVisitor(Workspace workspace, File source, ConverterConfiguration configuration) {
		super(workspace, source, configuration);
	}

	protected boolean isDeclaredInDiagram() {
		return configuration.includeElements.whenDeclaredIn(source, diagrams.peek().source);
	}

}
