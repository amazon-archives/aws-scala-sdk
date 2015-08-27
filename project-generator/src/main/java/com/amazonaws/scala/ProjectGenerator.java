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
 *
 */
public final class ProjectGenerator {

    private final File baseDir;
    private final Freemarker freemarker;
    private final ObjectMapper mapper;

    public ProjectGenerator(File baseDir) {
        this(baseDir, new Freemarker(), new ObjectMapper(new YAMLFactory()));
    }

    public ProjectGenerator(
            File baseDir,
            Freemarker freemarker,
            ObjectMapper mapper) {

        this.baseDir = baseDir;
        this.freemarker = freemarker;
        this.mapper = mapper;
    }

    public void generate() throws IOException, TemplateException {
        File configFile = new File(baseDir, "config.yaml");
        AggregatorModel aggregator = mapper.readValue(configFile, AggregatorModel.class);

        File project = new File(baseDir, "project");
        mkdir(project);

        Template pom = freemarker.getPomTemplate();
        for (ModuleModel module : aggregator.getModules()) {
            File dir = new File(project, module.getName());
            mkdir(dir);

            Writer writer = new FileWriter(new File(dir, "pom.xml"));
            try {
                pom.process(module, writer);
            } finally {
                writer.close();
            }
        }

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
