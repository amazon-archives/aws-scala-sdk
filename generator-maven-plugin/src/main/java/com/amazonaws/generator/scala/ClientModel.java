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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Model type representing the client wrapper.
 */
public final class ClientModel {

    private static final Class<?> AMAZON_WEB_SERVICE_REQUEST;
    private static final Class<?> ASYNC_HANDLER;

    static {
        // Grab these via reflection to avoid hardcoding a dependency on a
        // particular version of the SDK here. The user is responsible for
        // adding an appropriate version of the SDK to the classpath.
        try {
            AMAZON_WEB_SERVICE_REQUEST = Class.forName("com.amazonaws.AmazonWebServiceRequest");
            ASYNC_HANDLER = Class.forName("com.amazonaws.handlers.AsyncHandler");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private final String pkg;
    private final String classPrefix;
    private final boolean shutdownSupported;
    private final List<OperationModel> operations;

    /**
     * @param pkg the name of the Java package the target client belongs to (ie
     *            'kinesis' for 'com.amazonaws.services.kinesis._)
     * @param classPrefix the initial part of the class name (ie 'AmazonKinesis'
     *            for 'AmazonKinesisClientAsync')
     */
    public ClientModel(String pkg, String classPrefix, boolean shutdownSupported) {

        this.pkg = pkg;
        this.classPrefix = classPrefix;
        this.shutdownSupported = shutdownSupported;

        String name = String.format(
                "com.amazonaws.services.%s.%sAsync",
                pkg,
                classPrefix);

        try {
            List<OperationModel> list = new ArrayList<OperationModel>();

            Class<?> clazz = Class.forName(name);
            for (Method method : clazz.getMethods()) {
                if (method.getName().endsWith("Async")
                 && method.getParameterCount() == 2
                 && AMAZON_WEB_SERVICE_REQUEST.isAssignableFrom(method.getParameters()[0].getType())
                 && ASYNC_HANDLER.isAssignableFrom(method.getParameters()[1].getType())) {

                    list.add(new OperationModel(method));
                }
            }

            this.operations = list;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the package (under com.amazonaws.services) to generate under
     */
    public String getPackage() {
        return pkg;
    }

    /**
     * @return the start of the class name to generate
     */
    public String getClassPrefix() {
        return classPrefix;
    }

    /**
     * @return the start of the class name to generate
     */
    public boolean isShutdownSupported() {
        return shutdownSupported;
    }

    /**
     * @return the list of operations for the client
     */
    public List<OperationModel> getOperations() {
        return operations;
    }
}
