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
package com.amazonaws.services.dynamodbv2.scala

import scala.concurrent.Future
import scala.concurrent.Promise

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.RequestClientOptions
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.regions.RegionUtils
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient

class AmazonDynamoDBClient(
  private val client: AmazonDynamoDBAsync,
  private val region: Region) {

  client.setRegion(region)

  def this(region: Region) = this(new AmazonDynamoDBAsyncClient(), region)

  def this(region: String) = this(RegionUtils.getRegion(region))

  def this(region: Regions) = this(Region.getRegion(region))

  def scan(request: com.amazonaws.services.dynamodbv2.model.ScanRequest):
      Future[com.amazonaws.services.dynamodbv2.model.ScanResult] = {

    invoke(client.scanAsync, request)
  }

  def updateTable(request: com.amazonaws.services.dynamodbv2.model.UpdateTableRequest):
      Future[com.amazonaws.services.dynamodbv2.model.UpdateTableResult] = {

    invoke(client.updateTableAsync, request)
  }

  def deleteTable(request: com.amazonaws.services.dynamodbv2.model.DeleteTableRequest):
      Future[com.amazonaws.services.dynamodbv2.model.DeleteTableResult] = {

    invoke(client.deleteTableAsync, request)
  }

  def batchWriteItem(request: com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult] = {

    invoke(client.batchWriteItemAsync, request)
  }

  def describeTable(request: com.amazonaws.services.dynamodbv2.model.DescribeTableRequest):
      Future[com.amazonaws.services.dynamodbv2.model.DescribeTableResult] = {

    invoke(client.describeTableAsync, request)
  }

  def getItem(request: com.amazonaws.services.dynamodbv2.model.GetItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.GetItemResult] = {

    invoke(client.getItemAsync, request)
  }

  def deleteItem(request: com.amazonaws.services.dynamodbv2.model.DeleteItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.DeleteItemResult] = {

    invoke(client.deleteItemAsync, request)
  }

  def createTable(request: com.amazonaws.services.dynamodbv2.model.CreateTableRequest):
      Future[com.amazonaws.services.dynamodbv2.model.CreateTableResult] = {

    invoke(client.createTableAsync, request)
  }

  def query(request: com.amazonaws.services.dynamodbv2.model.QueryRequest):
      Future[com.amazonaws.services.dynamodbv2.model.QueryResult] = {

    invoke(client.queryAsync, request)
  }

  def putItem(request: com.amazonaws.services.dynamodbv2.model.PutItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.PutItemResult] = {

    invoke(client.putItemAsync, request)
  }

  def listTables(request: com.amazonaws.services.dynamodbv2.model.ListTablesRequest):
      Future[com.amazonaws.services.dynamodbv2.model.ListTablesResult] = {

    invoke(client.listTablesAsync, request)
  }

  def updateItem(request: com.amazonaws.services.dynamodbv2.model.UpdateItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.UpdateItemResult] = {

    invoke(client.updateItemAsync, request)
  }

  def batchGetItem(request: com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest):
      Future[com.amazonaws.services.dynamodbv2.model.BatchGetItemResult] = {

    invoke(client.batchGetItemAsync, request)
  }

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
