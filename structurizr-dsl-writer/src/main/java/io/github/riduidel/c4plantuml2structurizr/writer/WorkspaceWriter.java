package io.github.riduidel.c4plantuml2structurizr.writer;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.structurizr.PropertyHolder;
import com.structurizr.Workspace;
import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.view.ViewSet;

import io.github.riduidel.structurizr.visitor.FullVisitorAdapter;
import io.github.riduidel.structurizr.visitor.WorkspaceVisitor;

public class WorkspaceWriter extends FullVisitorAdapter {
	String prefix = "";
	Stack<List<StringBuilder>> fragments = new Stack<>();
	private String fullString;

	public String write(Workspace workspace) {
		WorkspaceVisitor visitor = new WorkspaceVisitor(this);
		visitor.visit(workspace);
		return fullString;
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
		endVisitElement(
				true,
				new StringBuilder().append("workspace \"").append(workspace.getName()).append("\""), writeProperties(workspace)
				);
		super.endVisit(workspace);
	}
	private void endVisitElement(boolean force, StringBuilder declarationLine, StringBuilder...localTags) {
		endVisitElement(force, declarationLine, Arrays.asList(localTags));
	}

	private void endVisitElement(boolean force, StringBuilder declarationLine, List<StringBuilder> localTags) {
		dedent();
		List<StringBuilder> content = new ArrayList<>();
		content.addAll(localTags);
		content.addAll(fragments.pop());
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
		endVisitElement(
				true,
				new StringBuilder().append("model"), writeProperties(model)
				);
		super.endVisitModel(model);
	}

	private StringBuilder writeUrl(Element element) {
		StringBuilder returned = new StringBuilder();
		if(element.getUrl()!=null && !element.getUrl().isBlank()) {
			returned.append(prefix)
				.append("url \"").append(element.getUrl()).append("\"\n");
		}
		return returned;
	}

	private StringBuilder writeDescription(Element element) {
		StringBuilder returned = new StringBuilder();
		if(element.getDescription()!=null && !element.getDescription().isBlank()) {
			returned.append(prefix)
				.append("description \"").append(element.getDescription()).append("\"\n");
		}
		return returned;
	}

	private StringBuilder writeDeclaration(Element element) {
		StringBuilder returned = new StringBuilder();
		if(element.getProperties().containsKey("structurizr.dsl.identifier")) {
			returned
				.append(element.getProperties().get("structurizr.dsl.identifier"))
				.append(" = ");
		}
		returned
			.append(Introspector.decapitalize(element.getClass().getSimpleName()))
			.append(" \"").append(element.getName()).append("\"");
		return returned;
	}

	private StringBuilder writeTags(Element element) {
		StringBuilder returned = new StringBuilder();
		// Structurizr adds to normal tags the element class name (and the "element" tag)
		Set<String> tagsSet = new LinkedHashSet<>(element.getTagsAsSet());
		tagsSet.removeAll(element.getDefaultTags());
		if(!tagsSet.isEmpty()) {
			String tags = tagsSet.stream().collect(Collectors.joining(","));
			returned.append(prefix)
				.append("tags \"").append(tags).append("\"\n");
		}
		return returned;
	}

	@Override
	public boolean startVisitPerson(Person person) {
		startVisitElement();
		return super.startVisitPerson(person);
	}
	@Override
	public void endVisitPerson(Person person) {
		endVisitElement(false,
				writeDeclaration(person),
				writeProperties(person),
				writeDescription(person),
				writeUrl(person), writeTags(person));
		super.endVisitPerson(person);
	}
	
	@Override
	public boolean startVisitViewList(ViewSet views) {
		startVisitElement();
		return super.startVisitViewList(views);
	}
	
	@Override
	public void endVisitViewList(ViewSet views) {
		endVisitElement(true, new StringBuilder().append("views"));
		super.endVisitViewList(views);
	}

	void indent() {
		prefix += "\t";
	}
	void dedent() {
		prefix = prefix.substring(0, prefix.length()-1);
	}
}
