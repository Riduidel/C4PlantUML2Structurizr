package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.structurizr.Workspace;
import com.structurizr.model.Element;
import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.Include;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.ModelElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.PersonBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.PlantUMLBlock;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.RelationshipBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.SoftwareSystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WorkspaceElementAdapter;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram.BuildContextDiagramVisitor;
import io.github.riduidel.c4plantuml2structurizr.copier.StructurizrHack;

public class WorkspaceBuilderVisitor extends WorkspaceElementAdapter<Workspace> {

	private Configuration configuration;
	
	protected Stack stack = new Stack();

	/**
	 * Specialized (and isolated) diagrams stack.
	 * Allows easy localization of diagram containing an element reference.
	 * This allows easy diagram generation at the Structurizr level (see {@link BuildContextDiagramVisitor} for an example)
	 */
	protected Stack<C4PlantUMLDiagram> diagrams = new Stack<>();

	public WorkspaceBuilderVisitor(Configuration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public boolean startVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock) {
		if(returned==null) {
			returned = new Workspace(plantUMLBlock.blockName);
		}
		return true;
	}
	
	@Override
	public boolean startVisitInclude(Include include) {
		// I think I'll have to change that
		return false;
	}
	
	@Override
	public Workspace endVisitInclude(Include include) {
		return super.endVisitInclude(include);
	}
	
	private <Type extends StaticStructureElement> void associateAlias(ModelElementBuilder<?, Type> builder, Type element) {
		element.addProperty(configuration.variableProperty, builder.getAlias());
	}
	
	private <Type extends StaticStructureElement> void createModelElement(ModelElementBuilder<?, Type> builder, Type element) {
		associateAlias(builder, element);
		stack.push(element);
	}
	
	@Override
	public boolean startVisitPerson(PersonBuilder personBuilder) {
		Person person = returned.getModel().addPerson(personBuilder.getName(), personBuilder.getDescription());
		createModelElement(personBuilder, person);
		return true;
	}

	@Override
	public Workspace endVisitPerson(PersonBuilder personBuilder) {
		stack.pop();
		return super.endVisitPerson(personBuilder);
	}
	
	@Override
	public boolean startVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder) {
		SoftwareSystem software = returned.getModel().addSoftwareSystem(softwareSystemBuilder.getName(), softwareSystemBuilder.getDescription());
		createModelElement(softwareSystemBuilder, software);
		return true;
	}
	
	@Override
	public Workspace endVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder) {
		stack.pop();
		return super.endVisitSoftwareSystem(softwareSystemBuilder);
	}
	
	@Override
	public boolean startVisitRelationship(RelationshipBuilder relationshipBuilder) {
		Model model = returned.getModel();
		// Source and target are collections to get all declarations
		List<Element> source = new ArrayList<Element>();
		List<Element> target = new ArrayList<Element>();
		for (Element element : model.getElements()) {
			if(element.getProperties().containsKey(configuration.variableProperty)) {
				String variable = element.getProperties().get(configuration.variableProperty);
				if(relationshipBuilder.getSourceAlias().equals(variable)) {
					source.add(element);
				} else if(relationshipBuilder.getTargetAlias().equals(variable)) {
					target.add(element);
				}
			}
		}
		failIfBadlyDeclared(Map.of("source", source, "target", target));
		// ok, source and target have only one elements, so create the relationship
		Relationship built = new StructurizrHack().addRelationship(model, source.get(0), target.get(0), relationshipBuilder.getDescription(), relationshipBuilder.getTechnology(), InteractionStyle.Synchronous, new String[0]);
		stack.push(built);
		return true;
	}
	
	@Override
	public Workspace endVisitRelationship(RelationshipBuilder relationshipBuilder) {
		stack.pop();
		return super.endVisitRelationship(relationshipBuilder);
	}

	private void failIfBadlyDeclared(Map<String, List<Element>> of) {
		String message = of.entrySet().stream()
			.filter(e -> e.getValue().size()!=1)
			.map(e -> String.format("%s end of %s is badly declared, since %d model elements match\n", 
					e.getKey(),
					this,
					e.getValue().stream()
						.map(element -> element.toString())
						.map(element -> "* "+element)
						.collect(Collectors.joining("\n"))
						))
			.collect(Collectors.joining("\n"));
		if(!message.isBlank()) {
			throw new RuntimeException(message);
		}
	}
	
	@Override
	public boolean startVistContextDiagram(ContextDiagram contextDiagram) {
		return startVisitDiagram(contextDiagram);
	}
	
	private boolean startVisitDiagram(C4PlantUMLDiagram contextDiagram) {
		stack.push(contextDiagram);
		diagrams.push(contextDiagram);
		return true;
	}

	@Override
	public Workspace endVisitContextDiagram(ContextDiagram contextDiagram) {
		return endVisitDiagram(contextDiagram);
	}

	private Workspace endVisitDiagram(C4PlantUMLDiagram contextDiagram) {
		stack.pop();
		diagrams.pop();
		return returned;
	}
}
