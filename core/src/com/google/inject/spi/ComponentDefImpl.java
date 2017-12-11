package com.google.inject.spi;

import com.google.inject.ComponentBuilder;
import com.google.inject.ComponentDef;
import com.google.inject.Key;
import com.google.inject.Scope;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author xavier.hanin@gmail.com (Xavier Hanin)
 */
class ComponentDefImpl<T> implements ComponentDef<T> {
  private final Object source;
  private final Key<T> key;
  private final Set<Dependency<?>> dependencies;
  private final ComponentBuilder<T> builder;
  private final Scope scopeInstance;
  private final Class<? extends Annotation> scopeAnnotation;

  ComponentDefImpl(Object source, Key<T> key, Set<Dependency<?>> dependencies,
                   ComponentBuilder<T> builder) {
    this.source = source;
    this.key = key;
    this.dependencies = dependencies;
    this.builder = builder;
    this.scopeInstance = null;
    this.scopeAnnotation = null;
  }
  ComponentDefImpl(Object source, Key<T> key, Set<Dependency<?>> dependencies,
                   ComponentBuilder<T> builder, Scope scopeInstance) {
    this.source = source;
    this.key = key;
    this.dependencies = dependencies;
    this.builder = builder;
    this.scopeInstance = scopeInstance;
    this.scopeAnnotation = null;
  }
  ComponentDefImpl(Object source, Key<T> key, Set<Dependency<?>> dependencies,
                   ComponentBuilder<T> builder, Class<? extends Annotation> scopeAnnotation) {
    this.source = source;
    this.key = key;
    this.dependencies = dependencies;
    this.builder = builder;
    this.scopeInstance = null;
    this.scopeAnnotation = scopeAnnotation;
  }

  @Override
  public Object getSource() {
    return source;
  }

  @Override
  public Key<T> getKey() {
    return key;
  }

  @Override
  public ComponentBuilder<T> getBuilder() {
    return builder;
  }

  @Override
  public Set<Dependency<?>> getDependencies() {
    return dependencies;
  }

  /** Returns the scope instance, or {@code null} if that isn't known for this instance. */
  public Scope getScopeInstance() {
    return scopeInstance;
  }

  /** Returns the scope annotation, or {@code null} if that isn't known for this instance. */
  public Class<? extends Annotation> getScopeAnnotation() {
    return scopeAnnotation;
  }

}
