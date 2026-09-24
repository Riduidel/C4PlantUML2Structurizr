package io.github.riduidel.c4plantuml2structurizr.copier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.structurizr.PropertyHolder;
import com.structurizr.Workspace;
import com.structurizr.model.Element;
import com.structurizr.model.GroupableElement;
import com.structurizr.model.Model;
import com.structurizr.model.ModelItem;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.Configuration;
import com.structurizr.view.ElementStyle;
import com.structurizr.view.RelationshipStyle;
import com.structurizr.view.Styles;
import com.structurizr.view.ViewSet;

/**
 * This workspace copier allows data from one Structurizr workspace to be copied into another one.
 */
public class WorkspaceCopier {
	private static final Logger logger = Logger.getLogger(WorkspaceCopier.class.getName());
	private StructurizrHack hack = new StructurizrHack();
	
	public WorkspaceCopier() {
		
	}
	
	public WorkspaceCopier(StructurizrHack hack) {
		this.hack = hack;
	}

	void clone(Workspace from, Workspace to) {
		String included = to.getProperties().getOrDefault(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES, "");
		List<String> includedWorkspaces = new ArrayList<>(Arrays.asList(included.split(",")));
		if(from.getName().isBlank())
			throw new UnsupportedOperationException("No workspace should have a blank name");
		if(!includedWorkspaces.contains(from.getName())) {
			logger.fine(String.format("Cloning %s to %s", from, to));
			clone(from.getModel(), to.getModel());
			clone(from.getViews(), to.getViews());
			includedWorkspaces.add(from.getName());
			to.removeProperty(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES);
			to.addProperty(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES, includedWorkspaces.stream().collect(Collectors.joining(",")));
		}
	}

	void clone(ViewSet from, ViewSet to) {
		if (!from.getViews().isEmpty()) {
			logger.warning(String.format("🚫 We voluntarly copy none of the %s views", from.getViews().size()));
		}
		clone(from.getConfiguration(), to.getConfiguration());
	}

	void clone(Configuration from, Configuration to) {
		from.setThemes(to.getThemes());
		clone(from.getStyles(), to.getStyles());
		clonePropertyHolder(from, to);
	}

	void clone(Styles from, Styles to) {
		for (ElementStyle source : from.getElements()) {
			clone(source, to);
		}
		for (RelationshipStyle source : from.getRelationships()) {
			clone(source, to);
		}
	}

	void clone(ElementStyle from, Styles to) {
//if(to.findElementStyle(from.getTag())==null) {
//ElementStyle cloned = to.addElementStyle(from.getTag());
//cloned.copyFrom(from);
//clonePropertyHolder(from, cloned);
//}
	}

	void clone(RelationshipStyle from, Styles to) {
//if(to.findRelationshipStyle(from.getTag())==null) {
//RelationshipStyle cloned = to.addRelationshipStyle(from.getTag());
//cloned.copyFrom(from);
//clonePropertyHolder(from, cloned);
//}
	}

	void clone(Model from, Model to) {
		for (SoftwareSystem system : from.getSoftwareSystems()) {
			clone(system, to);
		}
		for (Person person : from.getPeople()) {
			clone(person, to);
		}
		for (Relationship relationship : from.getRelationships()) {
			clone(relationship, to);
		}
	}

	void clonePropertyHolder(PropertyHolder source, PropertyHolder target) {
		for(Map.Entry<String, String> e : source.getProperties().entrySet()) {
			target.addProperty(e.getKey(), e.getValue());
		}
	}

	void cloneModelItem(ModelItem source, ModelItem target) {
		if (target == null) {
			throw new NullPointerException(String.format("Unable to clone from %s since target is null", source));
		}
		target.addTags(source.getTags().split(","));
		clonePropertyHolder(source, target);
	}

	void cloneGroupableElement(GroupableElement source, GroupableElement target) {
		if (target == null) {
			throw new NullPointerException(String.format("Unable to clone from %s since target is null", source));
		}
		cloneModelItem(source, target);
		target.setGroup(source.getGroup());
	}

	void clone(Relationship relationship, Model into) {
		Element source = into.getElementWithCanonicalName(relationship.getSource().getCanonicalName());
		Element destination = into.getElementWithCanonicalName(relationship.getDestination().getCanonicalName());
		if (source == null || destination == null) {
			logger.fine(String.format(
					"Unable to clone relationship %s because one of its end is null (source=%s, destination=%s)",
					relationship.getDescription(), source, destination));
		} else {
			Relationship cloned = hack.addRelationship(into, source, destination,
					relationship.getDescription(), relationship.getTechnology(), relationship.getInteractionStyle(),
					relationship.getTags().split(","));
//Unfortunatly not accessible
//Relationship cloned = into.addRelationship(source, destination, relationship.getDescription(), relationship.getTechnology(), relationship.getInteractionStyle(), relationship.getTags().split(","));
			if (cloned == null) {
				logger.fine(String.format(
						"There already is a relationship\nfrom %s\nto %s\ndescribed as %s (according to Structurizr). So this one won't be cloned",
						source, destination, relationship.getDescription()));
			} else {
				cloneModelItem(relationship, cloned);
			}
		}
	}

	void clone(SoftwareSystem system, Model into) {
		if (into.getElementWithCanonicalName(system.getCanonicalName()) == null) {
			SoftwareSystem cloned = into.addSoftwareSystem(system.getName(), system.getDescription());
			cloneGroupableElement(system, cloned);
		}
	}

	void clone(Person person, Model into) {
		if (into.getElementWithCanonicalName(person.getCanonicalName()) == null) {
			Person cloned = into.addPerson(person.getName(), person.getDescription());
			cloneGroupableElement(person, cloned);
		}
	}
}
