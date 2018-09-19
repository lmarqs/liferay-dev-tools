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
package br.com.objective.liferay.blade.extensions.eclipse.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.liferay.blade.cli.command.BaseArgs;

/** @author Liferay */
@Parameters(
    commandDescription = "Provides Eclipse IDE helpers",
    commandNames = "eclipse generate"
)
public class GenerateArgs extends BaseArgs {

  public String getLibs() {
    return libs;
  }

  @Parameter(
      description = "The paths (comma separated) where the libs are located",
      names = {"--libs", "-l"}
  )
  private String libs;
}
