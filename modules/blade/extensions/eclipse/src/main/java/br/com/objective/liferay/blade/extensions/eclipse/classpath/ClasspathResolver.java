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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ClasspathResolver {
  final File file;
  final Classpath classpath;

  public ClasspathResolver(File file, Classpath classpath) {
    this.file = file;
    this.classpath = classpath;
  }

  private Path getRoot() throws Exception {

    Path root = file.toPath().toAbsolutePath();

    do {
      if (Files.exists(root.resolve("settings.gradle"))) {
        return root;
      }
      root = root.getParent();
    } while (root != null);

    throw new Exception("settings.gradle not found on parent dirs");
  }

  private static Optional<Path> findModulePath(Path root, ClasspathEntry entry) throws IOException {

    Path moduleName = Paths.get(entry.get("path")).getFileName();

    return Files.walk(root).filter(file -> file.getFileName().equals(moduleName)).findAny();
  }

  public Classpath resolve() throws Exception {
    Path root = getRoot();

    Classpath classpath = cloneClasspath();

    classpath
        .getClasspathEntries()
        .forEach(
            entry -> {
              try {
                Optional<Path> modulePath = findModulePath(root, entry);

                if (modulePath.isPresent()) {
                  Path path = modulePath.get();
                  Path absolutePath = path.toAbsolutePath();
                  entry.set(ClasspathEntry.Attribute.PATH, absolutePath.toString());
                }
              } catch (IOException ignored) {
                // silence is golden
              }
            });

    return classpath;
  }

  private Classpath cloneClasspath() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(classpath);

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);
    return (Classpath) ois.readObject();
  }
}
