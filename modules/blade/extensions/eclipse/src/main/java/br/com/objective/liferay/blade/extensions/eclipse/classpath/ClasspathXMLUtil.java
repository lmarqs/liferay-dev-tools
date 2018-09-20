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
package br.com.objective.liferay.blade.extensions.eclipse.classpath;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import br.com.objective.liferay.blade.extensions.eclipse.classpath.ClasspathEntry.Attribute;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ClasspathXMLUtil {

  public static final String TAG_NAME_CLASSPATH_ENTRY = "classpathentry";
  public static final String TAG_NAME_CLASSPATH = "classpath";
  public static final String YES = "yes";
  public static final String EMPTY_VALUE = "";

  public static Classpath readClasspath(File file)
      throws IOException, SAXException, ParserConfigurationException {
    Document document = readDocument(file);

    Set<ClasspathEntry> classpathEntries = getClasspathEntries(document);

    return new Classpath(classpathEntries);
  }

  public static Document readDocument(File file)
      throws ParserConfigurationException, IOException, SAXException {

    Document document = getDocument(file);

    return document;
  }

  private static Document getDocument(File file)
      throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document = builder.parse(file);

    // optional, but recommended
    // read this:
    // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    Element element = document.getDocumentElement();
    element.normalize();
    return document;
  }

  public static Set<ClasspathEntry> getClasspathEntries(Document document) {

    NodeList nodes = document.getElementsByTagName(TAG_NAME_CLASSPATH_ENTRY);

    int length = nodes.getLength();

    Set<ClasspathEntry> entries = new HashSet<>(length);

    for (int i = 0; i < length; i++) {

      Node node = nodes.item(i);

      if (node.getNodeType() == Node.ELEMENT_NODE) {

        Element element = (Element) node;

        entries.add(getClasspathEntry(element));
      }
    }

    return entries;
  }

  public static ClasspathEntry getClasspathEntry(Element element) {

    Attribute[] values = Attribute.values();

    Map<String, String> attributes = new HashMap<>(values.length);

    Arrays.stream(values)
        .map(Attribute::getName)
        .forEach(
            name -> {
              String value = element.hasAttribute(name) ? element.getAttribute(name) : EMPTY_VALUE;
              attributes.put(name, value);
            });

    return new ClasspathEntry(attributes);
  }

  public static void writeClasspath(File file, Classpath classpath)
      throws TransformerException, ParserConfigurationException {

    Document document = getDocument(classpath);

    writeDocument(file, document);
  }

  private static Document getDocument(Classpath classpath) throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document = builder.newDocument();

    // add elements to Document
    Element root = getElement(document, classpath);

    // append root element to document
    document.appendChild(root);

    classpath
        .getClasspathEntries()
        .stream()
        .map(entry -> getElement(document, entry))
        .forEach(root::appendChild);
    return document;
  }

  private static void writeDocument(File file, Document document) throws TransformerException {
    // for output to file, console
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    // for pretty print
    transformer.setOutputProperty(OutputKeys.INDENT, YES);
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

    DOMSource source = new DOMSource(document);

    transformer.transform(source, new StreamResult(file));
  }

  private static Element getElement(Document document, Classpath classpath) {
    return document.createElement(TAG_NAME_CLASSPATH);
  }

  private static Element getElement(Document document, ClasspathEntry classpathEntry) {
    Element element = document.createElement(TAG_NAME_CLASSPATH_ENTRY);

    Attribute[] values = Attribute.values();

    Arrays.stream(values)
        .map(Attribute::getName)
        .forEach(
            name -> {
              String value = classpathEntry.get(name);
              element.setAttribute(name, value);
            });

    return element;
  }
}
