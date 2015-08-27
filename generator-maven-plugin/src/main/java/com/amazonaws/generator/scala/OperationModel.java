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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public final class OperationModel {

    private final String methodName;
    private final String requestType;
    private final String resultType;
    private final String javaResultType;
    private final String result;

    public OperationModel(Method method) {
        this.methodName = method.getName().substring(0, method.getName().length() - 5);
        this.requestType = method.getParameters()[0].getType().getSimpleName();

        Type rtype = method.getGenericReturnType();
        if (!(rtype instanceof ParameterizedType)) {
            throw new RuntimeException("Not parameterized: " + rtype);
        }

        Type ptype = ((ParameterizedType) rtype).getActualTypeArguments()[0];
        if (ptype == Void.class) {
            this.resultType = "scala.runtime.BoxedUnit";
            this.javaResultType = "Void";
            this.result = "scala.runtime.BoxedUnit.UNIT";
        } else {
            this.resultType = ((Class<?>) ptype).getSimpleName();
            this.javaResultType = this.resultType;
            this.result = "result";
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getResultType() {
        return resultType;
    }

    public String getJavaResultType() {
        return javaResultType;
    }

    public String getResult() {
        return result;
    }
}
