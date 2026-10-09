package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors;

import java.io.File;

import com.structurizr.Workspace;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.SystemContextView;

import io.github.riduidel.c4plantuml2structurizr.Configuration;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.SoftwareSystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WorkspaceElementVisitor;

public class ContextDiagram extends C4PlantUMLDiagram {
	public static class BuildContextDiagramVisitor extends WorkspaceBuilderVisitor {

		private File source;

		public BuildContextDiagramVisitor(File source, Configuration configuration) {
			super(configuration);
			this.source = source;
		}

		/**
		 * When we stop visiting a software system, if containing diagram is this diagram (as defined by the source file)
		 * we generate a view for that software system
		 */
		@Override
		public Workspace endVisitSoftwareSystem(SoftwareSystemBuilder softwareSystemBuilder) {
			if(source.equals(diagrams.peek().source)) {
				// software system is declared in file, so add the corresponding view
				SystemContextView view = returned.getViews().createSystemContextView(
						(SoftwareSystem) stack.peek(), 
						String.format("context_of_%s", softwareSystemBuilder.getAlias()));
				view.setDescription(String.format("Context view of \"%s\"", softwareSystemBuilder.getName()));
			}
			return super.endVisitSoftwareSystem(softwareSystemBuilder);
		}
	}

	public ContextDiagram(File context) {
		super(context);
	}

	@Override
	public Workspace build(Configuration configuration) {
		return accept(new BuildContextDiagramVisitor(source, configuration));
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVistContextDiagram(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitContextDiagram(this);
	}
}
