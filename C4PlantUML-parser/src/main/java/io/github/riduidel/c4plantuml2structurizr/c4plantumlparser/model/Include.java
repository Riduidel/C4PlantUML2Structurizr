package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

public class Include extends WorkspaceElementBuilder{

	private String url;

	public Include(String url) {
		this.url = url;
	}

	@Override
	protected boolean startVisit(WorkspaceElementVisitor visitor) {
		return visitor.startVisitInclude(this);
	}

	@Override
	protected <Type> Type endVisit(WorkspaceElementVisitor<Type> visitor) {
		return visitor.endVisitInclude(this);
	}

}
