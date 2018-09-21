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
package br.com.objective.liferay.blade.extensions.eclipse.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import br.com.objective.liferay.blade.extensions.eclipse.classpath.Classpath;
import br.com.objective.liferay.blade.extensions.eclipse.classpath.ClasspathEntry;
import br.com.objective.liferay.blade.extensions.eclipse.classpath.ClasspathEntry.Attribute;
import br.com.objective.liferay.blade.extensions.eclipse.classpath.ClasspathEntry.Kind;

import com.liferay.blade.cli.BladeCLI;

import org.xml.sax.SAXException;

public class ClasspathResolver {

  private final BladeCLI bladeCli;
  private final Path base;
  private Path root;

  public ClasspathResolver(BladeCLI bladeCli) throws Exception {
    this.bladeCli = bladeCli;
    this.base = bladeCli.getBase().toPath();
  }

  private Optional<Path> getRoot() {

    if (root != null) {
      return Optional.of(root);
    }

    root = bladeCli.getBase().toPath().toAbsolutePath();

    do {
      if (Files.exists(root.resolve("settings.gradle"))) {
        return Optional.of(root);
      }
      root = root.getParent();

    } while (root != null);

    return Optional.empty();
  }

  private Stream<Path> findSubModules() {
    try {

      return Files.walk(base)
          .filter(file -> Files.exists(file.resolve("build.gradle")))
          .map(Path::toAbsolutePath);

    } catch (IOException ignored) {
      return Stream.empty();
    }
  }

  private Optional<Path> findModule(String module) {
    Optional<Path> root = getRoot();

    if (root.isPresent()) {
      try {

        Path modulePath = Paths.get(module).getFileName();

        return Files.walk(root.get())
            .filter(path -> path.getFileName().equals(modulePath))
            .map(Path::toAbsolutePath)
            .findFirst();

      } catch (IOException ignored) {
        return Optional.empty();
      }
    }

    return Optional.empty();
  }

  public void resolve() {
    bladeCli.out("Resolving modules classpath");
    findSubModules()
        .forEach(
            modulePath -> {
              try {

                bladeCli.out("Resolving module " + modulePath.getFileName().toString());

                resolve(modulePath);

              } catch (Exception e) {
                bladeCli.err(e.getMessage());
              }
            });
  }

  public void resolve(Path modulePath)
      throws ParserConfigurationException, SAXException, IOException, TransformerException {

    File file = modulePath.resolve(".classpath").toFile();

    Classpath classpath = ClasspathXMLUtil.readClasspath(file);

    classpath
        .getClasspathEntries()
        .stream()
        .filter(entry -> entry.get(Attribute.KIND).equals(Kind.SRC.toString()))
        .filter(entry -> !entry.get(Attribute.PATH).startsWith("src"))
        .forEach(this::resolve);

    ClasspathXMLUtil.writeClasspath(file, classpath);
  }

  public void resolve(ClasspathEntry classpathEntry) {
    findModule(classpathEntry.get(Attribute.PATH))
        .ifPresent(
            modulePath -> {
              classpathEntry.set(Attribute.KIND, Kind.LIB);
              classpathEntry.set(Attribute.PATH, modulePath.resolve("classes"));
              classpathEntry.set(Attribute.SOURCEPATH, modulePath.resolve("src/main/java"));
            });
  }
}
