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
 * A default do-nothing visitor that visit each and every element of the workspace
 */
public class FullVisitorAdapter implements Visitor {

	@Override
	public boolean startVisit(Workspace workspace) {
		return true;
	}

	@Override
	public void endVisit(Workspace workspace) {

	}

	@Override
	public boolean startVisitPeople(Model model) {
		return true;
	}

	@Override
	public void endVisitPeople(Model model) {

	}

	@Override
	public boolean startVisitPerson(Person person) {
		return true;
	}

	@Override
	public void endVisitPerson(Person person) {

	}

	@Override
	public boolean startVisitSoftwareSystemList(Model model) {
		return true;
	}

	@Override
	public void endVisitSoftwareSystemList(Model model) {

	}

	@Override
	public boolean startVisitSoftwareSystem(SoftwareSystem system) {
		return true;
	}

	@Override
	public void endVisitSoftwareSystem(SoftwareSystem system) {

	}

	@Override
	public boolean startVisitContainer(Container container) {
		return true;
	}

	@Override
	public void endVisitContainer(Container container) {

	}

	@Override
	public boolean startVisitComponent(Component component) {
		return true;
	}

	@Override
	public void endVisitComponent(Component component) {

	}

	@Override
	public boolean startVisitDeploymentList(Model model) {
		return true;
	}

	@Override
	public void endVisitDeploymentList(Model model) {

	}

	@Override
	public boolean startVisitDeploymentNode(DeploymentNode node) {
		return true;
	}

	@Override
	public void endVisitDeploymentNode(DeploymentNode node) {

	}

	@Override
	public boolean startVisitCustomElementList(Model model) {
		return true;
	}

	@Override
	public void endVisitCustomElementList(Model model) {

	}

	@Override
	public boolean startVisitCustomElement(CustomElement custom) {
		return true;
	}

	@Override
	public void endVisitCustomElement(CustomElement custom) {

	}

	@Override
	public boolean startVisitViewList(ViewSet views) {
		return true;
	}

	@Override
	public void endVisitViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitLandscapeViewList(ViewSet views) {
		return true;
	}

	@Override
	public boolean startVisitLandscapeView(SystemLandscapeView v) {
		return true;
	}

	@Override
	public void endVisitLandscapeView(SystemLandscapeView v) {

	}

	@Override
	public void endVisitLandscapeViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitContextViewList(ViewSet views) {
		return true;
	}

	@Override
	public boolean startVisitContextView(SystemContextView v) {
		return true;
	}

	@Override
	public void endVisitContextView(SystemContextView v) {

	}

	@Override
	public void endVisitContextViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitContainerViewList(ViewSet views) {
		return true;
	}

	@Override
	public boolean startVisitContainerView(ContainerView v) {
		return true;
	}

	@Override
	public void endVisitContainerView(ContainerView v) {

	}

	@Override
	public void endVisitContainerViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitComponentViewList(ViewSet views) {
		return true;
	}

	@Override
	public boolean startVisitComponentView(ComponentView v) {
		return true;
	}

	@Override
	public void endVisitComponentView(ComponentView v) {

	}

	@Override
	public void endVisitComponentViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitDeploymentViewList(ViewSet views) {
		return true;
	}

	@Override
	public boolean startVisitDeploymentView(DeploymentView v) {
		return true;
	}

	@Override
	public void endVisitDeploymentView(DeploymentView v) {

	}

	@Override
	public void endVisitDeploymentViewList(ViewSet views) {

	}

	@Override
	public boolean startVisitViewConfiguration(Configuration configuration) {
		return true;
	}

	@Override
	public void endVisitViewConfiguration(Configuration configuration) {

	}

	@Override
	public boolean startVisitStylesList(Styles styles) {
		return true;
	}

	@Override
	public void endVisitStylesList(Styles styles) {

	}

	@Override
	public boolean startVisitElementStyle(ElementStyle e) {
		return true;
	}

	@Override
	public void endVisitElementStyle(ElementStyle e) {

	}

	@Override
	public boolean startVisitElementStyleList(Styles styles) {
		return true;
	}

	@Override
	public void endVisitElementStyleList(Styles styles) {

	}

	@Override
	public boolean startVisitRelationshipStyleList(Styles styles) {
		return true;
	}

	@Override
	public boolean startVisitRelationshipStyle(RelationshipStyle e) {
		return true;
	}

	@Override
	public void endVisitRelationshipStyle(RelationshipStyle e) {

	}

	@Override
	public void endVisitRelationshipStyleList(Styles styles) {

	}

}
