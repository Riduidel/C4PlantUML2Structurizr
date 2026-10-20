package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithShape<Type> {
	Type withShape(String shape);
}
