package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.style;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementBuilder;

public abstract class StyleElementBuilder<BuilderType extends StyleElementBuilder> extends WorkspaceElementBuilder
	implements WithTargetTag<BuilderType> {
	
	private String targetTag;

	public String getTargetTag() {
		return targetTag;
	}

	@Override
	public BuilderType withTargetTag(String targetTag) {
		this.targetTag = targetTag;
		return (BuilderType) this;
	}

}
