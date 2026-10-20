package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithLink<Type> {
	Type withLink(String link);
}
