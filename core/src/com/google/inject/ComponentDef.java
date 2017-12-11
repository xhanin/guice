package com.google.inject;

import com.google.inject.spi.HasDependencies;

import java.lang.annotation.Annotation;

/**
 * Fully defines a component (key, source, scoping), its dependencies, and how it can be created
 * @author xavier.hanin@gmail.com (Xavier Hanin)
 */
public interface ComponentDef<T> extends HasDependencies {
    /**
     * Returns an arbitrary object containing information about the "place" where this element was
     * defined. Used by Guice in the production of descriptive error messages.
     *
     * <p>Tools might specially handle types they know about; {@code StackTraceElement} is a good
     * example. Tools should simply call {@code toString()} on the source object if the type is
     * unfamiliar.
     */
    Object getSource();

    /** Returns the key for this component. */
    Key<T> getKey();

    /** Returns the scope instance, or {@code null} if that isn't known for this instance. */
    Scope getScopeInstance();

    /** Returns the scope annotation, or {@code null} if that isn't known for this instance. */
    Class<? extends Annotation> getScopeAnnotation();

    /**
     * Returns the component builder that can be used to construct instances of components defined by this component def
     */
    ComponentBuilder<T> getBuilder();
}
