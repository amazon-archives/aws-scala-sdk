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
 *
 */
public final class ClientModel {

    private static final Class<?> AMAZON_WEB_SERVICE_REQUEST;
    private static final Class<?> ASYNC_HANDLER;

    static {
        try {
            AMAZON_WEB_SERVICE_REQUEST = Class.forName("com.amazonaws.AmazonWebServiceRequest");
            ASYNC_HANDLER = Class.forName("com.amazonaws.handlers.AsyncHandler");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private final String pkg;
    private final String classPrefix;
    private final List<OperationModel> operations;

    public ClientModel(String pkg, String classPrefix) {
        this.pkg = pkg;
        this.classPrefix = classPrefix;

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

    public String getPackage() {
        return pkg;
    }

    public String getClassPrefix() {
        return classPrefix;
    }

    public List<OperationModel> getOperations() {
        return operations;
    }
}
