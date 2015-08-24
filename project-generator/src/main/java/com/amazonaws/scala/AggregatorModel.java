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

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model type for the aggregator POM.
 */
public final class AggregatorModel {

    private final String parentPomVersion;
    private final String sdkVersion;
    private final List<ModuleModel> modules;

    /**
     * @param parentPomVersion the version of the parent POM to use
     * @param sdkVersion the version of the SDK to depend on
     * @param modules the list of modules to generate
     */
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

    /**
     * @return the version of the parent POM to use
     */
    public String getParentPomVersion() {
        return parentPomVersion;
    }

    /**
     * @return the version of the SDK to depend on
     */
    public String getSdkVersion() {
        return sdkVersion;
    }

    /**
     * @return the list of modules to generate
     */
    public List<ModuleModel> getModules() {
        return modules;
    }
}
