package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.model.Container;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ContainerBuilder extends ModelElementBuilder<ContainerBuilder, Container> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitContainer(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitContainer(this);
	}

}
