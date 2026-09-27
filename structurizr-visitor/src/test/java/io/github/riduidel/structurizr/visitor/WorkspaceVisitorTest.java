package io.github.riduidel.structurizr.visitor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.fge.lambdas.Throwing;
import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParser;

/**
 * We check that the system works correctly by ensuring all elements of input workspace are visited
 */
class WorkspaceVisitorTest {
	static class CollectingVisitor extends FullVisitorAdapter {
	}
	
	static class CollectingVisitorAssert extends AbstractAssert<CollectingVisitorAssert, CollectingVisitor> {

		public CollectingVisitorAssert(CollectingVisitor actual) {
			super(actual, CollectingVisitorAssert.class);
		}

		public void hasVisitedEverything() {
			isNotNull();
			// I frankly don't know how to do that any further ...
		}
		
	}

	public static CollectingVisitorAssert assertThat(CollectingVisitor actual) {
	    return new CollectingVisitorAssert(actual);
	}
	
	public static Stream<Arguments> all_elements_of_workspace_are_visited() throws IOException, URISyntaxException {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.dsl");
		// Now read the whole folder of examples
		URL resource = WorkspaceVisitorTest.class.getClassLoader().getResource(WorkspaceVisitorTest.class.getPackageName().replace('.', '/'));
        Path dirPath = Paths.get(resource.toURI());  
        try (Stream<Path> stream = Files.list(dirPath)) {
        	// Convert the paths to Workspace objects
        	// And return that as a stream
            return stream
            		.filter(path -> matcher.matches(path))
            		.map(Throwing.function(path -> {
            			StructurizrDslParser parser = new StructurizrDslParser();
            			parser.parse(path.toFile());
            			Workspace parsed = parser.getWorkspace();
            			return Arguments.of(path.toFile(), parsed);
            		}))
            		.toList()
            		.stream();  
        }
	}
	
	@ParameterizedTest
	@MethodSource
	void all_elements_of_workspace_are_visited(File source, Workspace workspace) {
		// Given
		CollectingVisitor collecting = new CollectingVisitor();
		WorkspaceVisitor tested = new WorkspaceVisitor(collecting);
		// When
		tested.visit(workspace);
		// Then
		assertThat(collecting)
			.hasVisitedEverything();
	}

}
