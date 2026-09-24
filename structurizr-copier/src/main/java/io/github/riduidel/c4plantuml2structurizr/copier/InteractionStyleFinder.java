package io.github.riduidel.c4plantuml2structurizr.copier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.structurizr.model.InteractionStyle;

public class InteractionStyleFinder {
	private static final File MAPPINGS_FILE = new File("mappings.properties");
	Properties mappings;
	
	public InteractionStyleFinder() {
		readMappings();
	}

	public InteractionStyle getInteractionStyleFor(String technology) {
		if(!mappings.containsKey(technology)) {
			addMappingAndReload(technology);
		}
		return InteractionStyle.valueOf(mappings.getProperty(technology));
	}

	private void addMappingAndReload(String technology) {
		mappings.setProperty(technology, InteractionStyle.Synchronous.name());
		// Now write file in alphabetical order, please
		writeMappings();
		// And reload it
		readMappings();
	}
	
	private void writeMappings() {
		try {
			try(OutputStream output = new FileOutputStream(MAPPINGS_FILE)) {
				mappings.store(output, null);
			}
		} catch(IOException e) {
			throw new RuntimeException(String.format("Unable to write properties to %s", MAPPINGS_FILE), e);
		}
	}

	public void readMappings() {
		try {
			Properties used = new Properties();
			try(InputStream reader = new FileInputStream(MAPPINGS_FILE)) {
				used.load(reader);
				mappings = used;
			}
		} catch(IOException e) {
			throw new RuntimeException(String.format("Unable to read properties from %s", MAPPINGS_FILE), e);
		}
	}
}