package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.Configuration;

public class WorkspaceElementBuilder {
	protected List<WorkspaceElementBuilder> children = new ArrayList<>();

	public boolean add(WorkspaceElementBuilder e) {
		return children.add(e);
	}

	/**
	 * Build the Workspace element and recursively build children.
	 * @param configuration Transformation configration
	 * @param stack The stack model allowing access to ancestors. This is handled directly here.
	 * @param workspace
	 * @return
	 */
	protected void build(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned) {
		beforeBuildingChildren(configuration, stack, returned);
		buildChildren(configuration, stack, returned);
		afterBuildingChildren(configuration, stack, returned);
	}

	private void buildChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned) {
		for(WorkspaceElementBuilder child : children) {
			beforeBuildingChild(configuration, stack, returned, child);
			buildChild(configuration, stack, returned, child);
			afterBuildingChild(configuration, stack, returned, child);
		}
	}

	protected void afterBuildingChild(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned,
			WorkspaceElementBuilder child) {
		
	}

	protected void beforeBuildingChild(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned,
			WorkspaceElementBuilder child) {
		
	}

	protected void buildChild(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned, WorkspaceElementBuilder child) {
		child.build(configuration, stack, returned);
	}

	protected void beforeBuildingChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned) {
		stack.push(this);
	}
	
	protected void afterBuildingChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned) {
		stack.pop();
	}
}
