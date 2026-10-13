package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.SoftwareSystem;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class SystemBuilder extends ModelElementBuilder<SystemBuilder, SoftwareSystem> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitSoftwareSystem(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitSoftwareSystem(this);
	}

}
