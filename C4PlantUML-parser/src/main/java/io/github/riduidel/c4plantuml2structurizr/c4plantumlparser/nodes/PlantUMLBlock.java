package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

public class PlantUMLBlock extends WorkspaceElementBuilder {

	public final String blockName;

	public PlantUMLBlock(String blockName) {
		this.blockName = blockName;
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitPlantUMLBlock(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitPlantUMLBlock(this);
	}
}
