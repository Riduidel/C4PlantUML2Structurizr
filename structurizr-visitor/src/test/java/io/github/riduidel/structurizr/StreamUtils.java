package io.github.riduidel.structurizr;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StreamUtils {
	/*
	 * @see https://stackoverflow.com/a/50501819
	 * Posted by Grzegorz Piwowarek, modified by community. See post 'Timeline' for change history
	 * Retrieved 2026-09-27, License - CC BY-SA 4.0
	 */
	public static <T> Predicate<T> andLogFilteredOutValues(Predicate<T> predicate, Consumer<T> action) {
	    Objects.requireNonNull(predicate);
	    Objects.requireNonNull(action);

	    return value -> {
	        if (predicate.test(value)) {
	            return true;
	        } else {
	            action.accept(value);
	            return false;
	        }
	    };
	}

}
