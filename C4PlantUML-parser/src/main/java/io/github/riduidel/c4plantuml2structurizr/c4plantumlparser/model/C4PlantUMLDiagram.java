package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import java.io.File;
import java.util.Stack;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;

public abstract class C4PlantUMLDiagram extends WorkspaceElementBuilder {
	public final File source;

	public C4PlantUMLDiagram(File context) {
		source = context;
	}

	public abstract Workspace build(Configuration configuration);
}
