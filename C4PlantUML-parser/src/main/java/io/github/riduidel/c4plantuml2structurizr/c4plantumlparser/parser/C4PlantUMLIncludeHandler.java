package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.parboiled.Context;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ValueStack;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.WorkspaceBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.C4PlantUMLDiagram;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.Include;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.nodes.WorkspaceElementBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.visitors.IncludedDiagram;

public class C4PlantUMLIncludeHandler {

	public void processInclude(Context context) {
		ValueStack valueStack = context.getValueStack();
		WorkspaceElementBuilder container = (WorkspaceElementBuilder) valueStack.peek();
		String includePath = context.getMatch();
		// Explore the value stack looking for the latest diagram declaration
		Optional<C4PlantUMLDiagram> containing = StreamSupport.stream(valueStack.spliterator(), false)
			.filter(element -> element instanceof C4PlantUMLDiagram)
			.map(d -> ((C4PlantUMLDiagram) d))
			.reduce((first, second) -> second);
		containing.ifPresent(containingDiagram -> {
				// We now can resolve url
				processIncludeIn(containingDiagram, container, includePath);
			});
	}

	private void processIncludeIn(C4PlantUMLDiagram containingDiagram, 
			WorkspaceElementBuilder container,
			String includePath) {
		Include include = new Include(includePath);
		container.add(include);
		try {
			URI uri = new URI(includePath);
			if(uri.getScheme()==null || uri.getScheme().equals("file")) {
				Path includedPath = containingDiagram.source.getParentFile().toPath().resolve(uri.getPath());
				C4PlantUMLDiagram diagram = new IncludedDiagram(includedPath.toFile());
				ParsingResult<WorkspaceElementBuilder> result = WorkspaceBuilder.parseFileToResult(diagram);
				if(!result.hasErrors()) {
					include.add(result.resultValue);
				}
			} else {
				// Would love to log that
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException("TODO handle URISyntaxException", e);
		} catch (IOException e) {
			throw new RuntimeException("TODO handle IOException", e);
		}
	}
}
