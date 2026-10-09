package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import com.structurizr.Workspace;
import com.structurizr.model.SoftwareSystem;

public class SoftwareSystemBuilder extends ModelElementBuilder<SoftwareSystemBuilder, SoftwareSystem> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitSoftwareSystem(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitSoftwareSystem(this);
	}

}
