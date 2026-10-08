package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import java.util.Stack;

import com.structurizr.Workspace;
import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.Configuration;

public abstract class ModelElementBuilder<BuilderType extends ModelElementBuilder, BuiltType extends StaticStructureElement> 
	extends WorkspaceElementBuilder 
	implements WithAlias<BuilderType>, WithLabel<BuilderType>, WithDescription<BuilderType> {
	
	private BuiltType built;

	private String alias;
	protected String name;
	protected String description;

	public void setAlias(String match) {
		this.alias = match;
	}

	@Override
	public BuilderType withAlias(String match) {
		setAlias(match);
		return (BuilderType) this;
	}

	public void setLabel(String match) {
		this.name = match;
	}

	@Override
	public BuilderType withLabel(String match) {
		setLabel(match);
		return (BuilderType) this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public BuilderType withDescription(String match) {
		setDescription(match);
		return (BuilderType) this;
	}

	@Override
	protected void beforeBuildingChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned) {
		super.beforeBuildingChildren(configuration, stack, returned);
		if(built==null) {
			built = buildModelElement(returned);
			decorate(configuration, stack, returned, built);
		}
	}

	protected void decorate(Configuration configuration, Stack<WorkspaceElementBuilder> stack, Workspace returned, BuiltType built2) {
		setVariableName(configuration, built);
	}

	private void setVariableName(Configuration configuration, BuiltType built) {
		if(alias!=null) {
			built.addProperty(configuration.variableProperty, alias);
		}
	}

	protected abstract BuiltType buildModelElement(Workspace returned);
	
	
}
