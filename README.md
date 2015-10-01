# aws-scala-sdk

[![Join the chat at https://gitter.im/awslabs/aws-scala-sdk](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/awslabs/aws-scala-sdk?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

It's like the [AWS SDK for Java](https://github.com/aws/aws-java-sdk), but more Scala-y.

## Building

Have a look at `config.yaml`. Pick your favorite version of the AWS SDK for Java.
Then run `build.sh`. You'll need [Apache Maven](https://maven.apache.org) on your path.

## Using

```scala
import com.amazonaws.services.kinesis.model._
import com.amazonaws.services.kinesis.scala._

val client = new AmazonKinesisClient("us-west-2")
client.listStreams(new ListStreamsRequest()).
  map((r: ListStreamsResult) => r.getStreams())
```
## Contributing

Feature and pull requests welcome!
