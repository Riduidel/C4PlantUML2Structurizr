package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.style;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithBackgroundColor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithFontColor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ElementStyleBuilder extends StyleElementBuilder<ElementStyleBuilder> 
	implements WithBackgroundColor<ElementStyleBuilder>,
		WithFontColor<ElementStyleBuilder>{
	
	private String backgroundColor;

	public String getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public ElementStyleBuilder withBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	
	private String fontColor;

	public String getFontColor() {
		return fontColor;
	}

	@Override
	public ElementStyleBuilder withFontColor(String fontColor) {
		this.fontColor = fontColor;
		return this;
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitElementStyle(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitElementStyle(this);
	}
}
