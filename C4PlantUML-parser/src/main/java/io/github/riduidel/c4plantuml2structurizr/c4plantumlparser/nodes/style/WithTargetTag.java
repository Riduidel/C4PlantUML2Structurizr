package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.style;

@FunctionalInterface
public interface WithTargetTag<Type> {
	Type withTargetTag(String targetTag);
}
