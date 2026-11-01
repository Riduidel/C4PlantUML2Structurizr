package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithBackgroundColor<Type> {
	Type withBackgroundColor(String backgroundColor);
}
