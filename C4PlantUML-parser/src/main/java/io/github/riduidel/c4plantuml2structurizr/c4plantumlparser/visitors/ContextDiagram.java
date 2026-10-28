package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.SystemContextView;

import io.github.riduidel.c4plantuml2structurizr.ConverterConfiguration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.SystemBuilder;

public class ContextDiagram extends BuildableC4PlantUMLDiagram {
	public static class BuildContextDiagramVisitor extends DiagramBuilderVisitor {

		public BuildContextDiagramVisitor(File source, ConverterConfiguration configuration) {
			// If null is given as argument, a new Workspace will be created
			super(null, source, configuration);
		}

		/**
		 * When we stop visiting a software system, if containing diagram is this diagram (as defined by the source file)
		 * we generate a view for that software system
		 */
		@Override
		public Workspace endVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
			if(isDeclaredInDiagram()) {
				// software system is declared in file, so add the corresponding view
				SystemContextView view = returned.getViews().createSystemContextView(
						(SoftwareSystem) stack.peek(), 
						String.format("context_of_%s", softwareSystemBuilder.getAlias()));
				view.setDescription(String.format("Context view of \"%s\"", softwareSystemBuilder.getLabel()));
			}
			return super.endVisitSoftwareSystem(softwareSystemBuilder);
		}
	}

	public ContextDiagram(File context) {
		super(context);
	}

	@Override
	public Workspace build(ConverterConfiguration configuration) {
		return accept(new BuildContextDiagramVisitor(source, configuration));
	}
}
