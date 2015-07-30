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
package com.amazonaws.services.${package}.scala

import scala.concurrent.Future
import scala.concurrent.Promise

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.RequestClientOptions
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.regions.RegionUtils
import com.amazonaws.services.${package}.${classPrefix}Async
import com.amazonaws.services.${package}.${classPrefix}AsyncClient

class ${classPrefix}Client(
  private val client: ${classPrefix}Async,
  private val region: Region) {

  client.setRegion(region)

  def this(region: Region) = this(new ${classPrefix}AsyncClient(), region)

  def this(region: String) = this(RegionUtils.getRegion(region))

  def this(region: Regions) = this(Region.getRegion(region))

<#list operations as operation>
  def ${operation.methodName}(request: ${operation.requestType}):
      Future[${operation.resultType}] = {

    invoke${operation.invokeSuffix}(client.${operation.methodName}Async, request)
  }

</#list>
  def shutdown(): Unit = client.shutdown()
  
  private def invoke[Request <: AmazonWebServiceRequest, Result](
      method: (Request, AsyncHandler[Request, Result]) => java.util.concurrent.Future[Result],
      request: Request): Future[Result] = {

    val opts = request.getRequestClientOptions()
    if (opts.getClientMarker(RequestClientOptions.Marker.USER_AGENT) == null) {
      opts.appendUserAgent("aws-scala-sdk")
    }

    val promise = Promise[Result]

    method(request, new AsyncHandler[Request, Result]() {
      override def onSuccess(request: Request, result: Result) = promise.success(result)
      override def onError(exception: Exception) = promise.failure(exception)
    })

    promise.future
  }

  private def invokeVoid[Request <: AmazonWebServiceRequest](
      method: (Request, AsyncHandler[Request, Void]) => java.util.concurrent.Future[Void],
      request: Request): Future[Unit] = {

    val opts = request.getRequestClientOptions()
    if (opts.getClientMarker(RequestClientOptions.Marker.USER_AGENT) == null) {
      opts.appendUserAgent("aws-scala-sdk")
    }

    val promise = Promise[Unit]
    
    method(request, new AsyncHandler[Request, Void]() {
      override def onSuccess(request: Request, result: Void) = promise.success(())
      override def onError(exception: Exception) = promise.failure(exception)
    })

    promise.future
  }
}
