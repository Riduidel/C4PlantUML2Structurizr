package io.github.riduidel.c4plantuml2structurizr.writer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParserException;

import io.github.riduidel.structurizr.StreamUtils;
import io.github.riduidel.structurizr.StructurizrTestUtils;

class WorkspaceWriterTest {
	private static final Logger logger = Logger.getLogger(WorkspaceWriterTest.class.getName());

	static Stream<Arguments> can_write_a_dsl_file() throws URISyntaxException, IOException {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.dsl");
		// Now read the whole folder of examples
		URL resource = WorkspaceWriterTest.class.getClassLoader().getResource(WorkspaceWriterTest.class.getPackageName().replace('.', '/'));
        Path dirPath = Paths.get(resource.toURI());  
        try (Stream<Path> stream = Files.list(dirPath)) {
        	// Convert the paths to Workspace objects
        	// And return that as a stream
            return stream
					.filter(StreamUtils.andLogFilteredOutValues(
							path -> matcher.matches(path), 
							p -> logger.info("REFUSED "+p)))
            		.map(path -> Arguments.of(path.toFile()))
            		.toList()
            		.stream();
        }
	}


	@ParameterizedTest
	@MethodSource
	void can_write_a_dsl_file(File source) throws IOException, StructurizrDslParserException {
		// Given
		Workspace workspace = StructurizrTestUtils.toWorkspace(source);
		workspace.removeProperty("structurizr.dsl");
		// When
		String actual = new WorkspaceWriter().write(workspace);
		// Then
		String expected = Files.readString(source.toPath());
		Assertions.assertThat(actual).isEqualTo(expected);
	}

}
