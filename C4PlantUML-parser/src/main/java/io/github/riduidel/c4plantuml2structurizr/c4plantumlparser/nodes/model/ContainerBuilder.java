package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.Container;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShape;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithType;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ContainerBuilder extends ModelElementBuilder<ContainerBuilder, Container> 
	implements 
	WithTechnology<ContainerBuilder>,
	WithType<ContainerBuilder> {
	private String technology = "";
	private String type = "";

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitContainer(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitContainer(this);
	}

	public String getTechnology() {
		return technology;
	}
	
	public ContainerBuilder withTechnology(String technology) {
		this.technology = technology;
		return this;
	}
	
	@Override
	public ContainerBuilder withType(String type) {
		this.type = type;
		return this;
	}

	public String getType() {
		return type;
	}

}
