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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model type for an individual client module POM.
 */
public final class ModuleModel {

    private final String name;
    private final String classPrefix;
    private final String serviceName;
    private final String pkg;
    private final String shutdownSupported;

    private String sdkVersion;

    /**
     * @param name the name of the module (ie 'kinesis')
     * @param classPrefix the class prefix (ie 'AmazonKinesis')
     * @param serviceName the (optional) name of the service (ie 'Kinesis')
     * @param pkg the Java package name to use, if different than {@code name}
     * @param shutdownSupported whether the service supports shutdown, default = true
     */
    public ModuleModel(
            @JsonProperty(value="name", required=true) String name,
            @JsonProperty(value="classPrefix", required=true) String classPrefix,
            @JsonProperty(value="serviceName") String serviceName,
            @JsonProperty(value="package") String pkg,
            @JsonProperty(value="shutdownSupported") String shutdownSupported) {

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

        if (shutdownSupported != null) {
            this.shutdownSupported = Boolean.valueOf(shutdownSupported).toString();
        } else {
            this.shutdownSupported = Boolean.TRUE.toString();
        }
    }

    /**
     * @return the name of this module (ie 'dynamodb')
     */
    public String getName() {
        return name;
    }

    /**
     * @return the class prefix (ie 'AmazonDynamoDB')
     */
    public String getClassPrefix() {
        return classPrefix;
    }

    /**
     * @return the service name (ie 'DynamoDB')
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return the Java package (ie 'dynamodbv2')
     */
    public String getPackage() {
        return pkg;
    }

    /**
     * @return whether the services supports the shutdown() method
     */
    public String getShutdownSupported() {
        return shutdownSupported;
    }

    /**
     * @return the version of the SDK to depend on
     */
    public String getSdkVersion() {
        return sdkVersion;
    }

    /**
     * This gets injected after the fact by the AggregatorModel that we're
     * nested under.
     *
     * @param value the version of the SDK to depend on
     */
    void setSdkVersion(String value) {
        sdkVersion = value;
    }
}
