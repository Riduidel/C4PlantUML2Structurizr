package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

import java.io.File;
import java.util.Stack;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;

public abstract class C4PlantUMLDiagram extends WorkspaceElementBuilder {
	public final File source;

	public C4PlantUMLDiagram(File context) {
		source = context;
	}
	
	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitDiagram(this);
	}
	
	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitDiagram(this);
	}
}
