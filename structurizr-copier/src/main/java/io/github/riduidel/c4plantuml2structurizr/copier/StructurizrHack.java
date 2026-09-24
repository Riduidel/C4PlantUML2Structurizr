package io.github.riduidel.c4plantuml2structurizr.copier;

import java.lang.reflect.Method;

import com.structurizr.model.Element;
import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Model;
import com.structurizr.model.Relationship;

public class StructurizrHack {
	private static InteractionStyleFinder interactionStyleFinder = new InteractionStyleFinder();
	static Method addRelationshipMethod;
	static {
		try {
			addRelationshipMethod = Model.class.getDeclaredMethod("addRelationship", Element.class, Element.class,
					String.class, String.class, InteractionStyle.class, String[].class);
			addRelationshipMethod.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Relationship addRelationship(Model into, Element source, Element destination, String description,
			String technology, InteractionStyle interactionStyle, String[] split) {
		try {
			return (Relationship) addRelationshipMethod.invoke(into, source, destination, description, technology,
					interactionStyle, split);
		} catch (Exception e) {
			throw new RuntimeException("Unable to mess with Structurizr", e);
		}
	}

	public static InteractionStyle getInteractionFromTechnology(String technology) {
		return interactionStyleFinder.getInteractionStyleFor(technology);
	}
}