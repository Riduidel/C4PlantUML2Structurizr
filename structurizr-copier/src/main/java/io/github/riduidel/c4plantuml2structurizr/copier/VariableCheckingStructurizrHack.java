package io.github.riduidel.c4plantuml2structurizr.copier;

import com.structurizr.model.Element;
import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Model;
import com.structurizr.model.Relationship;

/**
 * This class will be moved later in C4PlantUML2Structurizr.
 */
public class VariableCheckingStructurizrHack extends StructurizrHack {
@Override
public Relationship addRelationship(Model into, Element source, Element destination, String description,
		String technology, InteractionStyle interactionStyle, String[] split) {
		try {
			// We make sure here that elements have assoiated variables defined, otherwise things will be hard to debug
			boolean sourceHasVariable = source.getProperties().containsKey(C4PlantUML2Structurizr.Constants.VARIABLE);
			boolean destinationHasVariable = destination.getProperties().containsKey(C4PlantUML2Structurizr.Constants.VARIABLE);
			if(!sourceHasVariable || !destinationHasVariable) {
				throw new UnsupportedOperationException(
						String.format("Unable to add relationship labelled %s between\n"
								+ "source identified as variable %s is %s\n"
								+ "destination identified as variable %s is %s\n", 
								description, 
								sourceHasVariable ? source.getProperties().get(C4PlantUML2Structurizr.Constants.VARIABLE) : "NO VARIABLE",
								source, 
								destinationHasVariable ? destination.getProperties().get(C4PlantUML2Structurizr.Constants.VARIABLE) : "NO VARIABLE",
								destination
								)
						);
			}
			super.addRelationship(into, source, destination, description, technology, interactionStyle, split);
		} catch (Exception e) {
			throw new RuntimeException("Unable to mess with Structurizr", e);
		}
	}
}
