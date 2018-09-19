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

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Classpath {

  private final File file;

  public Classpath(String pathname) {
    this.file = new File(pathname);
  }

  public Classpath(File file) {
    this.file = file;
  }

  public static void main(String argv[]) {}

  public Classpath read()
      throws ParserConfigurationException, IOException, SAXException {

    return this;
  }
}
