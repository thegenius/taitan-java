package com.taitan.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Mojo to generate Java files from .flux files by simple copying.
 * Since .flux syntax is identical to Java files, this is a straightforward copy operation.
 */
@Mojo(name = "generate-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class FluxToJavaMojo extends AbstractMojo {

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /**
     * Directory containing the .flux files.
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/resources/flux", property = "fluxSourceDirectory", required = true)
    private File fluxSourceDirectory;

    /**
     * Output directory for the generated Java files.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/java", property = "javaOutputDirectory", required = true)
    private File javaOutputDirectory;

    public void execute() throws MojoExecutionException {
        getLog().info("Starting Flux to Java generation...");
        getLog().info("Flux source directory: " + fluxSourceDirectory.getAbsolutePath());
        getLog().info("Java output directory: " + javaOutputDirectory.getAbsolutePath());

        // Check if flux source directory exists
        if (!fluxSourceDirectory.exists()) {
            getLog().warn("Flux source directory does not exist: " + fluxSourceDirectory.getAbsolutePath());
            return;
        }

        // Create output directory if it doesn't exist
        if (!javaOutputDirectory.exists()) {
            javaOutputDirectory.mkdirs();
        }

        try {
            processFluxFiles(fluxSourceDirectory.toPath(), javaOutputDirectory.toPath());
            // Add generated sources to project compile roots
            project.addCompileSourceRoot(javaOutputDirectory.getAbsolutePath());
            getLog().info("Added compile source root: " + javaOutputDirectory.getAbsolutePath());
            getLog().info("Flux to Java generation completed successfully.");
        } catch (IOException e) {
            throw new MojoExecutionException("Error processing .flux files", e);
        }
    }

    /**
     * Process all .flux files in the source directory and copy them as .java files to output directory.
     *
     * @param sourceDir Path to the source directory containing .flux files
     * @param outputDir Path to the output directory for .java files
     * @throws IOException If an I/O error occurs
     */
    private void processFluxFiles(Path sourceDir, Path outputDir) throws IOException {
        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".flux"))
                 .forEach(fluxFile -> {
                     try {
                         processSingleFluxFile(fluxFile, sourceDir, outputDir);
                     } catch (IOException e) {
                         getLog().error("Error processing file: " + fluxFile.toString(), e);
                     }
                 });
        }
    }

    /**
     * Process a single .flux file by copying it as a .java file to the output directory.
     *
     * @param fluxFile   Path to the .flux file
     * @param sourceDir  Source directory root
     * @param outputDir  Output directory root
     * @throws IOException If an I/O error occurs
     */
    private void processSingleFluxFile(Path fluxFile, Path sourceDir, Path outputDir) throws IOException {
        // Calculate relative path from source directory
        Path relativePath = sourceDir.relativize(fluxFile);
        String relativePathStr = relativePath.toString();
        
        // Replace .flux extension with .java
        String javaFileName = relativePathStr.replaceAll("\\.flux$", ".java");
        Path javaFilePath = outputDir.resolve(javaFileName);
        
        // Create parent directories if needed
        Path parentDir = javaFilePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        
        // Copy .flux file content to .java file, overwriting if it exists
        getLog().info("Copying " + fluxFile.toString() + " to " + javaFilePath.toString());
        Files.copy(fluxFile, javaFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
}