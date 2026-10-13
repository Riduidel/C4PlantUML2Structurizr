package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries;

import com.structurizr.model.Container;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ContainerBoundaryBuilder extends BoundaryBuilder<ContainerBoundaryBuilder, Container> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitContainerBoundary(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitContainerBoundary(this);
	}
	
}
