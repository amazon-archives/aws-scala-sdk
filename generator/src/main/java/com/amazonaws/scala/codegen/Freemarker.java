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
package com.amazonaws.scala.codegen;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 */
public final class Freemarker {

    private static final Configuration CONFIGURATION;
    static {
        Configuration config = new Configuration(Configuration.VERSION_2_3_21);

        config.setClassForTemplateLoading(Freemarker.class, "/templates");
        config.setDefaultEncoding("UTF-8");

        CONFIGURATION = config;
    }

    public static Template getClientTemplate() {
        try {
            return CONFIGURATION.getTemplate("scala-client.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Template getBuildSbtTemplate() {
        try {
            return CONFIGURATION.getTemplate("build-sbt.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Freemarker() {
    }
}
