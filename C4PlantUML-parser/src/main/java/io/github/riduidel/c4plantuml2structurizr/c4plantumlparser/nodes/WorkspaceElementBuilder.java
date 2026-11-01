package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import com.structurizr.Workspace;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;

public abstract class WorkspaceElementBuilder {
	protected List<WorkspaceElementBuilder> children = new ArrayList<>();
	
	protected Properties properties = new Properties();

	public boolean add(WorkspaceElementBuilder e) {
		return children.add(e);
	}
	
	public <Type> Type accept(WorkspaceElementVisitor<Type> visitor) {
		if(startVisit(visitor)) {
			for (WorkspaceElementBuilder workspaceElementBuilder : children) {
				workspaceElementBuilder.accept(visitor);
			}
		}
		return endVisit(visitor);
	}

	protected abstract boolean startVisit(WorkspaceElementVisitor visitor);
	
	protected abstract <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor);

	public void setProperty(String name, String value) {
		properties.setProperty(name, value);
	}
}
