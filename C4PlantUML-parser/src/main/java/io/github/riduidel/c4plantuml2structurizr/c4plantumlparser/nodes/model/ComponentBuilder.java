package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.Component;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShape;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ComponentBuilder extends ModelElementBuilder<ComponentBuilder, Component> 
	implements 
	WithTechnology<ComponentBuilder>,
	WithShape<ComponentBuilder> {

	private String technology = "";
	private String shape;

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitComponent(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitComponent(this);
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	public ComponentBuilder withTechnology(String technology) {
		setTechnology(technology);
		return this;
	}

	public String getShape() {
		return shape;
	}

	@Override
	public ComponentBuilder withShape(String shape) {
		this.shape = shape;
		return this;
	}
}
