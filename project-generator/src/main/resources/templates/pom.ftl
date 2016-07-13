<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.amazonaws.scala</groupId>
    <artifactId>aggregator</artifactId>
    <version>${sdkVersion}</version>
  </parent>

  <artifactId>aws-scala-sdk-${name}</artifactId>
  <name>AWS SDK for Scala - ${serviceName}</name>

  <dependencies>
    <dependency>
      <groupId>org.scala-lang</groupId>
      <artifactId>scala-library</artifactId>
    </dependency>

    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-${name}</artifactId>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>com.amazonaws.scala</groupId>
        <artifactId>generator-maven-plugin</artifactId>
        <configuration>
          <pkg>${package}</pkg>
          <classPrefix>${classPrefix}</classPrefix>
          <shutdownSupported>${shutdownSupported}</shutdownSupported>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-${name}</artifactId>
            <version>${sdkVersion}</version>
          </dependency>
        </dependencies>
      </plugin>

      <plugin>
        <groupId>net.alchim31.maven</groupId>
        <artifactId>scala-maven-plugin</artifactId>
      </plugin>

      <plugin>
        <artifactId>maven-source-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
