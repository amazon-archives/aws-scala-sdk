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
        <plugin>
          <groupId>net.alchim31.maven</groupId>
          <artifactId>scala-maven-plugin</artifactId>
          <executions>
            <execution>
              <goals>
                <goal>doc-jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <plugin>
          <artifactId>maven-source-plugin</artifactId>
          <version>2.4</version>
          <executions>
            <execution>
              <goals>
                <goal>jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>

  <modules>
   <#list modules as module>
    <module>aws-scala-sdk-${module.name}</module>
   </#list>
  </modules>

</project>
