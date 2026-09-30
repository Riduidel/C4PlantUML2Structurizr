package io.github.riduidel.structurizr.visitor.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.CustomElement;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.GroupableElement;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;

import io.github.riduidel.structurizr.visitor.GroupAwareVisitor;
import io.github.riduidel.structurizr.visitor.WorkspaceVisitor;

/**
 * workspace visitor executor aware of groups. This is specific because groups
 * in Structurizr don't exist. Instead, each element has its path stored in
 * model. However, it may sometimes be interesting to have a visitor being aware
 * of groups structure.
 */
public class GroupAwareWorkspaceVisitor extends WorkspaceVisitor {
	class ComponentDescription extends GroupableElementDescription<Component> {

		public ComponentDescription(Component t) {
			super(t);
		}

		@Override
		public void visit() {
			visitComponent(described);
		}
	}

	class ContainerDescription extends GroupableElementDescription<Container> {

		public ContainerDescription(Container t) {
			super(t);
		}

		@Override
		public void visit() {
			visitContainer(described);
		}
	}

	class CustomNodeDescription extends GroupableElementDescription<CustomElement> {

		public CustomNodeDescription(CustomElement t) {
			super(t);
		}

		@Override
		public void visit() {
			visitCustomElement(described);
		}
	}

	class DeploymentNodeDescription extends GroupableElementDescription<DeploymentNode> {

		public DeploymentNodeDescription(DeploymentNode node) {
			super(node);
		}

		@Override
		public void visit() {
			visitDeploymentNode(described);
		}
	}

	abstract class GroupableElementDescription<Type extends GroupableElement> {
		final Type described;
		protected GroupableElementDescription(Type t) {
			described = t;
		}
		public String getGroupPath() {
			return described.getGroup();
		}
		public abstract void visit();
	}

	private class GroupedElementList {
		List<GroupableElementDescription> elements = new ArrayList<>();
		Map<String, GroupedElementList> subPaths = new TreeMap<>();
		public void putInPath(GroupableElementDescription description) {
			String path = description.getGroupPath();
			if(path==null) {
				putInPath(Arrays.asList(), description);
			} else {
				putInPath(Arrays.asList(path.split(pathSeparator)), description);
			}
		}
		private void putInPath(List<String> asList, GroupableElementDescription description) {
			if(asList.isEmpty()) {
				elements.add(description);
			} else {
				String node = asList.get(0);
				List<String> remainingPath = asList.subList(1, asList.size());
				if(!subPaths.containsKey(node)) {
					subPaths.put(node, new GroupedElementList());
				}
				subPaths.get(node).putInPath(remainingPath, description);
			}
		}
		/**
		 * As a convention, we always visit sub nodes before visiting contained elements
		 */
		public void visit() {
			visit(Arrays.asList());
		}
		private void visit(List<String> path) {
			for(Map.Entry<String, GroupedElementList> entry : subPaths.entrySet()) {
				List<String> nextPath = new ArrayList<String>();
				nextPath.addAll(path);
				nextPath.add(entry.getKey());
				if(((GroupAwareVisitor)visitor).startVisitGroup(nextPath)) {
					entry.getValue().visit(nextPath);
					((GroupAwareVisitor)visitor).endVisitGroup(nextPath);
				}
			}
			for(GroupableElementDescription description : elements) {
				description.visit();
			}
		}
	}

	class PersonDescription extends GroupableElementDescription<Person> {

		public PersonDescription(Person person) {
			super(person);
		}

		@Override
		public void visit() {
			visitPerson(described);
		}
	}

	class SoftwareSystemDescription extends GroupableElementDescription<SoftwareSystem> {

		public SoftwareSystemDescription(SoftwareSystem system) {
			super(system);
		}

		@Override
		public void visit() {
			visitSoftwareSystem(described);
		}
	}

	private String pathSeparator = "/";
	
	public GroupAwareWorkspaceVisitor(GroupAwareVisitor visitor) {
		super(visitor);
	}
	
	/**
	 * BEWARE: In that mode, the various startVisit*List won't be invoked around
	 * specific element invocation (because the group management requires
	 * adaptation)
	 */
	@Override
	protected void doVisitModel(Model model) {
		pathSeparator = model.getProperties().getOrDefault("structurizr.groupSeparator", "/");
		List<GroupableElementDescription> ungroupedElements = new ArrayList<>();
		if (visitor.startVisitPeople(model)) {
			for (Person person : model.getPeople()) {
				ungroupedElements.add(new PersonDescription(person));
			}
			visitor.endVisitPeople(model);
		}
		if (visitor.startVisitSoftwareSystemList(model)) {
			for (SoftwareSystem system : model.getSoftwareSystems()) {
				ungroupedElements.add(new SoftwareSystemDescription(system));
			}
			visitor.endVisitSoftwareSystemList(model);
		}
		if (visitor.startVisitDeploymentList(model)) {
			for (DeploymentNode node : model.getDeploymentNodes()) {
				ungroupedElements.add(new DeploymentNodeDescription( node));
			}
			visitor.endVisitDeploymentList(model);
		}
		if (visitor.startVisitCustomElementList(model)) {
			for (CustomElement custom : model.getCustomElements()) {
				ungroupedElements.add(new CustomNodeDescription(custom));
			}
			visitor.endVisitCustomElementList(model);
		}
		visitGroupableElements(ungroupedElements);
	}

	/**
	 * Perform specific grouped visit of all elements
	 * @param ungroupedElements
	 */
	private void visitGroupableElements(List<? extends GroupableElementDescription> ungroupedElements) {
		GroupedElementList root = new GroupedElementList();
		for(GroupableElementDescription description : ungroupedElements) {
			root.putInPath(description);
		}
		root.visit();
	}
	
	@Override
	protected void visitSoftwareSystem(SoftwareSystem system) {
		if(visitor.startVisitSoftwareSystem(system)) {
			visitGroupableElements(system.getContainers().stream()
					.map(ContainerDescription::new)
					.toList());
			visitor.endVisitSoftwareSystem(system);
		}
	}
	
	@Override
	protected void visitContainer(Container container) {
		if(visitor.startVisitContainer(container)) {
			visitGroupableElements(container.getComponents().stream()
					.map(ComponentDescription::new)
					.toList());
			visitor.endVisitContainer(container);
		}
	}
	
	@Override
	protected void visitDeploymentNode(DeploymentNode node) {
		if(visitor.startVisitDeploymentNode(node)) {
			visitGroupableElements(node.getChildren().stream()
					.map(DeploymentNodeDescription::new)
					.toList());
			visitor.endVisitDeploymentNode(node);
			throw new UnsupportedOperationException("Not all node types are handled here");
		}
	}
}
