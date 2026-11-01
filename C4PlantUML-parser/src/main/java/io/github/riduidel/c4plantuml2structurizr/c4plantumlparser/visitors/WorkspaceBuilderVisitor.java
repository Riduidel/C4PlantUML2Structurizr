package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.structurizr.PropertyHolder;
import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Element;
import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.StaticStructureElement;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.WorkspaceBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.Include;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.PlantUMLBlock;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementAdapter;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.AbstractBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.BasicBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.ContainerBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.SystemBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ComponentBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ContainerBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ModelElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.PersonBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.RelationshipBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.SystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.ContextDiagram.BuildContextDiagramVisitor;
import io.github.riduidel.c4plantuml2structurizr.copier.StructurizrHack;

public class WorkspaceBuilderVisitor extends WorkspaceElementAdapter<Workspace> {
	private static final Logger logger = Logger.getLogger(WorkspaceBuilderVisitor.class.getName());

	protected ConverterConfiguration configuration;
	
	protected Stack stack = new Stack();

	/**
	 * Specialized (and isolated) diagrams stack.
	 * Allows easy localization of diagram containing an element reference.
	 * This allows easy diagram generation at the Structurizr level (see {@link BuildContextDiagramVisitor} for an example)
	 */
	protected Stack<C4PlantUMLDiagram> diagrams = new Stack<>();

	protected File source;

	public WorkspaceBuilderVisitor(Workspace workspace, File source, ConverterConfiguration configuration) {
		this.source = source;
		this.configuration = configuration;
		this.returned = workspace;
	}
	
