package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries;

import com.structurizr.model.SoftwareSystem;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class SystemBoundaryBuilder extends AbstractBoundaryBuilder<SystemBoundaryBuilder> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitSystemBoundary(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitSystemBoundary(this);
	}

}
