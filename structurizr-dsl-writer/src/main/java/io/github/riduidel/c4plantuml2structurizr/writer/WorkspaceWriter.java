package io.github.riduidel.c4plantuml2structurizr.writer;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import com.structurizr.PropertyHolder;
import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.ModelItem;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.StaticView;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

import io.github.riduidel.structurizr.visitor.FullVisitorAdapter;
import io.github.riduidel.structurizr.visitor.GroupAwareVisitor;
import io.github.riduidel.structurizr.visitor.group.GroupAwareWorkspaceVisitor;

public class WorkspaceWriter extends FullVisitorAdapter implements GroupAwareVisitor {
	String prefix = "";
	Stack<List<StringBuilder>> fragments = new Stack<>();
	private String fullString;

	public String write(Workspace workspace) {
		GroupAwareWorkspaceVisitor visitor = new GroupAwareWorkspaceVisitor(this);
		visitor.visit(workspace);
		// Finally, remove all empty lines
		return fullString.replace("\n\n", "\n");
	}

	private void startVisitElement() {
		indent();
		fragments.push(new ArrayList<>());
	}

	public StringBuilder writeProperties(PropertyHolder holder) {
		StringBuilder returned = new StringBuilder();
		Map<String, String> properties = holder.getProperties().entrySet().stream()
				.filter(e -> !e.getKey().startsWith("structurizr.dsl"))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		// we first filter out structurizr properties
		// And we should add some way to configure that, but later
		if(!properties.isEmpty()) {
			returned.append(prefix).append("properties {").append("\n");
			indent();
			for(Map.Entry<String, String> e : properties.entrySet()) {
				returned.append(prefix)
					.append("\"").append(e.getKey()).append("\"")
					.append(" ")
					.append("\"").append(e.getValue()).append("\"")
					.append("\n")
					;
			}
			dedent();
			returned.append(prefix).append("}");
		}
		return returned;
	}
	
	@Override
	public boolean startVisit(Workspace workspace) {
		startVisitElement();
		return super.startVisit(workspace);
	}
	
	@Override
	public void endVisit(Workspace workspace) {
		writeElementInFragments(
				true,
				new StringBuilder().append("workspace \"").append(workspace.getName()).append("\""), writeProperties(workspace)
				);
		super.endVisit(workspace);
	}
	/**
	 * Write current element in parent level fragments
	 * @param force when set to true, the "{...}" pair is always written
	 * @param declarationLine declaration line of the element
	 * @param localTags local tags to write before writing children
	 */
	private void writeElementInFragments(boolean force, StringBuilder declarationLine, StringBuilder...localTags) {
		writeElementInFragments(force, declarationLine, Arrays.asList(localTags), Arrays.asList());
	}

	/**
	 * Write the element in parent level fragments, for continuous accumulation
	 * @param force when set to true, the "{...}" pair is always written
	 * @param declarationLine declaration line of the element
	 * @param prefixLines lines to write BEFORE writing current {@link #fragments} lines
	 * @param suffixLines lines to write AFTER writing current {@link #fragments} lines
	 */
	private void writeElementInFragments(boolean force, 
			StringBuilder declarationLine, 
			List<StringBuilder> prefixLines,
			List<StringBuilder> suffixLines) {
		dedent();
		List<StringBuilder> content = new ArrayList<>();
		content.addAll(prefixLines);
		content.addAll(fragments.pop());
		content.addAll(suffixLines);
		content = content.stream()
				.filter(builder -> !builder.isEmpty())
				.collect(Collectors.toList());
		StringBuilder returned = new StringBuilder();
		returned.append(prefix).append(declarationLine);
		if(content.isEmpty()) {
			if(force) {
				returned.append(" {\n").append(prefix).append("}");
			}
		} else {
			returned.append(content.stream().collect(Collectors.joining("\n", " {\n", "\n"+prefix+"}")));
		}
		if(fragments.isEmpty()) {
			fullString = returned.toString();
		} else {
			fragments.peek().add(returned);
		}
	}
	
	@Override
	public boolean startVisitModel(Model model) {
		startVisitElement();
		return super.startVisitModel(model);
	}
	
