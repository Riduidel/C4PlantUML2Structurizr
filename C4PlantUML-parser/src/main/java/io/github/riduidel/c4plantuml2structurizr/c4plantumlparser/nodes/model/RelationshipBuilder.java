package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.structurizr.Workspace;
import com.structurizr.model.Element;
import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Model;
import com.structurizr.model.Relationship;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithDescription;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLink;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithSprite;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTags;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;
import io.github.riduidel.c4plantuml2structurizr.copier.StructurizrHack;

public class RelationshipBuilder 
	extends WorkspaceElementBuilder
	implements 
		WithDescription<RelationshipBuilder>, 
		WithTechnology<RelationshipBuilder>,
		WithSprite<RelationshipBuilder>,
		WithTags<RelationshipBuilder>,
		WithLink<RelationshipBuilder> {
	private String sourceAlias;
	private String targetAlias;
	private String description = "";
	private String technology = "";
	private String sprite = "";
	private String tags = "";
	private String link = "";

	@Override
	public String toString() {
		return "RelationshipBuilder [" + (sourceAlias != null ? "sourceAlias=" + sourceAlias + ", " : "")
				+ (targetAlias != null ? "targetAlias=" + targetAlias + ", " : "")
				+ (description != null ? "description=" + description : "")
				+ (technology != null ? "technology=" + technology : "") + "]";
	}
	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitRelationship(this);
	}
	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitRelationship(this);
	}
	public void setSourceAlias(String sourceAlias) {
		this.sourceAlias = sourceAlias;
	}
	public void setTargetAlias(String targetAlias) {
		this.targetAlias = targetAlias;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public RelationshipBuilder withDescription(String description) {
		setDescription(description);
		return this;
	}
	@Override
	public RelationshipBuilder withTechnology(String technology) {
		setTechnology(technology);
		return this;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	public String getSourceAlias() {
		return sourceAlias;
	}
	public String getTargetAlias() {
		return targetAlias;
	}
	public String getDescription() {
		return description;
	}
	public String getTechnology() {
		return technology;
	}
	@Override
	public RelationshipBuilder withSprite(String sprite) {
		this.sprite = sprite;
		return this;
	}
	public String getSprite() {
		return sprite;
	}
	public String getTags() {
		return tags;
	}
	@Override
	public RelationshipBuilder withTags(String tags) {
		this.tags = tags;
		return this;
	}
	public String getLink() {
		return link;
	}
	@Override
	public RelationshipBuilder withLink(String link) {
		this.link = link;
		return this;
	}
}
