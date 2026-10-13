package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ContainerView;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.boundaries.SystemBoundaryBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.model.SystemBuilder;

public class ContainerDiagram extends C4PlantUMLDiagram {
	public static class BuildContainerDiagramVisitor extends WorkspaceBuilderVisitor {
		private File source;

		public BuildContainerDiagramVisitor(Workspace workspace, File source, Configuration configuration) {
			super(configuration);
			this.returned = workspace;
			this.source = source;
		}
		
		private void createContainerDiagramFor(String alias, String name) {
			if(source.equals(diagrams.peek().source)) {
				// software system is declared in file, so add the corresponding view
				ContainerView view = returned.getViews().createContainerView(
						(SoftwareSystem) stack.peek(), 
						String.format("containers_of_%s", alias));
				view.setDescription(String.format("Container view of \"%s\"", name));
			}
		}

		/**
		 * When we stop visiting a software system, if containing diagram is this diagram (as defined by the source file)
		 * we generate a view for that software system
		 */
		@Override
		public Workspace endVisitSoftwareSystem(SystemBuilder softwareSystemBuilder) {
			createContainerDiagramFor(softwareSystemBuilder.getAlias(), softwareSystemBuilder.getName());
			return super.endVisitSoftwareSystem(softwareSystemBuilder);
		}
		
		@Override
		public Workspace endVisitSystemBoundary(SystemBoundaryBuilder softwareSystemBoundaryBuilder) {
			createContainerDiagramFor(softwareSystemBoundaryBuilder.getAlias(), softwareSystemBoundaryBuilder.getName());
			return super.endVisitSystemBoundary(softwareSystemBoundaryBuilder);
		}
		
	}

	private Workspace returned;

	public ContainerDiagram(Workspace returned, File container) {
		super(container);
		this.returned = returned;
	}

	@Override
	public Workspace build(Configuration configuration) {
		return accept(new BuildContainerDiagramVisitor(returned, source, configuration));
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitContainerDiagram(this);
		
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitContainerDiagram(this);
	}

}
