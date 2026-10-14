package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.ContainerBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.SystemBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ComponentBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ContainerBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.PersonBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.RelationshipBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.SystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContainerDiagram;
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
	public boolean startVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
		return true;
	}

	@Override
	public Returned endVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
		return returned;
	}
	
	@Override
	public boolean startVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder) {
		return true;
	}
	
	@Override
	public Returned endVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder) {
		return returned;
	}
	
	@Override
	public boolean startVisitContainer(ContainerBuilder containerBuilder) {
		return true;
	}
	
	@Override
	public Returned endVisitContainer(ContainerBuilder containerBuilder) {
		return returned;
	}
	
	@Override
	public boolean startVisitContainerBoundary(ContainerBoundaryBuilder containerBoundaryBuilder) {
		return true;
	}
	
	@Override
	public Returned endVisitContainerBoundary(ContainerBoundaryBuilder containerBoundaryBuilder) {
		return returned;
	}
	
	@Override
	public boolean startVisitComponent(ComponentBuilder componentBuilder) {
		return true;
	}
	
	@Override
	public Returned endVisitComponent(ComponentBuilder componentBuilder) {
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
	
	@Override
	public boolean startVisitContainerDiagram(ContainerDiagram containerDiagram) {
		return true;
	}
	
	@Override
	public Returned endVisitContainerDiagram(ContainerDiagram containerDiagram) {
		return returned;
	}
}
