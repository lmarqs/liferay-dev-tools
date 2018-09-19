/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
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

    public static void main(String argv[]) {
    }

    public Classpath read() throws ParserConfigurationException, IOException, SAXException {

        return this;
    }
}
