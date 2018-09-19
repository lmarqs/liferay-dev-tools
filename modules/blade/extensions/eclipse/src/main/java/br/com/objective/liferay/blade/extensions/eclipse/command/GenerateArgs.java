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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.liferay.blade.cli.command.BaseArgs;

/** @author Liferay */
@Parameters(commandDescription = "Generates eclipse project related artifacts", commandNames = "generate")
public class GenerateArgs extends BaseArgs {

    public String getLibs() {
        return libs;
    }

    @Parameter(description = "The path where the libs are located", names = { "--libs",
            "-l" }, required = true)
    private String libs;
}
