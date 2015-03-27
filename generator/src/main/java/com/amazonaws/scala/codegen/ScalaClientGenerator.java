package com.amazonaws.scala.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *
 */
public class ScalaClientGenerator {

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            usage();
            return;
        }

        File baseDir = new File(args[0]);
        String service = args[1];
        String pkg = args[2];
        String classPrefix = args[3];

        generateBuildSbt(baseDir, service);
        generateClient(baseDir, pkg, classPrefix);
    }

    private static void usage() {
        System.err.println(
                "usage: java com.amazonaws.scala.codegen.ScalaClientGenerator "
                + "<baseDir> <service> <package> <clientPrefix>");
    }

    private static void generateBuildSbt(
        File baseDir,
        String service
    ) throws IOException, TemplateException {

        Template template = Freemarker.getBuildSbtTemplate();

        BuildSbtModel model = new BuildSbtModel(service);

        File file = new File(baseDir, "build.sbt");
        baseDir.mkdirs();

        try (Writer writer = new FileWriter(file)) {
            template.process(model, writer);
        }
    }

    private static void generateClient(
        File baseDir,
        String pkg,
        String classPrefix
    ) throws IOException, TemplateException {

        Template template = Freemarker.getClientTemplate();

        ClientModel model = new ClientModel(pkg, classPrefix);

        String path = String.format(
                "src/main/scala/com/amazonaws/services/%s/scala/%sClient.scala",
                pkg,
                classPrefix);

        File file = new File(baseDir, path);
        file.getParentFile().mkdirs();

        try (Writer writer = new FileWriter(file)) {
            template.process(model, writer);
        }
    }
}
