package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import com.structurizr.Workspace;
import com.structurizr.model.Person;

public class PersonBuilder extends ModelElementBuilder<PersonBuilder, Person> {

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitPerson(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitPerson(this);
	}

}
