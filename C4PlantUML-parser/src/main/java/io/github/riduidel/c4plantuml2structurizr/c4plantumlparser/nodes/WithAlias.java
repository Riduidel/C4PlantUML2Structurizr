package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithAlias<Type> {
	Type withAlias(String alias);
}
