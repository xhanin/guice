package com.google.inject.internal;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.ComponentDef;
import com.google.inject.Key;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.ComponentDefBinding;
import com.google.inject.spi.Dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by xhanin on 11/12/2017.
 */
public class ComponentDefBindingImpl<T> extends BindingImpl<T> implements ComponentDefBinding<T> {
    private final ComponentDef<T> def;

    public ComponentDefBindingImpl(ComponentDef<T> def) {
        super(def.getSource(), def.getKey(), getScoping(def));
        this.def = def;
    }

    @Override
    public Set<Dependency<?>> getDependencies() {
        return def.getDependencies();
    }

    @Override
    public ComponentDef<T> getComponentDef() {
        return def;
    }

    @Override
    public void applyTo(Binder binder) {
        List<Key<?>> keys = new ArrayList<>();
        for (Dependency<?> dependency : getDependencies()) {
            keys.add(dependency.getKey());
        }
        if (def.getScopeInstance() != null) {
            binder.bind(def.getSource(), def.getKey(), ImmutableList.copyOf(keys), def.getBuilder(), def.getScopeInstance());
        } else if (def.getScopeAnnotation() != null) {
            binder.bind(def.getSource(), def.getKey(), ImmutableList.copyOf(keys), def.getBuilder(), def.getScopeAnnotation());
        } else {
            binder.bind(def.getSource(), def.getKey(), ImmutableList.copyOf(keys), def.getBuilder());
        }
    }

    @Override
    public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ComponentDefBinding.class)
                .add("key", getKey())
                .add("source", getSource())
                .add("scope", getScoping())
                .add("dependencies", getDependencies())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentDefBindingImpl<?> that = (ComponentDefBindingImpl<?>) o;

        return def != null ? def.equals(that.def) : that.def == null;
    }

    @Override
    public int hashCode() {
        return def != null ? def.hashCode() : 0;
    }

    private static <T> Scoping getScoping(ComponentDef<T> def) {
        if (def.getScopeInstance() != null) {
            return Scoping.forInstance(def.getScopeInstance());
        }
        if (def.getScopeAnnotation() != null) {
            return Scoping.forAnnotation(def.getScopeAnnotation());
        }
        return Scoping.UNSCOPED;
    }
}
