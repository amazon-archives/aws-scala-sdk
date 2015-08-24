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
 * Model type representing a single operation of a service client.
 */
public final class OperationModel {

    private final String methodName;
    private final String requestType;
    private final String resultType;
    private final String javaResultType;
    private final String result;

    /**
     * @param method the xxxAsync(AmazonWebServiceRequest, AsyncHandler) method
     *            to wrap
     */
    public OperationModel(Method method) {

        // Strip out the trailing "Async"
        this.methodName = method.getName().substring(0, method.getName().length() - 5);

        this.requestType = method.getParameters()[0].getType().getSimpleName();

        Type returnType = method.getGenericReturnType();
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Not parameterized: " + returnType);
        }

        // Extract the actual result type from inside the Future<?>
        Type resultType = ((ParameterizedType) returnType)
                .getActualTypeArguments()[0];

        if (resultType == Void.class) {
            this.resultType = "scala.runtime.BoxedUnit";
            this.javaResultType = "Void";
            this.result = "scala.runtime.BoxedUnit.UNIT";
        } else {
            this.resultType = ((Class<?>) resultType).getSimpleName();
            this.javaResultType = this.resultType;
            this.result = "result";
        }
    }

    /**
     * @return the name of the method to generate
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the type of the request object for the method
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @return the type of the result object for the method
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * @return the type of the underlying Java result object
     */
    public String getJavaResultType() {
        return javaResultType;
    }

    /**
     * @return the result to return (either 'result' or 'BoxedUnit.UNIT')
     */
    public String getResult() {
        return result;
    }
}
