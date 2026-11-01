package io.github.riduidel.c4plantuml2structurizr.writer;

import java.util.function.Function;

import com.structurizr.Workspace;

public class WorkspaceWithIncludeWriterTest_Example_01_simple_workspace implements Function<WriterConfiguration, Workspace> {

	@Override
	public Workspace apply(WriterConfiguration configuration) {
		Workspace returned = new Workspace("A simple workspace");
		returned.addProperty(configuration.include, "_01_simple_workspace_include.dsl");
		return returned;
	}

}
