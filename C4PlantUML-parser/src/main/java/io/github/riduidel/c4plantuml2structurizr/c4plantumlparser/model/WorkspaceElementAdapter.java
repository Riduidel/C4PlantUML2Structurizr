package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram;

public abstract class WorkspaceElementAdapter<Returned> implements WorkspaceElementVisitor<Returned> {
	
	protected Returned returned = null;

	public WorkspaceElementAdapter() {
	}

	@Override
	public boolean startVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock) {
		throw new UnsupportedOperationException("TODO implement WorkspaceElementAdapter#startVisitPlantUMLBlock");

	}

	@Override
	public Returned endVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock) {
		return returned;
	}

	@Override
	public boolean startVisitInclude(Include include) {
		return true;
	}

	@Override
	public Returned endVisitInclude(Include include) {
		return returned;
	}

	@Override
	public boolean startVisitPerson(PersonBuilder personBuilder) {
		return true;
	}

	@Override
	public Returned endVisitPerson(PersonBuilder personBuilder) {
		return returned;
	}

	@Override
	public boolean startVisitRelationship(RelationshipBuilder relationshipBuilder) {
		return true;
	}

	@Override
	public Returned endVisitRelationship(RelationshipBuilder relationshipBuilder) {
		return returned;
	}

	@Override
	public boolean startVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder) {
		return true;
	}

	@Override
	public Returned endVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder) {
		return returned;
	}

	@Override
	public boolean startVistContextDiagram(ContextDiagram contextDiagram) {
		return true;
	}

	@Override
	public Returned endVisitContextDiagram(ContextDiagram contextDiagram) {
		return returned;
	}
}
