package io.github.riduidel.c4plantuml2structurizr.writer;

import java.beans.Introspector;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
	StringBuilder returned = new StringBuilder();

	public String write(Workspace workspace) {
		WorkspaceVisitor visitor = new WorkspaceVisitor(this);
		visitor.visit(workspace);
		return returned.toString();
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
			returned.append(prefix).append("}").append("\n");
		}
		return returned;
	}
	
	@Override
	public boolean startVisit(Workspace workspace) {
		returned.append(prefix).append("workspace \"").append(workspace.getName()).append("\" {\n");
		indent();
		returned.append(writeProperties(workspace));
		return super.startVisit(workspace);
	}
	
	@Override
	public void endVisit(Workspace workspace) {
		endVisitElement();
		super.endVisit(workspace);
	}

	private void endVisitElement() {
		dedent();
		returned.append(prefix).append("}\n");
	}
	
	@Override
	public boolean startVisitModel(Model model) {
		returned.append(prefix).append("model {\n");
		indent();
		returned.append(writeProperties(model));
		return super.startVisitModel(model);
	}
	
	@Override
	public void endVisitModel(Model model) {
		endVisitElement();
		super.endVisitModel(model);
	}
	
	private void startVisitElement(Element element) {
		returned.append(prefix);
		if(element.getProperties().containsKey("structurizr.dsl.identifier")) {
			returned
				.append(element.getProperties().get("structurizr.dsl.identifier"))
				.append(" = ");
		}
		returned
			.append(Introspector.decapitalize(element.getClass().getSimpleName()))
			.append(" \"").append(element.getName()).append("\" {")
			.append("\n");
		indent();
		returned.append(writeProperties(element));
		if(element.getDescription()!=null && !element.getDescription().isBlank()) {
			returned.append(prefix)
				.append("description \"").append(element.getDescription()).append("\"\n");
		}
		writeTags(element);
		if(element.getUrl()!=null && !element.getUrl().isBlank()) {
			returned.append(prefix)
				.append("url \"").append(element.getUrl()).append("\"\n");
		}
	}

	private void writeTags(Element element) {
		// Structurizr adds to normal tags the element class name (and the "element" tag)
		Set<String> tagsSet = new LinkedHashSet<>(element.getTagsAsSet());
		tagsSet.removeAll(element.getDefaultTags());
		if(!tagsSet.isEmpty()) {
			tags = tagsSet.stream().collect(Collectors.joining(","));
			returned.append(prefix)
				.append("tags \"").append(tags).append("\"\n");
		}
	}
	
	@Override
	public boolean startVisitPerson(Person person) {
		startVisitElement(person);
		return super.startVisitPerson(person);
	}
	@Override
	public void endVisitPerson(Person person) {
		endVisitElement();
		super.endVisitPerson(person);
	}
	
	@Override
	public boolean startVisitViewList(ViewSet views) {
		returned.append(prefix).append("views {\n");
		indent();
		return super.startVisitViewList(views);
	}
	
	@Override
	public void endVisitViewList(ViewSet views) {
		endVisitElement();
		super.endVisitViewList(views);
	}

	void indent() {
		prefix += "\t";
	}
	void dedent() {
		prefix = prefix.substring(0, prefix.length()-1);
	}
}
