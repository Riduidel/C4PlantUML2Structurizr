package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.SoftwareSystem;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShape;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithType;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class SystemBuilder extends ModelElementBuilder<SystemBuilder, SoftwareSystem>
	implements WithType<SystemBuilder>, WithShape<SystemBuilder> {

	private String type = "";
	public String getType() {
		return type;
	}

	@Override
	public SystemBuilder withType(String type) {
		this.type = type;
		return (SystemBuilder) this;
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
