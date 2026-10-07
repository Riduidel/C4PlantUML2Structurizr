package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.C4PlantUMLDiagramModel;

@BuildParseTree
public class C4PlantUMLParser extends BaseParser<C4PlantUMLDiagramModel> {

	public Rule diagram() {
		return Sequence(String("@startuml"),
				String("@enduml"));
		
	}

}
