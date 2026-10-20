package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithAlias;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithDescription;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLabel;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLink;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithSprite;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTags;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementBuilder;

public abstract class ModelElementBuilder<BuilderType extends ModelElementBuilder, BuiltType extends StaticStructureElement> 
	extends WorkspaceElementBuilder 
	implements WithAlias<BuilderType>, 
		WithLabel<BuilderType>, 
		WithDescription<BuilderType>,
		WithSprite<BuilderType>,
		WithTags<BuilderType>,
		WithLink<BuilderType> {

	/**
	 * Name of the variable used to reference this element in model
	 */
	private String alias = "";
	private String name = "";
	private String description = "";
	private String sprite = "";
	private String tags = "";
	private String link = "";

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

	public String getLabel() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSprite() {
		return sprite;
	}

	@Override
	public BuilderType withSprite(String match) {
		this.sprite = sprite;
		return (BuilderType) this;
	}

	public String getTags() {
		return tags;
	}

	@Override
	public BuilderType withTags(String tags) {
		this.tags = tags;
		return (BuilderType) this;
	}

	public String getLink() {
		return link;
	}

	@Override
	public BuilderType withLink(String link) {
		this.link = link;
		return (BuilderType) this;
	}
}
