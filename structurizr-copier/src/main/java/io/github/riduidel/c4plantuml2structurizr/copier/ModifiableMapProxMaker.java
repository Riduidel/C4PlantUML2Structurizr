package io.github.riduidel.c4plantuml2structurizr.copier;

import java.lang.reflect.Field;
import java.util.Map;

import com.structurizr.PropertyHolder;

public class ModifiableMapProxMaker<Type extends PropertyHolder>  {

	private Class<Type> proxified;
	private Field proxifiedField;

	public ModifiableMapProxMaker(Class<Type> proxifiedClass) throws NoSuchMethodException, SecurityException, NoSuchFieldException {
		this.proxified = proxifiedClass;
		this.proxifiedField = proxified.getDeclaredField("properties");
		proxifiedField.setAccessible(true);
	}

	/**
	 * Exposes a Java object able to read/write properties into
	 * 
	 * @param propertyHolder the object we want to be able to inject properties into
	 * @return a Proxy that will propagate all changes directly
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Map<String, String> proxify(Type propertyHolder) throws IllegalArgumentException, IllegalAccessException {
		return (Map<String, String>) proxifiedField.get(propertyHolder);
	}

}
