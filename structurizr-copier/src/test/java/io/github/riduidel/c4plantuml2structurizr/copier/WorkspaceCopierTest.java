package io.github.riduidel.c4plantuml2structurizr.copier;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.fge.lambdas.Throwing;
import com.structurizr.Workspace;

import io.github.riduidel.structurizr.StreamUtils;
import io.github.riduidel.structurizr.StructurizrTestUtils;

class WorkspaceCopierTest {
	private static final Logger logger = Logger.getLogger(WorkspaceCopierTest.class.getName());
	
	static private Arguments toArguments(File testFolder) {
		List<Workspace> workspaces = usedFiles(testFolder)
			.map(Throwing.function(StructurizrTestUtils::toWorkspace))
			.toList();
		List<Object> args = new ArrayList<>();
		args.add(testFolder);
		args.addAll(workspaces);
		Arguments returned = Arguments.of(args.toArray());
		return returned;
	}

	public static boolean pathHasRequiredFiles(Path path) {
		File folder = path.toFile();
		return usedFiles(folder).allMatch(File::exists);
	}

	private static Stream<File> usedFiles(File folder) {
		return Stream.of(new File(folder, "source.dsl"), new File(folder, "target.dsl"),
				new File(folder, "reference.dsl"));
	}

	private static void explainWhyPathIsBad(Path path) {
		logger.warning(usedFiles(path.toFile())
			.map(f -> String.format("\t%s exists? %s %s", f.getName(), f.exists() ? "✅" : "❌", f.exists()))
			.collect(Collectors.joining("\n",
					"Path "+path+" has been filtered out\n", "")));
	}

	static Stream<Arguments> can_copy_workspace_into_another() throws URISyntaxException, IOException {
		// Now read the whole folder of examples
		URL resource = WorkspaceCopierTest.class.getClassLoader()
				.getResource(WorkspaceCopierTest.class.getPackageName().replace('.', '/'));
		Path dirPath = Paths.get(resource.toURI());
		// In that folder, each subfolder is a test case containing exactly three
		// workspace files
		try (Stream<Path> stream = Files.list(dirPath)) {
			List<Arguments> returned = stream
					.filter(path -> path.toFile().isDirectory())
					.filter(StreamUtils.andLogFilteredOutValues(
							WorkspaceCopierTest::pathHasRequiredFiles, 
							WorkspaceCopierTest::explainWhyPathIsBad))
					.map(Path::toFile)
					.map(WorkspaceCopierTest::toArguments)
					.toList()
					;
			return returned.stream();
		}

	}

	@ParameterizedTest
	@MethodSource
	void can_copy_workspace_into_another(File folder, Workspace source, Workspace target, Workspace expected) {
		// Given
		WorkspaceCopier copier = new WorkspaceCopier();
		// When
		copier.clone(source, target);
		// Then
		Assertions.assertThat(target).usingRecursiveComparison()
		// I think I can do better than that
			.ignoringFieldsMatchingRegexes(".*properties")
			.isEqualTo(expected);
	}

}
