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
	private Relationship built;
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
	
	@Override
	protected void beforeBuildingChildren(Configuration configuration, Stack<WorkspaceElementBuilder> stack,
			Workspace returned) {
		if(built==null) {
			Model model = returned.getModel();
			// Source and target are collections to get all declarations
			List<Element> source = new ArrayList<Element>();
			List<Element> target = new ArrayList<Element>();
			for (Element element : model.getElements()) {
				if(element.getProperties().containsKey(configuration.variableProperty)) {
					String variable = element.getProperties().get(configuration.variableProperty);
					if(sourceAlias.equals(variable)) {
						source.add(element);
					} else if(targetAlias.equals(variable)) {
						target.add(element);
					}
				}
			}
			failIfBadlyDeclared(Map.of("source", source, "target", target));
			// ok, source and target have only one elements, so create the relationship
			built = new StructurizrHack().addRelationship(model, source.get(0), target.get(0), description, technology, InteractionStyle.Synchronous, new String[0]);
		}
		super.beforeBuildingChildren(configuration, stack, returned);
	}

	private void failIfBadlyDeclared(Map<String, List<Element>> of) {
		String message = of.entrySet().stream()
			.filter(e -> e.getValue().size()!=1)
			.map(e -> String.format("%s end of %s is badly declared, since %d model elements match\n", 
					e.getKey(),
					this,
					e.getValue().stream()
						.map(element -> element.toString())
						.map(element -> "* "+element)
						.collect(Collectors.joining("\n"))
						))
			.collect(Collectors.joining("\n"));
		if(!message.isBlank()) {
			throw new RuntimeException(message);
		}
	}
	@Override
	public String toString() {
		return "RelationshipBuilder [" + (sourceAlias != null ? "sourceAlias=" + sourceAlias + ", " : "")
				+ (targetAlias != null ? "targetAlias=" + targetAlias + ", " : "")
				+ (description != null ? "description=" + description : "")
				+ (technology != null ? "technology=" + technology : "") + "]";
	}
}
