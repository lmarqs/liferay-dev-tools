/**
 * Copyright (c) 2018-present Objective Solutions, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objective.liferay.blade.extensions.eclipse.classpath;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ClasspathEntry implements Map<String, String> {
  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    return false;
  }

  @Override
  public String get(Object key) {
    return null;
  }

  @Override
  public String put(String key, String value) {
    return null;
  }

  @Override
  public String remove(Object key) {
    return null;
  }

  @Override
  public void putAll(Map<? extends String, ? extends String> m) {}

  @Override
  public void clear() {}

  @Override
  public Set<String> keySet() {
    return null;
  }

  @Override
  public Collection<String> values() {
    return null;
  }

  @Override
  public Set<Entry<String, String>> entrySet() {
    return null;
  }
}
