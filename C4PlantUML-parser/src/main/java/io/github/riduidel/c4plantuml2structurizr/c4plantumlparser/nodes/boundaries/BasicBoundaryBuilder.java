package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries;

import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class BasicBoundaryBuilder extends AbstractBoundaryBuilder<BasicBoundaryBuilder> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitBasicBoundary(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitBasicBoundary(this);
	}

}
