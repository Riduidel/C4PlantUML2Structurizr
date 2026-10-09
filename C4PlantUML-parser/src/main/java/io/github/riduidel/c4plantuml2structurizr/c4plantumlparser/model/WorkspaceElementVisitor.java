package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram;

public interface WorkspaceElementVisitor<Returned> {

	boolean startVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock);

	Returned endVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock);

	boolean startVisitInclude(Include include);

	Returned endVisitInclude(Include include);

	boolean startVisitPerson(PersonBuilder personBuilder);

	Returned endVisitPerson(PersonBuilder personBuilder);

	boolean startVisitRelationship(RelationshipBuilder relationshipBuilder);

	Returned endVisitRelationship(RelationshipBuilder relationshipBuilder);

	boolean startVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder);

	Returned endVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder);

	boolean startVistContextDiagram(ContextDiagram contextDiagram);

	Returned endVisitContextDiagram(ContextDiagram contextDiagram);

}
