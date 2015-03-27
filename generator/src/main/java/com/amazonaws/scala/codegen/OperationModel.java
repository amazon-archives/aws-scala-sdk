package com.amazonaws.scala.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public class OperationModel {

    private String methodName;
    private String requestType;
    private String resultType;
    private String invokeSuffix;

    public OperationModel(Method method) {
        this.methodName = method.getName().substring(0, method.getName().length() - 5);
        this.requestType = method.getParameters()[0].getType().getCanonicalName();

        Type rtype = method.getGenericReturnType();
        if (!(rtype instanceof ParameterizedType)) {
            throw new RuntimeException("Not parameterized: " + rtype);
        }

        Type ptype = ((ParameterizedType) rtype).getActualTypeArguments()[0];
        if (ptype == Void.class) {
            this.resultType = "Unit";
            this.invokeSuffix = "Void";
        } else {
            this.resultType = ((Class<?>) ptype).getCanonicalName();
            this.invokeSuffix = "";
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

    public String getInvokeSuffix() {
        return invokeSuffix;
    }
}
