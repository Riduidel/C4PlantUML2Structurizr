package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.SystemBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ContainerBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.PersonBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.RelationshipBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.SystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContainerDiagram;
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

	boolean startVisitSoftwareSystem(SystemBuilder softwareSystemBuilder);

	Returned endVisitSoftwareSystem(SystemBuilder softwareSystemBuilder);

	boolean startVistContextDiagram(ContextDiagram contextDiagram);

	Returned endVisitContextDiagram(ContextDiagram contextDiagram);

	boolean startVisitContainerDiagram(ContainerDiagram containerDiagram);

	Returned endVisitContainerDiagram(ContainerDiagram containerDiagram);

	boolean startVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder);

	Returned endVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder);

	boolean startVisitContainer(ContainerBuilder containerBuilder);

	Returned endVisitContainer(ContainerBuilder containerBuilder);

}
