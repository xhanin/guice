package com.google.inject;

import java.util.Set;

/**
 * Allow to access to components from a predefined set of keys.
 *
 * @see ComponentBuilder
 * @author xavier.hanin@gmail.com (Xavier Hanin)
 */
public interface ComponentAccessor {
    /**
     * Returns the set of keys this accessor can provide access to.
     *
     * @return the set of keys this accessor can provide access to.
     */
    Set<Key<?>> getKeys();

    /**
     * Returns the provider used to obtain instances for the given injection key.
     *
     * @throws ConfigurationException if this accessor cannot find or create the provider
     * (whilst the key is defined for this accessor).
     * @param key the key for which the provider should be returned
     * @return the provider used to obtain instances for the given injection key.
     */
    <T> Provider<T> getProvider(Key<T> key);
}
