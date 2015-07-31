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

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Utility class for working with freemarker.
 */
public final class Freemarker {

    private final Configuration CONFIGURATION;

    public Freemarker() {
        Configuration config = new Configuration(Configuration.VERSION_2_3_21);

        config.setClassForTemplateLoading(Freemarker.class, "/templates");
        config.setDefaultEncoding("UTF-8");

        CONFIGURATION = config;
    }

    public Template getClientTemplate() {
        try {
            return CONFIGURATION.getTemplate("scala-client.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
