/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package br.com.objective.liferay.blade.extensions.eclipse.util;

import br.com.objective.liferay.blade.extensions.eclipse.command.GenerateArgs;

/** @author Liferay */
public class HelloUtil {

    public static String getHello(GenerateArgs generateArgs) {
        return "Generate " + generateArgs.getLibs() + "!";
    }
}
