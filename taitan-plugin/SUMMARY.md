# Taitan Flux Maven Plugin - Summary

## Overview
This project implements a Maven plugin that generates Java files from .flux files by simple copying. Since .flux syntax is identical to Java files, this is a straightforward copy operation.

## Project Structure

```
taitan-java/
├── taitan-plugin/
│   ├── pom.xml
│   ├── README.md
│   ├── SUMMARY.md
│   └── src/
│       ├── main/
│       │   └── java/
│       │       └── com/
│       │           └── taitan/
│       │               └── plugin/
│       │                   └── FluxToJavaMojo.java
│       └── test/
│           ├── resources/
│           │   └── flux/
│           │       └── SampleClass.flux
│           └── java/
│               └── com/
│                   └── taitan/
│                       └── plugin/
│                           └── FluxToJavaMojoTest.java
├── example-project/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── example/
│       │   │           └── Main.java
│       │   └── resources/
│       │       └── flux/
│       │           └── ExampleClass.flux
└── README.md
```

## Key Features

1. **Simple File Copying**: The plugin copies .flux files to .java files without any transformation since the syntax is identical.

2. **Automatic Source Directory Registration**: The generated Java files are automatically added to the project's compile source roots.

3. **Configurable Directories**: 
   - Source directory for .flux files (default: `src/main/resources/flux`)
   - Output directory for .java files (default: `target/generated-sources/java`)

4. **Maven Lifecycle Integration**: The plugin is bound to the `generate-sources` phase, so it automatically runs during the build process.

## Java 21 Support

The plugin has been updated to support Java 21 with the following changes:

1. **Updated Maven Dependencies**: All Maven dependencies have been updated to versions compatible with Java 21:
   - Maven version: 3.9.6
   - Maven Plugin API: 3.9.6
   - Maven Core: 3.9.6
   - Maven Plugin Annotations: 3.11.0
   - Maven Plugin Plugin: 3.11.0

2. **Updated Plugin Versions**: 
   - Maven Compiler Plugin: 3.11.0
   - Maven Plugin Plugin: 3.11.0

3. **Updated Testing Dependencies**: 
   - Maven Plugin Testing Harness: 3.3.0 (compatible version)

## How to Use

1. **Install the plugin**:
   ```bash
   cd taitan-plugin
   mvn install
   ```

2. **Configure the plugin in your project**:
   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>com.taitan</groupId>
               <artifactId>taitan-plugin</artifactId>
               <version>1.0</version>
           </plugin>
       </plugins>
   </build>
   ```

3. **Place .flux files** in `src/main/resources/flux`

4. **Run Maven**:
   ```bash
   mvn compile
   ```

The plugin will automatically generate corresponding .java files during the build process.

## Implementation Details

The plugin is implemented as a Maven Mojo (`FluxToJavaMojo`) that:

1. Scans the configured source directory for .flux files
2. Copies each .flux file to the output directory with a .java extension
3. Preserves the directory structure
4. Automatically adds the output directory to the project's compile sources
5. Handles file overwriting to support incremental builds

## Testing

The plugin includes unit tests that verify:
1. The plugin class can be instantiated
2. The file processing logic works correctly

The example project demonstrates the plugin in action, showing how .flux files are converted to .java files during the build process.