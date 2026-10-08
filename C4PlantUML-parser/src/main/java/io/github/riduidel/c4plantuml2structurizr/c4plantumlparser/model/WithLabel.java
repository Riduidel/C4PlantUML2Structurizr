package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model;

@FunctionalInterface
public interface WithLabel<Type> {
	Type withLabel(String label);
}
