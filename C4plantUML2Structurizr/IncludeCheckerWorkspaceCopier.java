package io.github.riduidel.c4plantuml2structurizr.copier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.structurizr.Workspace;

public class IncludeCheckerWorkspaceCopier extends WorkspaceCopier {

	public IncludeCheckerWorkspaceCopier() {
		super();
	}

	public IncludeCheckerWorkspaceCopier(StructurizrHack hack) {
		super(hack);
	}

	@Override
	public void clone(Workspace from, Workspace to) {
		String included = to.getProperties().getOrDefault(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES, "");
		List<String> includedWorkspaces = new ArrayList<>(Arrays.asList(included.split(",")));
		if(from.getName().isBlank())
			throw new UnsupportedOperationException("No workspace should have a blank name");
		if(!includedWorkspaces.contains(from.getName())) {
			logger.fine(String.format("Cloning %s to %s", from, to));
			super.clone(from, to);
			includedWorkspaces.add(from.getName());
			to.removeProperty(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES);
			to.addProperty(C4PlantUML2Structurizr.Constants.INCLUDED_WORKSPACES, includedWorkspaces.stream().collect(Collectors.joining(",")));
		}
	}
}
