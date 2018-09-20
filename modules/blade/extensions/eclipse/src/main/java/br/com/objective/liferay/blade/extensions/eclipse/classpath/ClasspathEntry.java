/**
 * Copyright (c) 2018-present Objective Solutions, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.objective.liferay.blade.extensions.eclipse.classpath;

import java.util.Map;
import java.util.TreeMap;

public class ClasspathEntry implements Comparable {

  public enum Attribute {
    KIND("kind"),
    PATH("path"),
    SOURCEPATH("sourcepath");

    private final String name;

    Attribute(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }

  public enum Kind {
    LIB("lib"),
    OUTPUT("output"),
    SRC("src");

    private final String value;

    Kind(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  private final Map<String, String> attributes;

  public ClasspathEntry(Map<String, String> attributes) {
    this.attributes = new TreeMap<>(attributes);
  }

  public String get(String name) {
    return attributes.get(name);
  }

  @Override
  public String toString() {
    return attributes.toString();
  }

  @Override
  public int compareTo(Object object) {
    return toString().compareTo(object.toString());
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (!ClasspathEntry.class.isAssignableFrom(object.getClass())) {
      return false;
    }

    return toString().equals(object.toString());
  }

  @Override
  public int hashCode() {
    return 53 * 3 + this.attributes.hashCode();
  }
}
