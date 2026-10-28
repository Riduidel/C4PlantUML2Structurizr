package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram.BuildContextDiagramVisitor;

public class IncludedDiagram extends C4PlantUMLDiagram {
	public IncludedDiagram(File context) {
		super(context);
	}
}
