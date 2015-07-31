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
package com.amazonaws.services.${package}.scala;

import java.util.function.BiFunction;

import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.runtime.BoxedUnit;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.${package}.${classPrefix}Async;
import com.amazonaws.services.${package}.${classPrefix}AsyncClient;

public class ${classPrefix}Client {

    private final ${classPrefix}Async client;

    public ${classPrefix}Client(String region) {
        this(RegionUtils.getRegion(region));
    }

    public ${classPrefix}Client(Regions region) {
        this(Region.getRegion(region));
    }

    public ${classPrefix}Client(Region region) {
        this(newClient(region));
    }

    private static ${classPrefix}AsyncClient newClient(Region region) {
        if (region == null) {
            throw new NullPointerException("region");
        }

        ${classPrefix}AsyncClient client = new ${classPrefix}AsyncClient();
        client.setRegion(region);
        return client;
    }

    public ${classPrefix}Client(${classPrefix}Async client) {
        if (client == null) {
            throw new NullPointerException("client");
        }
        this.client = client;
    }

<#list operations as operation>
    public Future<${operation.resultType}> ${operation.methodName}(
            ${operation.requestType} request) {

        return invoke${operation.invokeSuffix}(client::${operation.methodName}Async, request);
    }

</#list>
    public void shutdown() {
        client.shutdown();
    }

    private <I extends AmazonWebServiceRequest, O> Future<O> invoke(
            BiFunction<I, AsyncHandler<I, O>, java.util.concurrent.Future<O>> f,
            I request) {

        final Promise<O> promise = Promise$.MODULE$.apply();

        f.apply(request, new AsyncHandler<I, O>() {

            @Override
            public void onSuccess(I request, O result) {
                promise.success(result);
            }

            @Override
            public void onError(Exception exception) {
                promise.failure(exception);
            }
        });

        return promise.future();
    }

    private <I extends AmazonWebServiceRequest> Future<BoxedUnit> invokeVoid(
            BiFunction<I, AsyncHandler<I, Void>, java.util.concurrent.Future<Void>> f,
            I request) {

        final Promise<BoxedUnit> promise = Promise$.MODULE$.apply();

        f.apply(request, new AsyncHandler<I, Void>() {

            @Override
            public void onSuccess(I request, Void result) {
                promise.success(BoxedUnit.UNIT);
            }

            @Override
            public void onError(Exception exception) {
                promise.failure(exception);
            }
        });

        return promise.future();
    }
}
