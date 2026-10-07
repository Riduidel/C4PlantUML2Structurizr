package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiagramTypeDetector {
	PathMatcher context = FileSystems.getDefault().getPathMatcher("glob:**/*context.{plantuml,puml}");
	PathMatcher container = FileSystems.getDefault().getPathMatcher("glob:**/*container.{plantuml,puml}");
	PathMatcher component = FileSystems.getDefault().getPathMatcher("glob:**/*component.{plantuml,puml}");

	public DetectedDiagrams detectDiagramsIn(List<File> diagrams) {
		List<File> contextFiles = new ArrayList<File>();
		List<File> containerFiles = new ArrayList<File>();
		List<File> componentFiles = new ArrayList<File>();
		List<File> otherFiles = new ArrayList<File>(diagrams);
		for(File f : diagrams) {
			if(context.matches(f.toPath())) {
				contextFiles.add(f);
			} else if(container.matches(f.toPath())) {
				containerFiles.add(f);
			} else if(component.matches(f.toPath())) {
				componentFiles.add(f);
			}
		}
		otherFiles.removeAll(contextFiles);
		otherFiles.removeAll(containerFiles);
		otherFiles.removeAll(componentFiles);
		if(contextFiles.size()==0) {
			throw new UnsupportedOperationException(String.format("We don't know how to handle the case of missingcontext diagrams"));
		} else if(contextFiles.size()>1){
			throw new UnsupportedOperationException(String.format("We don't know how to handle the case of multiple context diagrams\n%s",
					contextFiles.stream()
						.map(f -> "* "+f.getAbsolutePath())
						.collect(Collectors.joining("\n"))));
		} else {
			return new DetectedDiagrams(contextFiles.get(0), containerFiles, componentFiles, otherFiles);
		}
	}

}
