# refaster-junit

A collection of [Refaster](https://errorprone.info/docs/refaster) templates
to migrate to JUnit 5.

These templates augment the IDEA-built-in [refactorings][idea]
which do not work if a test class contains [`ExpectedException`](https://junit.org/junit4/javadoc/4.12/org/junit/rules/ExpectedException.html)
rule (as of v2018.2).

Currently this project includes a set of rules to migrate from `ExpectedException` to 
[`assertThrows`][assert-throws].

## How to use
### Prerequisites
1. [JDK 9+](http://jdk.java.net/10/).
2. [Maven 3.5+](https://maven.apache.org/download.cgi).

### Build a rule
Clone this repository and run `build-rule.sh` script from the root directory
to compile a rule. For example, to build the rule processing `ExpectedException`s:
```bash
./build-rule.sh src/main/java/dt/refaster/junit/ExpectedExceptionRule.java expected-exception.refaster
```

### Patch the target project
Use the output Refaster rule file to produce a patch for your project. 
You will need to enable [Error Prone](https://errorprone.info/docs/installation)
compiler and configure it to use your Refaster rule:
```xml
<plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.0</version>
        <configuration>
          <compilerId>javac-with-errorprone</compilerId>
          <forceJavacCompilerUse>true</forceJavacCompilerUse>
          <source>8</source>
          <target>8</target>
          <compilerArgs>
            <!-- Enter the full path to the compiled Refaster rule below: -->
            <arg>-XepPatchChecks:refaster:/full/path/to/output-rule-name.refaster</arg>
            <arg>-XepPatchLocation:${project.basedir}</arg>
          </compilerArgs>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>org.codehaus.plexus</groupId>
            <artifactId>plexus-compiler-javac-errorprone</artifactId>
            <version>2.8.3</version>
          </dependency>
          <dependency>
            <groupId>com.google.errorprone</groupId>
            <artifactId>error_prone_core</artifactId>
            <version>2.3.1</version>
          </dependency>
        </dependencies>
      </plugin>
</plugins>
```

A complete Maven example can be found in 
the error prone [repository](https://github.com/google/error-prone/blob/v2.3.1/examples/maven/refaster-based-cleanup/pom.xml).
Please note that it inherits most of 'maven-compiler-plugin' configuration from its parent. 

### Migrate other APIs

Remove the `ExpectedException` rules from your test classes and then 
run the IDEA built-in [refactoring][idea] to migrate JUnit 4 
annotations and methods to JUnit 5 counterparts.

[idea]: https://blog.jetbrains.com/idea/2017/11/intellij-idea-2017-3-junit-support/
[assert-throws]: https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/Assertions.html#assertThrows(java.lang.Class,org.junit.jupiter.api.function.Executable)
