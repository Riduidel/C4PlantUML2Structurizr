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
 * Interface allowing visit of a Structurizr workspace.
 */
public interface Visitor {

	boolean startVisit(Workspace workspace);

	void endVisit(Workspace workspace);

	boolean startVisitPeople(Model model);

	void endVisitPeople(Model model);

	boolean startVisitPerson(Person person);

	void endVisitPerson(Person person);

	boolean startVisitSoftwareSystemList(Model model);

	void endVisitSoftwareSystemList(Model model);

	boolean startVisitSoftwareSystem(SoftwareSystem system);

	void endVisitSoftwareSystem(SoftwareSystem system);

	boolean startVisitContainer(Container container);

	void endVisitContainer(Container container);

	boolean startVisitComponent(Component component);

	void endVisitComponent(Component component);

	boolean startVisitDeploymentList(Model model);

	void endVisitDeploymentList(Model model);

	boolean startVisitDeploymentNode(DeploymentNode node);

	void endVisitDeploymentNode(DeploymentNode node);

	boolean startVisitCustomElementList(Model model);

	void endVisitCustomElementList(Model model);

	boolean startVisitCustomElement(CustomElement custom);

	void endVisitCustomElement(CustomElement custom);

	boolean startVisitViewList(ViewSet views);

	void endVisitViewList(ViewSet views);

	boolean startVisitLandscapeViewList(ViewSet views);

	boolean startVisitLandscapeView(SystemLandscapeView v);

	void endVisitLandscapeView(SystemLandscapeView v);

	void endVisitLandscapeViewList(ViewSet views);

	boolean startVisitContextViewList(ViewSet views);

	boolean startVisitContextView(SystemContextView v);

	void endVisitContextView(SystemContextView v);

	void endVisitContextViewList(ViewSet views);

	boolean startVisitContainerViewList(ViewSet views);

	boolean startVisitContainerView(ContainerView v);

	void endVisitContainerView(ContainerView v);

	void endVisitContainerViewList(ViewSet views);

	boolean startVisitComponentViewList(ViewSet views);

	boolean startVisitComponentView(ComponentView v);

	void endVisitComponentView(ComponentView v);

	void endVisitComponentViewList(ViewSet views);

	boolean startVisitDeploymentViewList(ViewSet views);

	boolean startVisitDeploymentView(DeploymentView v);

	void endVisitDeploymentView(DeploymentView v);

	void endVisitDeploymentViewList(ViewSet views);

	boolean startVisitViewConfiguration(Configuration configuration);

	void endVisitViewConfiguration(Configuration configuration);
	
	boolean startVisitStylesList(Styles styles);

	void endVisitStylesList(Styles styles);

	boolean startVisitElementStyle(ElementStyle e);

	void endVisitElementStyle(ElementStyle e);

	boolean startVisitElementStyleList(Styles styles);

	void endVisitElementStyleList(Styles styles);

	boolean startVisitRelationshipStyleList(Styles styles);

	boolean startVisitRelationshipStyle(RelationshipStyle e);

	void endVisitRelationshipStyle(RelationshipStyle e);

	void endVisitRelationshipStyleList(Styles styles);

	boolean startVisitModel(Model model);

	void endVisitModel(Model model);

}
