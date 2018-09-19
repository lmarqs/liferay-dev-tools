/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package br.com.objective.liferay.blade.extensions.eclipse.command;

import com.liferay.blade.cli.BladeCLI;
import com.liferay.blade.cli.command.BaseCommand;
import com.liferay.blade.cli.gradle.GradleExec;
import br.com.objective.liferay.blade.extensions.eclipse.classpath.Classpath;
import br.com.objective.liferay.blade.extensions.eclipse.util.HelloUtil;

public class Generate extends BaseCommand<GenerateArgs> {

    @Override
    public void execute() throws Exception {
        GenerateArgs generateArgs = getArgs();

        BladeCLI bladeCli = getBladeCLI();

        GradleExec gradleExec = new GradleExec(bladeCli);
        gradleExec.executeTask("eclipse");

        Classpath classpath = new Classpath(generateArgs.getBase());

        bladeCli.out(HelloUtil.getHello(generateArgs));
    }

    @Override
    public Class<GenerateArgs> getArgsClass() {
        return GenerateArgs.class;
    }
}
