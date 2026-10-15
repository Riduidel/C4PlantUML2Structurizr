package io.github.riduidel.c4plantuml2structurizr;

public class Configuration {
	public String variableProperty = "structurizr.dsl.identifier";
	/**
	 * Do not change that value, Structurizr relies upon it
	 * @see https://docs.structurizr.com/dsl/cookbook/groups/#nested-groups
	 */
	public String groupSeparator = "structurizr.groupSeparator";
	/**
	 * Key for a property containing current group path.
	 * This property is usually empty during normal structurizr usage
	 * but is updated during construction.
	 * We use the "structurizr.dsl" prefix, since WorkspaceWriter
	 * usually don't write that one ;-)
	 */
	public String currentGroupPath = "structurizr.dsl.current.group.path";
}
