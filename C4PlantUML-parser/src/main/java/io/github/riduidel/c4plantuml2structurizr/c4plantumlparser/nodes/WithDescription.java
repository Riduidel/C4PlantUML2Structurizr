package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithDescription<Type> {
	Type withDescription(String description);
}
