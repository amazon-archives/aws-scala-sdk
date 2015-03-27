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
package com.amazonaws.scala.codegen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.handlers.AsyncHandler;

/**
 *
 */
public class ClientModel {

    private String pkg;
    private String classPrefix;
    private List<OperationModel> operations;

    public ClientModel(String pkg, String classPrefix) {
        this.pkg = pkg;
        this.classPrefix = classPrefix;

        String name = String.format(
                "com.amazonaws.services.%s.%sAsync",
                pkg,
                classPrefix);

        try {
            List<OperationModel> list = new ArrayList<>();

            Class<?> clazz = Class.forName(name);
            for (Method method : clazz.getMethods()) {
                if (method.getName().endsWith("Async")
                 && method.getParameterCount() == 2
                 && AmazonWebServiceRequest.class.isAssignableFrom(method.getParameters()[0].getType())
                 && AsyncHandler.class.isAssignableFrom(method.getParameters()[1].getType())) {

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
