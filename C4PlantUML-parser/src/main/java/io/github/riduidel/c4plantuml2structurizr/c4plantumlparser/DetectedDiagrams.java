package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.util.List;

public record DetectedDiagrams(File context, List<File> containers, List<File> components, List<File> others) {

}
