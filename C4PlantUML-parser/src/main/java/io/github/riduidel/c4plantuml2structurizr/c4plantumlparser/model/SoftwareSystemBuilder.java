package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import com.structurizr.Workspace;
import com.structurizr.model.SoftwareSystem;

public class SoftwareSystemBuilder extends ModelElementBuilder<SoftwareSystemBuilder, SoftwareSystem> {

	@Override
	protected SoftwareSystem buildModelElement(Workspace returned) {
		return returned.getModel().addSoftwareSystem(name, description);
	}

}
