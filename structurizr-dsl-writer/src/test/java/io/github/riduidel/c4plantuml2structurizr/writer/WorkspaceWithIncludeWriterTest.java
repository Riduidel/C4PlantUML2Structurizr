package io.github.riduidel.c4plantuml2structurizr.writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.fge.lambdas.Throwing;
import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParserException;

import io.github.riduidel.structurizr.StreamUtils;

/**
 * To simulate workspace, we have to create workspace as code.
 * So we create one workspace class per test, then compare it to the output file
 */
class WorkspaceWithIncludeWriterTest {
	private static final Logger logger = Logger.getLogger(WorkspaceWithIncludeWriterTest.class.getName());
	private static WriterConfiguration configuration = new WriterConfiguration();

	static Stream<Arguments> can_write_a_dsl_file() throws URISyntaxException, IOException {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.dsl");
		// Now read the whole folder of examples
		URL resource = WorkspaceWithIncludeWriterTest.class.getClassLoader().getResource(WorkspaceWithIncludeWriterTest.class.getName().replace('.', '/'));
        Path dirPath = Paths.get(resource.toURI());  
        try (Stream<Path> stream = Files.list(dirPath)) {
        	// Convert the paths to Workspace objects
        	// And return that as a stream
            return stream
					.filter(StreamUtils.andLogFilteredOutValues(
							path -> matcher.matches(path), 
							p -> logger.info("REFUSED "+p)))
					.sorted(Path::compareTo)
					.map(Path::toFile)
					.filter(file -> !file.getName().startsWith("_"))
            		.map(Throwing.function(file -> Arguments.of(file.getName(),
            				createWorkspaceFor(file),
            				file)))
            		.toList()
            		.stream();
        }
	}

	@SuppressWarnings("unchecked")
	private static Workspace createWorkspaceFor(File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String name = file.getName();
		name = name.substring(0, name.indexOf('.'));
		String className = String.format("%s_Example_%s", 
				WorkspaceWithIncludeWriterTest.class.getName(),
				name);
		Class<?> supplierClass = Class.forName(className);
		Function<WriterConfiguration, Workspace> supplier = (Function<WriterConfiguration, Workspace>) supplierClass.getDeclaredConstructor().newInstance();
		return supplier.apply(configuration);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource
	void can_write_a_dsl_file(String path, Workspace workspace, File text) throws IOException, StructurizrDslParserException {
		// Given
		workspace.removeProperty("structurizr.dsl");
		// When
		String actual = new WorkspaceWriter(configuration).write(workspace);
		// Then
		String expected = Files.readString(text.toPath());
		Assertions.assertThat(actual).isEqualTo(expected);
	}

}
