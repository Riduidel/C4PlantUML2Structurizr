package io.github.riduidel.structurizr.visitor;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.CustomElement;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ComponentView;
import com.structurizr.view.Configuration;
import com.structurizr.view.ContainerView;
import com.structurizr.view.DeploymentView;
import com.structurizr.view.ElementStyle;
import com.structurizr.view.RelationshipStyle;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.SystemLandscapeView;
import com.structurizr.view.ViewSet;

/**
 * Basic workspace visitor, allowing to navigate the model (without handling groups)
 */
public class WorkspaceVisitor {
	protected Visitor visitor;

	public WorkspaceVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	
	public void visit(Workspace workspace) {
		if(visitor.startVisit(workspace)) {
			visitModel(workspace.getModel());
			visitViews(workspace.getViews());
			visitor.endVisit(workspace);
		}
	}

	protected void visitModel(Model model) {
		if(visitor.startVisitModel(model)) {
			doVisitModel(model);
			visitor.endVisitModel(model);
		}
	}

	/**
	 * Visit the model irespectively of groups they're declared in
	 * @param model
	 */
	protected void doVisitModel(Model model) {
		if(visitor.startVisitPeople(model)) {
			for(Person person : model.getPeople()) {
				visitPerson(person);
			}
			visitor.endVisitPeople(model);
		}
		if(visitor.startVisitSoftwareSystemList(model)) {
			for(SoftwareSystem system : model.getSoftwareSystems()) {
				visitSoftwareSystem(system);
			}
			visitor.endVisitSoftwareSystemList(model);
		}
		if(visitor.startVisitDeploymentList(model)) {
			for(DeploymentNode node : model.getDeploymentNodes()) {
				visitDeploymentNode(node);
			}
			visitor.endVisitDeploymentList(model);
		}
		if(visitor.startVisitCustomElementList(model)) {
			for(CustomElement custom : model.getCustomElements()) {
				visitCustomElement(custom);
			}
			visitor.endVisitCustomElementList(model);
		}
	}
	
	protected void visitCustomElement(CustomElement custom) {
		if(visitor.startVisitCustomElement(custom)) {
			visitor.endVisitCustomElement(custom);
		}
	}

	protected void visitDeploymentNode(DeploymentNode node) {
		if(visitor.startVisitDeploymentNode(node)) {
			for(DeploymentNode child : node.getChildren()) {
				visitDeploymentNode(child);
			}
			visitor.endVisitDeploymentNode(node);
			throw new UnsupportedOperationException("Not all node types are handled here");
		}
	}

	protected void visitSoftwareSystem(SoftwareSystem system) {
		if(visitor.startVisitSoftwareSystem(system)) {
			for(Container container : system.getContainers()) {
				visitContainer(container);
			}
			visitor.endVisitSoftwareSystem(system);
		}
	}

	protected void visitContainer(Container container) {
		if(visitor.startVisitContainer(container)) {
			for(Component component : container.getComponents()) {
				visitComponent(component);
			}
			visitor.endVisitContainer(container);
		}
	}

	protected void visitComponent(Component component) {
		if(visitor.startVisitComponent(component)) {
			visitor.endVisitComponent(component);
		}
	}

	protected void visitPerson(Person person) {
		if(visitor.startVisitPerson(person)) {
			visitor.endVisitPerson(person);
		}
	}

	protected void visitViews(ViewSet views) {
		if(visitor.startVisitViewList(views)) {
			visitLandscapeViews(views);
			visitContextViews(views);
			visitContainerViews(views);
			visitComponentViews(views);
			visitDeploymentViews(views);
			visitor.endVisitViewList(views);
		}
		visitViewConfiguration(views.getConfiguration());
	}

	protected void visitViewConfiguration(Configuration configuration) {
		if(visitor.startVisitViewConfiguration(configuration)) {
			visitStyles(configuration.getStyles());
			visitor.endVisitViewConfiguration(configuration);
		}
	}

	protected void visitStyles(Styles styles) {
		if(visitor.startVisitStylesList(styles)) {
			doVisitStyles(styles);
			visitor.endVisitStylesList(styles);
		}
	}

	protected void doVisitStyles(Styles styles) {
		if(visitor.startVisitElementStyleList(styles)) {
			for(ElementStyle e : styles.getElements()) {
				if(visitor.startVisitElementStyle(e)) {
					visitor.endVisitElementStyle(e);
				}
			}
			visitor.endVisitElementStyleList(styles);
		}
		if(visitor.startVisitRelationshipStyleList(styles)) {
			for(RelationshipStyle e : styles.getRelationships()) {
				if(visitor.startVisitRelationshipStyle(e)) {
					visitor.endVisitRelationshipStyle(e);
				}
			}
			visitor.endVisitRelationshipStyleList(styles);
		}
	}

	protected void visitLandscapeViews(ViewSet views) {
		if(visitor.startVisitLandscapeViewList(views)) {
			for(SystemLandscapeView v : views.getSystemLandscapeViews()) {
				if(visitor.startVisitLandscapeView(v)) {
					visitor.endVisitLandscapeView(v);
				}
			}
			visitor.endVisitLandscapeViewList(views);
		}
	}
	
	protected void visitContextViews(ViewSet views) {
		if(visitor.startVisitContextViewList(views)) {
			for(SystemContextView v : views.getSystemContextViews()) {
				if(visitor.startVisitContextView(v)) {
					visitor.endVisitContextView(v);
				}
			}
			visitor.endVisitContextViewList(views);
		}
	}
	
	protected void visitContainerViews(ViewSet views) {
		if(visitor.startVisitContainerViewList(views)) {
			for(ContainerView v : views.getContainerViews()) {
				if(visitor.startVisitContainerView(v)) {
					visitor.endVisitContainerView(v);
				}
			}
			visitor.endVisitContainerViewList(views);
		}
	}
	
	protected void visitComponentViews(ViewSet views) {
		if(visitor.startVisitComponentViewList(views)) {
			for(ComponentView v : views.getComponentViews()) {
				if(visitor.startVisitComponentView(v)) {
					visitor.endVisitComponentView(v);
				}
			}
			visitor.endVisitComponentViewList(views);
		}
	}
	
	protected void visitDeploymentViews(ViewSet views) {
		if(visitor.startVisitDeploymentViewList(views)) {
			for(DeploymentView v : views.getDeploymentViews()) {
				if(visitor.startVisitDeploymentView(v)) {
					visitor.endVisitDeploymentView(v);
				}
			}
			visitor.endVisitDeploymentViewList(views);
		}
	}
}
