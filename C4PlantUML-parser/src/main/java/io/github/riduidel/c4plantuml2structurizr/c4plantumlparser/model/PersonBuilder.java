package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import com.structurizr.Workspace;
import com.structurizr.model.Person;

public class PersonBuilder extends ModelElementBuilder<PersonBuilder, Person> {

	@Override
	protected Person buildModelElement(Workspace returned) {
		return returned.getModel().addPerson(name, description);
	}

}
