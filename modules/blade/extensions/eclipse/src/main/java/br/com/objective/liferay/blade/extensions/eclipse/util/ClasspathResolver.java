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
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import br.com.objective.liferay.blade.extensions.eclipse.model.Classpath;
import br.com.objective.liferay.blade.extensions.eclipse.model.ClasspathEntry;
import br.com.objective.liferay.blade.extensions.eclipse.model.ClasspathEntry.Attribute;
import br.com.objective.liferay.blade.extensions.eclipse.model.ClasspathEntry.Kind;

import com.liferay.blade.cli.BladeCLI;

import org.xml.sax.SAXException;

public class ClasspathResolver {

  private final BladeCLI bladeCli;
  private final Path baseModulePath;
  private final Path workspacePath;
  private final Map<String, Path> modules = new TreeMap<>();
  private final Map<String, Path> submodules = new TreeMap<>();

  public ClasspathResolver(BladeCLI bladeCli) throws Exception {
    this.bladeCli = bladeCli;

    this.baseModulePath = bladeCli.getBase().toPath().normalize().toAbsolutePath();

    this.workspacePath = findWorkspacePath();

    doIndexModules();
  }

  private Path findWorkspacePath() throws Exception {

    Path currentModule = baseModulePath;

    do {
      if (Files.exists(currentModule.resolve("settings.gradle"))) {
        return currentModule;
      }
      currentModule = currentModule.getParent();

    } while (currentModule != null);

    throw new Exception("Could not find workspace directory. settings.gradle not found");
  }

  private void doIndexModules() {
    doIndexModules(workspacePath.toFile(), workspacePath.equals(baseModulePath));
  }

  private void doIndexModules(File directory, boolean isSubmodule) {

    if (new File(directory, "build.gradle").exists()) {

      String name = directory.getName();
      Path path = directory.toPath();

      modules.put(name, path);

      if (isSubmodule) {
        submodules.put(name, path);
      }
    }

    File[] files = directory.listFiles();

    for (int i = 0, length = files.length; i < length; i++) {

      File file = files[i];

      if (file.isDirectory()) {

        String name = file.getName();

        if (!name.matches("^(build|classes|node_modules|out|src)$")) {
          doIndexModules(file, isSubmodule || file.toPath().equals(baseModulePath));
        }
      }
    }
  }

  public void resolve() {
    bladeCli.out("[INFO] Resolving modules");

    submodules.forEach(
        (name, path) -> {
          try {

            bladeCli.out("[INFO] Resolving: " + name);

            resolve(path);

          } catch (Exception e) {
            bladeCli.err("[WARN] Unable to resolve: " + e.getMessage());
          }
        });
  }

  public void resolve(Path module)
      throws ParserConfigurationException, SAXException, IOException, TransformerException {

    File file = module.resolve(".classpath").toFile();

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

    String path = classpathEntry.get(Attribute.PATH);

    if (modules.containsKey(path)) {

      Path module = modules.get(path);

      classpathEntry.set(Attribute.KIND, Kind.LIB);
      classpathEntry.set(Attribute.PATH, module.resolve("classes"));
      classpathEntry.set(Attribute.SOURCEPATH, module.resolve("src/main/java"));
    }
  }
}
