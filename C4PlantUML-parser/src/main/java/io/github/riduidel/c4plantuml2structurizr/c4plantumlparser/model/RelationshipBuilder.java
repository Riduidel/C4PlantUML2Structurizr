package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

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

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.copier.StructurizrHack;

public class RelationshipBuilder 
	extends WorkspaceElementBuilder
	implements WithDescription<RelationshipBuilder>, WithTechnology<RelationshipBuilder> {
	private String sourceAlias;
	private String targetAlias;
	private String description;
	private String technology;

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
}
