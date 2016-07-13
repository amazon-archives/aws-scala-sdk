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
package com.amazonaws.generator.scala;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Mojo that generates a Scala wrapper for a client class from the AWS SDK for
 * Java.
 */
@Mojo(name = "generate")
public class GeneratorMojo extends AbstractMojo {

    private final Freemarker freemarker;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/aws-scala-sdk")
    private File baseDir;

    @Parameter(property = "package", required = true)
    private String pkg;

    @Parameter(required = true)
    private String classPrefix;

    @Parameter(required = true)
    private boolean shutdownSupported;

    public GeneratorMojo() {
        freemarker = new Freemarker();
    }

    @Override
    public void execute() throws MojoExecutionException {
        try {
            generateClient();
        } catch (Exception e) {
            throw new MojoExecutionException("Generation failed", e);
        }

        project.addCompileSourceRoot(baseDir.getPath());
    }

    private void generateClient() throws IOException, TemplateException {

        Template template = freemarker.getClientTemplate();

        ClientModel model = new ClientModel(pkg, classPrefix, shutdownSupported);

        String path = String.format(
                "com/amazonaws/services/%s/scala/%sClient.scala",
                pkg,
                classPrefix);

        File file = new File(baseDir, path);
        file.getParentFile().mkdirs();

        Writer writer = new FileWriter(file);
        try {
            template.process(model, writer);
        } finally {
            writer.close();
        }
    }
}