	@Override
	public boolean startVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock) {
		if(returned==null) {
			returned = new Workspace(plantUMLBlock.blockName);
			returned.getModel().addProperty(configuration.groupSeparator, "/");
		}
		stack.push(returned.getModel());
		return true;
	}
	
	@Override
	public Workspace endVisitPlantUMLBlock(PlantUMLBlock plantUMLBlock) {
		stack.pop();
		return super.endVisitPlantUMLBlock(plantUMLBlock);
	}
	
	/**
	 * An include should contain an IncludedDiagram which will in turn declare the whole model elements.
	 * BUT BEWARE: There should be some mechanism to prevent them from generating diagram elements, otherwise we may have too much model elements
	 */
	@Override
	public boolean startVisitInclude(Include include) {
		return true;
	}
	
	@Override
	public Workspace endVisitInclude(Include include) {
		return super.endVisitInclude(include);
	}
	
	private <Type extends StaticStructureElement> void associateAlias(ModelElementBuilder<?, Type> builder, Type element) {
		element.addProperty(configuration.variableProperty, builder.getAlias());
	}
	
	private <Type extends StaticStructureElement> void fillModelElement(ModelElementBuilder<?, Type> builder, Type element) {
		associateAlias(builder, element);
		element.setDescription(builder.getDescription());
		element.setUrl(builder.getLink());
		element.addTags(builder.getTags().split(configuration.tagSeparator));
		if (stack.peek() instanceof PropertyHolder) {
			PropertyHolder holder = (PropertyHolder) stack.peek();
			if(holder.getProperties().containsKey(configuration.currentGroupPath)) {
				element.setGroup(holder.getProperties().get(configuration.currentGroupPath));
			}
		}
	}
	
	@Override
	public boolean startVisitPerson(PersonBuilder personBuilder) {
		if(returned.getModel().getPersonWithName(personBuilder.getLabel())==null) {
			Person person = returned.getModel().addPerson(personBuilder.getLabel(), personBuilder.getDescription());
			fillModelElement(personBuilder, person);
		}
		stack.push(returned.getModel().getPersonWithName(personBuilder.getLabel()));
		return true;
	}

	@Override
	public Workspace endVisitPerson(PersonBuilder personBuilder) {
		stack.pop();
		return super.endVisitPerson(personBuilder);
	}
	
	@Override
	public boolean startVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
		if(returned.getModel().getSoftwareSystemWithName(softwareSystemBuilder.getLabel())==null) {
			SoftwareSystem software = returned.getModel().addSoftwareSystem(softwareSystemBuilder.getLabel(), softwareSystemBuilder.getDescription());
			fillModelElement(softwareSystemBuilder, software);
			software.addTags(softwareSystemBuilder.getType());
		}
		stack.push(returned.getModel().getSoftwareSystemWithName(softwareSystemBuilder.getLabel()));
		return true;
	}
	
	@Override
	public Workspace endVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
		stack.pop();
		return super.endVisitSoftwareSystem(softwareSystemBuilder);
	}
	
	@Override
	public boolean startVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder) {
		if(returned.getModel().getSoftwareSystemWithName(softwareSystemBoundaryBuilder.getName())!=null) {
			stack.push(returned.getModel().getSoftwareSystemWithName(softwareSystemBoundaryBuilder.getName()));
			// TODO implement group that is not linked to a softwaresystem
		} else {
			startVisitBoundary(softwareSystemBoundaryBuilder);
		}
		return true;
	}
	
	@Override
	public Workspace endVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder) {
		if(stack.peek() instanceof SoftwareSystem) {
			SoftwareSystem peeked = (SoftwareSystem) stack.peek();
			if(peeked.getName().equals(softwareSystemBoundaryBuilder.getName())) {
				stack.pop();
			}
		} else {
			endVisitBoundary(softwareSystemBoundaryBuilder);
		}
		return super.endVisitSystemBoundary(softwareSystemBoundaryBuilder);
	}

	@Override
	public boolean startVisitContainer(ContainerBuilder containerBuilder) {
		SoftwareSystem containingSystem = (SoftwareSystem) stack.peek();
		if(containingSystem.getContainerWithName(containerBuilder.getLabel())==null) {
			Container created = containingSystem.addContainer(containerBuilder.getLabel());
			fillModelElement(containerBuilder, created);
			created.setTechnology(containerBuilder.getTechnology());
			created.addTags(containerBuilder.getType());
		}
		stack.push(containingSystem.getContainerWithName(containerBuilder.getLabel()));
		return true;
	}

	@Override
	public Workspace endVisitContainer(ContainerBuilder containerBuilder) {
		stack.pop();
		return super.endVisitContainer(containerBuilder);
	}
	
	@Override
	public boolean startVisitContainerBoundary(ContainerBoundaryBuilder containerBoundaryBuilder) {
		SoftwareSystem containingSystem = (SoftwareSystem) stack.peek();

		if(containingSystem.getContainerWithName(containerBoundaryBuilder.getName())!=null) {
			stack.push(containingSystem.getContainerWithName(containerBoundaryBuilder.getName()));
		} else {
			startVisitBoundary(containerBoundaryBuilder);
		}
		return true;
	}
	
	@Override
	public Workspace endVisitContainerBoundary(ContainerBoundaryBuilder containerBoundaryBuilder) {
		if(stack.peek() instanceof Container) {
			Container peeked = (Container) stack.peek();
			if(peeked.getName().equals(containerBoundaryBuilder.getName())) {
				stack.pop();
			} else {
				endVisitBoundary(containerBoundaryBuilder);
			}
		}
		return super.endVisitContainerBoundary(containerBoundaryBuilder);
	}
	
	@Override
	public boolean startVisitComponent(ComponentBuilder componentBuilder) {
		Container containingContainer = (Container) stack.peek();
		if(containingContainer.getComponentWithName(componentBuilder.getLabel())==null) {
			Component created = containingContainer.addComponent(componentBuilder.getLabel());
			fillModelElement(componentBuilder, created);
			created.setTechnology(componentBuilder.getTechnology());
		}
		stack.push(containingContainer.getComponentWithName(componentBuilder.getLabel()));
		return true;
	}
	
	@Override
	public Workspace endVisitComponent(WithTechnology<ComponentBuilder> componentBuilder) {
		stack.pop();
		return super.endVisitComponent(componentBuilder);
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
		failIfBadlyDeclared(relationshipBuilder, Map.of("source", source, "target", target));
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

	private void failIfBadlyDeclared(RelationshipBuilder relationshipBuilder, Map<String, List<Element>> of) {
		String message = of.entrySet().stream()
			.filter(e -> e.getValue().size()!=1)
			.map(e -> String.format("%s end of\n%s -> %s \"%s\"\nis badly declared, since %d elements matched:\n", 
					e.getKey(),
					relationshipBuilder.getSourceAlias(), relationshipBuilder.getTargetAlias(), relationshipBuilder.getDescription(),
					e.getValue().size(),
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
	
	public boolean startVisitDiagram(C4PlantUMLDiagram contextDiagram) {
		stack.push(contextDiagram);
		diagrams.push(contextDiagram);
		return true;
	}
	
	public Workspace endVisitDiagram(C4PlantUMLDiagram contextDiagram) {
		stack.pop();
		diagrams.pop();
		return returned;
	}
	
	@Override
	public boolean startVisitBasicBoundary(BasicBoundaryBuilder basicBoundaryBuilder) {
		startVisitBoundary(basicBoundaryBuilder);
		return super.startVisitBasicBoundary(basicBoundaryBuilder);
	}

	@Override
	public Workspace endVisitBasicBoundary(BasicBoundaryBuilder basicBoundaryBuilder) {
		endVisitBoundary(basicBoundaryBuilder);
		return super.endVisitBasicBoundary(basicBoundaryBuilder);
	}
	
	private void startVisitBoundary(AbstractBoundaryBuilder basicBoundaryBuilder) {
		if (stack.peek() instanceof PropertyHolder) {
			PropertyHolder holder = (PropertyHolder) stack.peek();
			updateCurrentGroupPath(holder, pathFragments -> pathFragments.add(basicBoundaryBuilder.getName()));
		}
	}

	private void updateCurrentGroupPath(PropertyHolder holder, Consumer<List<String>> updater) {
		String separator = returned.getModel().getProperties().get(configuration.groupSeparator);
		Map<String, String> properties = StructurizrHack.getModifiablePropertiesOf(holder);
		String currentPath = properties.getOrDefault(configuration.currentGroupPath, "");
		List<String> pathFragments = currentPath.isBlank() 
				? new ArrayList<String>() 
				: new ArrayList<String>(Arrays.asList(currentPath.split(separator, -1)));
		updater.accept(pathFragments);
		if(pathFragments.isEmpty()) {
			properties.remove(configuration.currentGroupPath);
		} else {
			properties.put(
					configuration.currentGroupPath,
					pathFragments.stream().collect(Collectors.joining(separator)));
		}
	}

	private void endVisitBoundary(AbstractBoundaryBuilder basicBoundaryBuilder) {
		if (stack.peek() instanceof PropertyHolder) {
			PropertyHolder holder = (PropertyHolder) stack.peek();
			updateCurrentGroupPath(holder, pathFragments -> {
				if(basicBoundaryBuilder.getName().equals(pathFragments.getLast())) {
					pathFragments.removeLast();
				} else {
					throw new UnsupportedOperationException(
							String.format("We tried to remove last group path %s from %s, it didn't end well.",
									basicBoundaryBuilder.getName(),
									pathFragments));
				}
			});
		}
	}
}
