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
package br.com.objective.liferay.blade.extensions.eclipse.command;

import java.io.File;

import br.com.objective.liferay.blade.extensions.eclipse.classpath.Classpath;
import br.com.objective.liferay.blade.extensions.eclipse.classpath.ClasspathResolver;
import br.com.objective.liferay.blade.extensions.eclipse.util.ClasspathXMLUtil;

import com.liferay.blade.cli.BladeCLI;
import com.liferay.blade.cli.command.BaseCommand;
import com.liferay.blade.cli.gradle.GradleExec;

public class GenerateCommand extends BaseCommand<GenerateArgs> {

  @Override
  public void execute() throws Exception {
    BladeCLI bladeCli = getBladeCLI();

    bladeCli.out("Generating Eclipse IDE artifacts");

    GenerateArgs generateArgs = getArgs();

    GradleExec gradleExec = new GradleExec(bladeCli);
    gradleExec.executeGradleCommand("clean assemble eclipse");

    File file = new File(bladeCli.getBase(), ".classpath");

    Classpath classpath = ClasspathXMLUtil.readClasspath(file);

    ClasspathResolver resolver = new ClasspathResolver(file, classpath);

    ClasspathXMLUtil.writeClasspath(file, resolver.resolve());

    bladeCli.out("done");
  }

  @Override
  public Class<GenerateArgs> getArgsClass() {
    return GenerateArgs.class;
  }
}
