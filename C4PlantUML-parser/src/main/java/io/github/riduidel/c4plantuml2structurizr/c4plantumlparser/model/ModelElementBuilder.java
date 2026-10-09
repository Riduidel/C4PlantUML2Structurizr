package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import java.util.Stack;

import com.structurizr.Workspace;
import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.Configuration;

public abstract class ModelElementBuilder<BuilderType extends ModelElementBuilder, BuiltType extends StaticStructureElement> 
	extends WorkspaceElementBuilder 
	implements WithAlias<BuilderType>, WithLabel<BuilderType>, WithDescription<BuilderType> {

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

	public String getAlias() {
		return alias;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
