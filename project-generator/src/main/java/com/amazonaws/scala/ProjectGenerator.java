/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.scala;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Main point of entry for the project generator.
 */
public final class ProjectGenerator {

    private final File baseDir;
    private final Freemarker freemarker;
    private final ObjectMapper mapper;

    /**
     * @param baseDir the base directory of the project
     */
    public ProjectGenerator(File baseDir) {
        this(baseDir, new Freemarker(), new ObjectMapper(new YAMLFactory()));
    }

    /**
     * @param baseDir the base directory of the project
     * @param freemarker a utility for working with freemarker
     * @param mapper a Jackson ObjectMapper for loading the YAML configuration
     */
    public ProjectGenerator(
            File baseDir,
            Freemarker freemarker,
            ObjectMapper mapper) {

        this.baseDir = baseDir;
        this.freemarker = freemarker;
        this.mapper = mapper;
    }

    /**
     * Generates a multi-module maven project structure under
     * {@code $baseDir/project} from the configuration in
     * {@code $baseDir/config.yaml}.
     */
    public void generate() throws IOException, TemplateException {
        File configFile = new File(baseDir, "config.yaml");
        AggregatorModel aggregator = mapper.readValue(configFile, AggregatorModel.class);

        File project = new File(baseDir, "project");
        mkdir(project);

        // Generate the individual client modules.
        Template pom = freemarker.getPomTemplate();
        for (ModuleModel module : aggregator.getModules()) {
            File dir = new File(project, "aws-scala-sdk-" + module.getName());
            mkdir(dir);

            Writer writer = new FileWriter(new File(dir, "pom.xml"));
            try {
                pom.process(module, writer);
            } finally {
                writer.close();
            }
        }

        // Generate the aggregator module.
        Template agg = freemarker.getAggregatorTemplate();

        Writer writer = new FileWriter(new File(project, "pom.xml"));
        try {
            agg.process(aggregator, writer);
        } finally {
            writer.close();
        }
    }

    private void mkdir(File dir) throws IOException {
        if (dir.exists()) {
            for (String file : dir.list()) {
                delete(new File(dir, file));
            }
        } else {
            if (!dir.mkdirs()) {
                throw new IOException("Error creating directory " + dir);
            }
        }
    }

    private void delete(File file) throws IOException {
        if (file.isDirectory()) {
            for (String f : file.list()) {
                delete(new File(file, f));
            }
        }
        if (!file.delete()) {
            throw new IOException("Could not delete " + file);
        }
    }

    /**
     * Main point of entry from the command line. Pass the base directory for
     * generation as the only argument.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: generator <basedir>");
            System.exit(-1);
        }

        File baseDir = new File(args[0]);
        System.out.println("Using baseDir: " + baseDir.getAbsolutePath());

        new ProjectGenerator(baseDir).generate();
    }
}
