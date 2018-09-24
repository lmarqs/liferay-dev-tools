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

import br.com.objective.liferay.blade.extensions.eclipse.util.ClasspathResolver;

import com.liferay.blade.cli.BladeCLI;
import com.liferay.blade.cli.command.BaseCommand;
import com.liferay.blade.cli.gradle.GradleExec;

public class GenerateCommand extends BaseCommand<GenerateArgs> {

  @Override
  public void execute() throws Exception {
    BladeCLI bladeCli = getBladeCLI();

    bladeCli.out("[INFO] \uD83D\uDE80 Generating Eclipse IDE artifacts");

    generate();

    bladeCli.out("[INFO] \uD83D\uDE80 Resolving modules dependencies");

    resolve();

    bladeCli.out("[INFO] \uD83D\uDC4D Done");
  }

  private void resolve() throws Exception {
    ClasspathResolver resolver = new ClasspathResolver(getBladeCLI());

    resolver.resolve();
  }

  private void generate() throws Exception {
    GradleExec gradleExec = new GradleExec(getBladeCLI());

    GenerateArgs args = getArgs();

    StringBuilder commandBuilder = new StringBuilder();

    if (!args.skipAssemble) {
      commandBuilder.append(" clean assemble ");
    }

    commandBuilder.append(" eclipse ");

    String command = commandBuilder.toString();

    gradleExec.executeGradleCommand(command.replaceAll("[ ]+", " "));
  }

  @Override
  public Class<GenerateArgs> getArgsClass() {
    return GenerateArgs.class;
  }
}
