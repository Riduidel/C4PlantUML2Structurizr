package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;
import com.structurizr.model.Container;
import com.structurizr.view.ComponentView;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.ContainerBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.ContainerBuilder;

public class ComponentDiagram extends C4PlantUMLDiagram {
	public static class BuildComponentDiagramVisitor extends WorkspaceBuilderVisitor {
		private File source;

		public BuildComponentDiagramVisitor(Workspace workspace, File source, Configuration configuration) {
			super(configuration);
			this.returned = workspace;
			this.source = source;
		}
		
		private void createComponentDiagramFor(String alias, String name) {
			if(source.equals(diagrams.peek().source)) {
				// software system is declared in file, so add the corresponding view
				ComponentView view = returned.getViews().createComponentView(
						(Container) stack.peek(), 
						String.format("components_of_%s", alias));
				view.setDescription(String.format("Component view of \"%s\"", name));
			}
		}

		/**
		 * When we stop visiting a software system, if containing diagram is this diagram (as defined by the source file)
		 * we generate a view for that software system
		 */
		@Override
		public Workspace endVisitContainer(ContainerBuilder containerBuilder) {
			createComponentDiagramFor(containerBuilder.getAlias(), containerBuilder.getLabel());
			return super.endVisitContainer(containerBuilder);
		}
		
		@Override
		public Workspace endVisitContainerBoundary(ContainerBoundaryBuilder containerBoundaryBuilder) {
			createComponentDiagramFor(containerBoundaryBuilder.getAlias(), containerBoundaryBuilder.getName());
			return super.endVisitContainerBoundary(containerBoundaryBuilder);
		}
		
	}

	private Workspace returned;

	public ComponentDiagram(Workspace returned, File container) {
		super(container);
		this.returned = returned;
	}

	@Override
	public Workspace build(Configuration configuration) {
		return accept(new BuildComponentDiagramVisitor(returned, source, configuration));
	}
}
