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
