package com.amazonaws.scala;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public final class ModuleModel {

    private final String name;
    private final String classPrefix;
    private final String serviceName;
    private final String pkg;

    private String sdkVersion;

    public ModuleModel(
            @JsonProperty(value="name", required=true) String name,
            @JsonProperty(value="classPrefix", required=true) String classPrefix,
            @JsonProperty(value="serviceName") String serviceName,
            @JsonProperty(value="package") String pkg) {

        this.name = name;
        this.classPrefix = classPrefix;

        if (serviceName != null) {
            this.serviceName = serviceName;
        } else if (classPrefix.startsWith("Amazon")) {
            this.serviceName = classPrefix.substring(6);
        } else if (classPrefix.startsWith("AWS")) {
            this.serviceName = classPrefix.substring(3);
        } else {
            this.serviceName = classPrefix;
        }

        if (pkg != null) {
            this.pkg = pkg;
        } else {
            this.pkg = name;
        }
    }

    public String getName() {
        return name;
    }

    public String getClassPrefix() {
        return classPrefix;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPackage() {
        return pkg;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String value) {
        sdkVersion = value;
    }
}
