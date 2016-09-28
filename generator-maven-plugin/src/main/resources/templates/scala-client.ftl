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

import com.amazonaws.services.${package}.${classPrefix}Async
import com.amazonaws.services.${package}.${classPrefix}AsyncClient
import com.amazonaws.services.${package}.model._

/** A Scala-friendly wrapper around an {@code ${classPrefix}Async}. */
class ${classPrefix}Client(private val client: ${classPrefix}Async) {

  /** Creates a client for the given region.
    *
    * @param region the region
    */
  def this(region: com.amazonaws.regions.Region) =
    this({
      val client = new ${classPrefix}AsyncClient()
      client.setRegion(region)
      client
    })

  /** Creates a client for the given region.
    *
    * @param region the region
    */
  def this(region: com.amazonaws.regions.Regions) =
    this(com.amazonaws.regions.Region.getRegion(region))

  /** Creates a client for the given region.
    *
    * @param region the region
    */
  def this(region: String) =
    this(com.amazonaws.regions.RegionUtils.getRegion(region))

<#list operations as operation>
  /** Invokes the {@code ${operation.methodName}Async} method of the underlying
    * client and adapts the result to a Scala {@code Future}.
    *
    * @param request the request to send
    * @return the future result
    */
  def ${operation.methodName}(request: ${operation.requestType}):
      scala.concurrent.Future[${operation.resultType}] = {

    val opts = request.getRequestClientOptions()
    if (opts.getClientMarker(com.amazonaws.RequestClientOptions.Marker.USER_AGENT) == null) {
      opts.appendUserAgent("aws-scala-sdk")
    }

    val promise = scala.concurrent.Promise[${operation.resultType}]

    client.${operation.methodName}Async(request, new com.amazonaws.handlers.AsyncHandler[${operation.requestType}, ${operation.javaResultType}]() {
      override def onSuccess(request: ${operation.requestType}, result: ${operation.javaResultType}) = promise.success(${operation.result})
      override def onError(exception: Exception) = promise.failure(exception)
    })

    promise.future
  }

</#list>
<#if shutdownSupported>
  /** Shuts down this client. */
  def shutdown(): Unit = client.shutdown()
</#if>
}
