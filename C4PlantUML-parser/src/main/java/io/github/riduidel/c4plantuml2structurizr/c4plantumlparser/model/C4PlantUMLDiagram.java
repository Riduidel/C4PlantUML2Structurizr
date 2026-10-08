package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import java.io.File;
import java.util.Stack;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;

public class C4PlantUMLDiagram extends WorkspaceElementBuilder {
	File source;

	public C4PlantUMLDiagram(File context) {
		source = context;
	}

	public Workspace build(Configuration configuration) {
		Workspace returned = new Workspace(null);
		build(configuration, new Stack<WorkspaceElementBuilder>(), returned);
		return returned;
	}
	
	@Override
	protected void afterBuildingChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack,
			Workspace returned) {
		for (WorkspaceElementBuilder workspaceElementBuilder : children) {
			if(workspaceElementBuilder instanceof PlantUMLBlock) {
				returned.setName(((PlantUMLBlock) workspaceElementBuilder).blockName);
			}
		}
	}
}
