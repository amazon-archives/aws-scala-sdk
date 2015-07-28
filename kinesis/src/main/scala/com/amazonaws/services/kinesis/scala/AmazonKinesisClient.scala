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
package com.amazonaws.services.kinesis.scala

import scala.concurrent.Future
import scala.concurrent.Promise

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.regions.RegionUtils
import com.amazonaws.services.kinesis.AmazonKinesisAsync
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient

class AmazonKinesisClient(
  private val client: AmazonKinesisAsync,
  private val region: Region) {

  client.setRegion(region)

  def this(region: Region) = this(new AmazonKinesisAsyncClient(), region)

  def this(region: String) = this(RegionUtils.getRegion(region))

  def this(region: Regions) = this(Region.getRegion(region))

  def addTagsToStream(request: com.amazonaws.services.kinesis.model.AddTagsToStreamRequest):
      Future[Unit] = {

    invokeVoid(client.addTagsToStreamAsync, request)
  }

  def createStream(request: com.amazonaws.services.kinesis.model.CreateStreamRequest):
      Future[Unit] = {

    invokeVoid(client.createStreamAsync, request)
  }

  def mergeShards(request: com.amazonaws.services.kinesis.model.MergeShardsRequest):
      Future[Unit] = {

    invokeVoid(client.mergeShardsAsync, request)
  }

  def removeTagsFromStream(request: com.amazonaws.services.kinesis.model.RemoveTagsFromStreamRequest):
      Future[Unit] = {

    invokeVoid(client.removeTagsFromStreamAsync, request)
  }

  def listStreams(request: com.amazonaws.services.kinesis.model.ListStreamsRequest):
      Future[com.amazonaws.services.kinesis.model.ListStreamsResult] = {

    invoke(client.listStreamsAsync, request)
  }

  def putRecord(request: com.amazonaws.services.kinesis.model.PutRecordRequest):
      Future[com.amazonaws.services.kinesis.model.PutRecordResult] = {

    invoke(client.putRecordAsync, request)
  }

  def getShardIterator(request: com.amazonaws.services.kinesis.model.GetShardIteratorRequest):
      Future[com.amazonaws.services.kinesis.model.GetShardIteratorResult] = {

    invoke(client.getShardIteratorAsync, request)
  }

  def listTagsForStream(request: com.amazonaws.services.kinesis.model.ListTagsForStreamRequest):
      Future[com.amazonaws.services.kinesis.model.ListTagsForStreamResult] = {

    invoke(client.listTagsForStreamAsync, request)
  }

  def putRecords(request: com.amazonaws.services.kinesis.model.PutRecordsRequest):
      Future[com.amazonaws.services.kinesis.model.PutRecordsResult] = {

    invoke(client.putRecordsAsync, request)
  }

  def deleteStream(request: com.amazonaws.services.kinesis.model.DeleteStreamRequest):
      Future[Unit] = {

    invokeVoid(client.deleteStreamAsync, request)
  }

  def getRecords(request: com.amazonaws.services.kinesis.model.GetRecordsRequest):
      Future[com.amazonaws.services.kinesis.model.GetRecordsResult] = {

    invoke(client.getRecordsAsync, request)
  }

  def splitShard(request: com.amazonaws.services.kinesis.model.SplitShardRequest):
      Future[Unit] = {

    invokeVoid(client.splitShardAsync, request)
  }

  def describeStream(request: com.amazonaws.services.kinesis.model.DescribeStreamRequest):
      Future[com.amazonaws.services.kinesis.model.DescribeStreamResult] = {

    invoke(client.describeStreamAsync, request)
  }

  def shutdown(): Unit = client.shutdown()
  
  private def invoke[Request <: AmazonWebServiceRequest, Result](
      method: (Request, AsyncHandler[Request, Result]) => java.util.concurrent.Future[Result],
      request: Request): Future[Result] = {

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

    val promise = Promise[Unit]
    
    method(request, new AsyncHandler[Request, Void]() {
      override def onSuccess(request: Request, result: Void) = promise.success(())
      override def onError(exception: Exception) = promise.failure(exception)
    })

    promise.future
  }
}
