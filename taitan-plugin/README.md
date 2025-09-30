# Taitan Flux Maven Plugin

A Maven plugin to generate Java files from .flux files by simple copying. Since .flux syntax is identical to Java files, this is a straightforward copy operation.

## Usage

Add the plugin to your `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.taitan</groupId>
            <artifactId>taitan-flux-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate-java</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

By default, the plugin will:
- Look for .flux files in `src/main/resources/flux`
- Generate .java files in `target/generated-sources/java`

## Configuration

You can customize the source and output directories:

```xml
<plugin>
    <groupId>com.taitan</groupId>
    <artifactId>taitan-flux-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <fluxSourceDirectory>src/main/flux</fluxSourceDirectory>
        <javaOutputDirectory>target/generated-sources/flux</javaOutputDirectory>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate-java</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Running the Plugin

Execute the plugin directly:
```bash
mvn com.taitan:taitan-flux-plugin:generate-java
```

Or as part of the build lifecycle:
```bash
mvn generate-sources
```