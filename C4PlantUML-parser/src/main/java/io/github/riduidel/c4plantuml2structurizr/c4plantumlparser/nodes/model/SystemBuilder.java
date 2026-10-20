package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.SoftwareSystem;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShape;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithType;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class SystemBuilder extends ModelElementBuilder<SystemBuilder, SoftwareSystem>
	implements WithType<SystemBuilder>, WithShape<SystemBuilder> {

	private String type = "";
	private String shape = "";

	public String getType() {
		return type;
	}

	@Override
	public SystemBuilder withType(String type) {
		this.type = type;
		return (SystemBuilder) this;
	}

	public String getShape() {
		return shape;
	}

	@Override
	public SystemBuilder withShape(String shape) {
		this.shape = shape;
		return this;
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitSoftwareSystem(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitSoftwareSystem(this);
	}
}
