package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.fge.lambdas.Throwing;
import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParserException;

import io.github.riduidel.c4plantuml2structurizr.writer.WorkspaceWriter;
import io.github.riduidel.structurizr.StreamUtils;
import io.github.riduidel.structurizr.StructurizrTestUtils;

class WorkspaceDefinitionProcessorTest extends WorkspaceDefinitionProcessor {
	private static final Logger logger = Logger.getLogger(WorkspaceDefinitionProcessorTest.class.getName());
	static PathMatcher plantuml = FileSystems.getDefault().getPathMatcher("glob:**/*.{plantuml,puml}");
	static PathMatcher structurizr = FileSystems.getDefault().getPathMatcher("glob:**/*.dsl");
	
	static private Arguments toArguments(File testFolder) throws IOException {
		List<File> plantUmlFiles = Files.walk(testFolder.toPath())
				.filter(p -> plantuml.matches(p))
				.map(Path::toFile)
				.toList();
		File structurizrFile = Files.walk(testFolder.toPath())
				.filter(p -> structurizr.matches(p))
				.map(Path::toFile)
				.findFirst().get();

		List<Object> args = new ArrayList<>();
		args.add(testFolder);
		args.add(plantUmlFiles);
		args.add(structurizrFile);
		Arguments returned = Arguments.of(args.toArray());
		return returned;
	}

	public static boolean pathHasRequiredFiles(Path path) throws IOException {
		File folder = path.toFile();
		return usedFiles(folder).allMatch(File::exists);
	}

	private static Stream<File> usedFiles(File folder) throws IOException {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{dsl,plantuml,puml}");
		return Files.walk(folder.toPath())
				.filter(path -> matcher.matches(path))
				.map(Path::toFile)
				;
	}

	private static void explainWhyPathIsBad(Path path) throws IOException {
		long plantUmlFiles = Files.walk(path).filter(p -> plantuml.matches(p)).count();
		long structurizrFiles = Files.walk(path).filter(p -> structurizr.matches(p)).count();
		logger.warning(String.format("""
				In %s we have
				%d PlantUML files %s
				%d Structurizr files %s
				""", path,
				plantUmlFiles, plantUmlFiles>0 ? "✅" : "❌",
				structurizrFiles, structurizrFiles==1 ? "✅" : "❌"));
	}

	static Stream<Arguments> can_parse_set_of_C4PlantUML_files() throws URISyntaxException, IOException {
		// Now read the whole folder of examples
		URL resource = WorkspaceDefinitionProcessorTest.class.getClassLoader()
				.getResource(WorkspaceDefinitionProcessorTest.class.getPackageName().replace('.', '/'));
		Path dirPath = Paths.get(resource.toURI());
		// In that folder, each subfolder is a test case containing exactly three
		// workspace files
		try (Stream<Path> stream = Files.list(dirPath)) {
			List<Arguments> returned = stream
					.filter(path -> path.toFile().isDirectory())
					.filter(StreamUtils.andLogFilteredOutValues(
							Throwing.predicate(WorkspaceDefinitionProcessorTest::pathHasRequiredFiles), 
							Throwing.consumer(WorkspaceDefinitionProcessorTest::explainWhyPathIsBad)))
					.map(Path::toFile)
					.map(Throwing.function(WorkspaceDefinitionProcessorTest::toArguments))
					.toList()
					;
			return returned.stream();
		}

	}

	@ParameterizedTest
	@MethodSource
	void can_parse_set_of_C4PlantUML_files(File folder, List<File> diagrams, File workspace) throws StructurizrDslParserException, IOException {
		// Given
		Workspace expected = StructurizrTestUtils.toWorkspace(workspace);
		// When
		Workspace parsed = new WorkspaceDefinitionProcessor().parse(diagrams);
		// Then
		try {
			Assertions.assertThat(parsed).usingRecursiveComparison()
			// I think I can do better than that
				.ignoringFieldsMatchingRegexes(".*properties|.*interactionStyle|.*tags")
				.isEqualTo(expected);
		} catch(AssertionError e) {
			WorkspaceWriter writer = new WorkspaceWriter();
			String effective = writer.write(parsed);
			logger.severe(String.format("""
					 Workspace parsed from %s is invalid
					 ########################################################
					 %s
					 ########################################################
					""",
					folder,
					effective));
			throw e;
		}
	}

}
