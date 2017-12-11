/*
 * Copyright (C) 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject;

import com.google.common.collect.ImmutableList;
import com.google.inject.name.Names;
import junit.framework.TestCase;


/** @author xavier.hanin@gmail.com (Xavier Hanin) */
public class ComponentDefBindingTest extends TestCase {

  public void testComponentDefBinding() throws CreationException {
    Injector injector =
            Guice.createInjector(
                    new AbstractModule() {
                      @Override
                      protected void configure() {
                        bind(Foo.class);

                        bind(this, Key.get(Bar.class), ImmutableList.<Key<?>>of(), new ComponentBuilder<Bar>() {
                          @Override
                          public Bar get(ComponentAccessor accessor) {
                            return new Bar();
                          }
                        });

                        final Key<String> aKey = Key.get(String.class, Names.named("a"));
                        bind(this, aKey, ImmutableList.<Key<?>>of(),
                                new ComponentBuilder<String>() {
                                  @Override
                                  public String get(ComponentAccessor accessor) {
                                    return "A";
                                  }
                                });
                        bind(this,
                                Key.get(String.class, Names.named("hello")),
                                ImmutableList.<Key<?>>of(aKey),
                                new ComponentBuilder<String>() {
                                  @Override
                                  public String get(ComponentAccessor accessor) {
                                    return "hello " + accessor.getProvider(aKey).get();
                                  }
                                });
                      }
                    });

    Foo foo = injector.getInstance(Foo.class);

    Bar bar = foo.barProvider.get();
    assertNotNull(bar);
    assertNotSame(bar, foo.barProvider.get());

    String hello = injector.getInstance(Key.get(String.class, Names.named("hello")));
    assertNotNull(hello);
    assertEquals("hello A", hello);
  }

  public void testPerf() throws CreationException {
    final Key<String> world = Key.get(String.class, Names.named("p"));

    final int count = 10000;
    Module m = new AbstractModule() {
      @Override
      protected void configure() {
        for (int i = 0; i < count; i++) {
          final int j = i;
          bind(this, Key.get(String.class, Names.named(String.valueOf(i))),
                  ImmutableList.<Key<?>>of(world),
                  new HelloBuilder(j, world));
        }
        bind(world).toInstance("world");
      }
    };

    Injector injector = Guice.createInjector(m);

    for (int i = 0; i < count; i++) {
      String instance = injector.getInstance(Key.get(String.class, Names.named(String.valueOf(i))));
      assertNotNull(instance);
      assertEquals("hello " + i + " from world", instance);
    }
  }

  static class Foo {
    @Inject Provider<Bar> barProvider;
  }

  static class Bar {}

  static class HelloBuilder implements ComponentBuilder<String> {
    private final int c;
    private final Key<String> world;

    public HelloBuilder(int c, Key<String> world) {
      this.c = c;
      this.world = world;
    }

    @Override
    public String get(ComponentAccessor injector) {
      return "hello " + c + " from " + injector.getProvider(world).get();
    }
  }
}
