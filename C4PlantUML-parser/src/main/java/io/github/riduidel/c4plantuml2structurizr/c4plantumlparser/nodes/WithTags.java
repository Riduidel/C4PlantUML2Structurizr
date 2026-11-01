package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes;

@FunctionalInterface
public interface WithTags<Type> {
	Type withTags(String tags);
}
