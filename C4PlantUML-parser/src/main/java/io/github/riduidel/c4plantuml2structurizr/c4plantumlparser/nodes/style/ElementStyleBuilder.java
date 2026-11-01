package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.style;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithBackgroundColor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithBorderColor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithBorderStyle;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithBorderThickness;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithFontColor;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLegendSprite;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithLegendText;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShadowing;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithShape;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithSprite;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementVisitor;

public class ElementStyleBuilder extends StyleElementBuilder<ElementStyleBuilder> 
	implements WithBackgroundColor<ElementStyleBuilder>,
		WithFontColor<ElementStyleBuilder>,
		WithBorderColor<ElementStyleBuilder>,
		WithShadowing<ElementStyleBuilder>,
		WithShape<ElementStyleBuilder>,
		WithSprite<ElementStyleBuilder>,
		WithTechnology<ElementStyleBuilder>,
		WithLegendText<ElementStyleBuilder>,
		WithLegendSprite<ElementStyleBuilder>,
		WithBorderStyle<ElementStyleBuilder>,
		WithBorderThickness<ElementStyleBuilder> {
	
	private String backgroundColor = "";
	private String borderColor = "";
	private String borderStyle = "";
	private String borderThickness = "";
	
	private String shadowing = "";
	private String shape = "";
	private String sprite = "";
	private String technology = "";
	private String legendText = "";
	private String legendSprite = "";

	public String getBorderColor() {
		return borderColor;
	}

	public String getShadowing() {
		return shadowing;
	}

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
	public ElementStyleBuilder withShadowing(String shadowing) {
		this.shadowing = shadowing;
		return this;
	}
	
	@Override
	public ElementStyleBuilder withBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	
	@Override
	public ElementStyleBuilder withBorderStyle(String borderStyle) {
		this.borderStyle = borderStyle;
		return this;
	}
	
	@Override
	public ElementStyleBuilder withBorderThickness(String borderThickness) {
		this.borderThickness = borderThickness;
		return this;
	}
	@Override
	public ElementStyleBuilder withShape(String shape) {
		this.shape = shape;
		return this;
	}

	@Override
	public ElementStyleBuilder withSprite(String sprite) {
		this.sprite = sprite;
		return this;
	}

	@Override
	public ElementStyleBuilder withLegendText(String legendText) {
		this.legendText = legendText;
		return this;
	}

	@Override
	public ElementStyleBuilder withLegendSprite(String legendSprite) {
		this.legendText = legendSprite;
		return this;
	}

	@Override
	public ElementStyleBuilder withTechnology(String technology) {
		this.technology = technology;
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

	public String getShape() {
		return shape;
	}

	public String getSprite() {
		return sprite;
	}

	public String getTechnology() {
		return technology;
	}

	public String getLegendText() {
		return legendText;
	}

	public String getLegendSprite() {
		return legendSprite;
	}

	public String getBorderStyle() {
		return borderStyle;
	}

	public String getBorderThickness() {
		return borderThickness;
	}
}
