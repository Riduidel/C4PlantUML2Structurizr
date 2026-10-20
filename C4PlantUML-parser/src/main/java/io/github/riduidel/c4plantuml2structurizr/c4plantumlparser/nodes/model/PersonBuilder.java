package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model;

import com.structurizr.Workspace;
import com.structurizr.model.Person;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLink;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithSprite;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTags;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithType;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class PersonBuilder extends ModelElementBuilder<PersonBuilder, Person>
	implements WithType<PersonBuilder>{

	private String type = "";

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitPerson(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitPerson(this);
	}


	public String getType() {
		return type;
	}

	@Override
	public PersonBuilder withType(String type) {
		this.type = type;
		return (PersonBuilder) this;
	}
}
