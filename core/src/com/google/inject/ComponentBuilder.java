package com.google.inject;

/**
 * Provides components using a provided component accessor to access to the component dependencies.
 * @author xavier.hanin@gmail.com (Xavier Hanin)
 */
public interface ComponentBuilder<T> {
    T get(ComponentAccessor accessor);
}
