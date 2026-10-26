package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;

public abstract class BuildableC4PlantUMLDiagram extends C4PlantUMLDiagram {

	public BuildableC4PlantUMLDiagram(File context) {
		super(context);
	}

	public abstract Workspace build(Configuration configuration);

}