	@Override
	public void endVisitModel(Model model) {
		for(Relationship r : model.getRelationships()) {
			writeRelationship(r);
		}
		writeElementInFragments(
				true,
				new StringBuilder("model"), 
				writeProperties(model)
				);
		super.endVisitModel(model);
	}

	private StringBuilder writeUrl(ModelItem element) {
		return writeNonNull("url", element.getUrl());
	}
	private StringBuilder writePerspectives(ModelItem element) {
		StringBuilder returned = new StringBuilder();
		if(element.getPerspectives()!=null) {
			if(!element.getPerspectives().isEmpty()) {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		}
		return returned;
	}

	private StringBuilder writeDescription(Element element) {
		String description = element.getDescription();
		return writeDescription(description);
	}

	private StringBuilder writeDescription(String description) {
		return writeNonNull("description", description);
	}

	private StringBuilder writeTechnology(String description) {
		return writeNonNull("technology", description);
	}

	private StringBuilder writeNonNull(String tag, String value) {
		StringBuilder returned = new StringBuilder();
		if(value!=null && !value.isBlank()) {
			returned.append(prefix)
				.append(tag).append(" \"").append(StringEscapeUtils.escapeJava(value)).append("\"\n");
		}
		return returned;
	}
	
	private Optional<String> getVariableName(Element element) {
		return Optional.ofNullable(element.getProperties().get("structurizr.dsl.identifier"));
	}

	private StringBuilder writeDeclaration(Element element) {
		StringBuilder returned = new StringBuilder();
		returned.append(getVariableName(element)
			.map(name -> name + " = ")
			.orElse(""));
		returned
			.append(Introspector.decapitalize(element.getClass().getSimpleName()))
			.append(" \"").append(element.getName()).append("\"");
		return returned;
	}

	private StringBuilder writeTags(ModelItem element) {
		StringBuilder returned = new StringBuilder();
		// Structurizr adds to normal tags the element class name (and the "element" tag)
		Set<String> tagsSet = new LinkedHashSet<>(element.getTagsAsSet());
		tagsSet.removeAll(element.getDefaultTags());
		String tags = tagsSet.stream().collect(Collectors.joining(","));
		returned.append(writeNonNull("tags", tags));
		return returned;
	}
	
	private void writeElementInFragments(Element element) {
		List<StringBuilder> elementParametersList = buildModelElementParameters(element);
		writeElementInFragments(element, elementParametersList);
	}

	private List<StringBuilder> buildModelElementParameters(Element element) {
		List<StringBuilder> elementParametersList = new ArrayList<StringBuilder>(Arrays.asList(
			writeProperties(element),
			writeDescription(element),
			writeUrl(element), 
			writeTags(element)
		));
		return elementParametersList;
	}

	private void writeElementInFragments(Element element, List<StringBuilder> elementParametersList) {
		StringBuilder[] elementParameters = elementParametersList.toArray(new StringBuilder[elementParametersList.size()]);
		writeElementInFragments(false,
				writeDeclaration(element),
				elementParameters);
	}

	@Override
	public boolean startVisitPerson(Person person) {
		startVisitElement();
		return super.startVisitPerson(person);
	}
	@Override
	public void endVisitPerson(Person person) {
		writeElementInFragments(person);
		super.endVisitPerson(person);
	}
	
	@Override
	public boolean startVisitSoftwareSystem(SoftwareSystem system) {
		startVisitElement();
		return super.startVisitSoftwareSystem(system);
	}
	
	@Override
	public void endVisitSoftwareSystem(SoftwareSystem system) {
		writeElementInFragments(system);
		super.endVisitSoftwareSystem(system);
	}
	
	@Override
	public boolean startVisitContainer(Container container) {
		startVisitElement();
		return super.startVisitContainer(container);
	}
	
	@Override
	public void endVisitContainer(Container container) {
		List<StringBuilder> elementParameters = buildModelElementParameters(container);
		elementParameters.add(writeTechnology(container.getTechnology()));
		writeElementInFragments(container, elementParameters);
		super.endVisitContainer(container);
	}
	
	@Override
	public boolean startVisitComponent(Component component) {
		startVisitElement();
		return super.startVisitComponent(component);
	}
	
	@Override
	public void endVisitComponent(Component component) {
		List<StringBuilder> elementParameters = buildModelElementParameters(component);
		elementParameters.add(writeTechnology(component.getTechnology()));
		writeElementInFragments(component, elementParameters);
		super.endVisitComponent(component);
	}
	
	@Override
	public boolean startVisitViewList(ViewSet views) {
		startVisitElement();
		return super.startVisitViewList(views);
	}
	
	@Override
	public void endVisitViewList(ViewSet views) {
		writeElementInFragments(true, new StringBuilder().append("views"));
		super.endVisitViewList(views);
	}

	void indent() {
		prefix += "\t";
	}
	void dedent() {
		prefix = prefix.substring(0, prefix.length()-1);
	}

	@Override
	public boolean startVisitGroup(List<String> nextPath) {
		startVisitElement();
		return true;
	}

	@Override
	public void endVisitGroup(List<String> nextPath) {
		writeElementInFragments(false,
				new StringBuilder("group")
					.append(" \"").append(nextPath.getLast()) .append("\"")
				);
	}

	private void writeRelationship(Relationship relationship) {
		startVisitElement();
		StringBuilder declarationLine = new StringBuilder();
		declarationLine.append(getVariableName(relationship.getSource()).get());
		declarationLine.append(" -> ");
		declarationLine.append(getVariableName(relationship.getDestination()).get());
		List<String> elements = Arrays.asList(relationship.getDescription(), relationship.getTechnology());
		Collections.reverse(elements);
		elements = elements
			.stream()
			.dropWhile(s -> s==null || s.isBlank())
			.map(s -> "\""+s+"\"")
			.collect(Collectors.toCollection(() -> new ArrayList<>()));
		Collections.reverse(elements);
		if(!elements.isEmpty()) {
			declarationLine.append(" ");
		}
		declarationLine.append(elements.stream()
			.collect(Collectors.joining(" ")));
		
		writeElementInFragments(false, 
				declarationLine,
				writeTags(relationship),
				writeUrl(relationship),
				writeProperties(relationship),
				writePerspectives(relationship));
	}
	
	@Override
	public boolean startVisitContextView(SystemContextView v) {
		startVisitElement();
		return super.startVisitContextView(v);
	}
	
	@Override
	public void endVisitContextView(SystemContextView v) {
		writeStaticView(v,
				new StringBuilder().append("systemContext")
					.append(" ").append(getVariableName(v.getSoftwareSystem()).orElseThrow(
							() -> new RuntimeException(String.format("Unable to write view %s refering to variable-less software system %s", 
									v.getKey(),
									v.getSoftwareSystem()))))
					.append(" ").append(v.getKey()));
	}
	
	@Override
	public boolean startVisitContainerView(ContainerView v) {
		startVisitElement();
		return super.startVisitContainerView(v);
	}
	
	@Override
	public void endVisitContainerView(ContainerView v) {
		writeStaticView(v,
				new StringBuilder().append("container")
					.append(" ").append(getVariableName(v.getSoftwareSystem()).orElseThrow(
							() -> new RuntimeException(String.format("Unable to write view %s refering to variable-less software system %s", 
									v.getKey(),
									v.getSoftwareSystem()))))
					.append(" ").append(v.getKey()));
	}
	
	@Override
	public boolean startVisitComponentView(ComponentView v) {
		startVisitElement();
		return super.startVisitComponentView(v);
	}
	
	@Override
	public void endVisitComponentView(ComponentView v) {
		writeStaticView(v,
				new StringBuilder().append("component")
					.append(" ").append(getVariableName(v.getContainer()).orElseThrow(
							() -> new RuntimeException(String.format("Unable to write view %s refering to variable-less container %s", 
									v.getKey(),
									v.getContainer()))))
					.append(" ").append(v.getKey()));
	}

	private void writeStaticView(StaticView staticView, StringBuilder declarationLine) {
		writeElementInFragments(true, 
				declarationLine,
				writeProperties(staticView),
				writeDescription(staticView.getDescription()));
	}
}
