package com.amazonaws.scala;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public final class AggregatorModel {

    private final String parentPomVersion;
    private final String sdkVersion;
    private final List<ModuleModel> modules;

    public AggregatorModel(
            @JsonProperty(value="parentPomVersion", required=true) String parentPomVersion,
            @JsonProperty(value="sdkVersion", required=true) String sdkVersion,
            @JsonProperty(value="modules", required=true) List<ModuleModel> modules) {

        this.parentPomVersion = parentPomVersion;
        this.sdkVersion = sdkVersion;

        this.modules = Collections.unmodifiableList(modules);
        for (ModuleModel module : modules) {
            module.setSdkVersion(sdkVersion);
        }
    }

    public String getParentPomVersion() {
        return parentPomVersion;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public List<ModuleModel> getModules() {
        return modules;
    }
}
