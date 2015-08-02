<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.amazonaws.scala</groupId>
    <artifactId>parent-pom</artifactId>
    <version>${parentPomVersion}</version>
  </parent>

  <artifactId>aggregator</artifactId>
  <version>${sdkVersion}</version>
  <packaging>pom</packaging>

  <name>AWS SDK for Scala - Aggregator</name>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-java-sdk-bom</artifactId>
        <version>${sdkVersion}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>com.amazonaws.scala</groupId>
          <artifactId>generator-maven-plugin</artifactId>
          <version>${parentPomVersion}</version>
          <executions>
            <execution>
              <phase>generate-sources</phase>
              <goals>
                <goal>generate</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>

  <modules>
   <#list modules as module>
    <module>${module.name}</module>
   </#list>
  </modules>

</project>
