# aws-scala-sdk

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
