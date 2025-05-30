package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Finding TODO
 */
@Mojo(name = "todo-scan", defaultPhase = LifecyclePhase.NONE)
public class TodoScannerMojo extends AbstractMojo {

    List<String> todoText = new ArrayList<String>();

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File rootDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        if (!rootDirectory.getName().equals("clean-code-plugin")) {
            scanDirectory(rootDirectory);

            try {
                generatePDF(todoText, "Tasks of " + rootDirectory.getName() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH mm ss")), "D:\\TODOs");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void scanDirectory(File directory) throws MojoExecutionException {
        if (directory.exists() && directory.isDirectory() && !directory.getName().equals("clean-code-plugin")) {

            File[] files = directory.listFiles();
            if (files == null) return;

            boolean firstFileWithTask = true;


            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file);
                } else if (file.getName().endsWith(".java")) {
                    try {
                        boolean foundTask = scanFile(file);
                        if (foundTask && firstFileWithTask) {
                            firstFileWithTask = false;
                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean scanFile(File file) throws MojoExecutionException, FileNotFoundException {
        Scanner scanner = new Scanner(file);
        boolean foundTask = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("//TODO")) {
                if (!foundTask) {
                    foundTask = true;
                    todoText.add("DIRECTORY: " + file.getParentFile().getPath());
                    todoText.add(file.getName());
                    todoText.add("TODOs:");
                }
                todoText.add("  - " + line.trim().substring(7));
            }
        }
        if (foundTask) {
            todoText.add("");
            todoText.add("");
        }
        scanner.close();
        return foundTask;
    }

    public String generatePDF(List<String> text, String name, String directoryPath) throws IOException {
        Files.createDirectories(Paths.get(directoryPath));

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDMMType1Font.TIMES_ROMAN, 12);
        contentStream.setLeading(16.0f);
        contentStream.newLineAtOffset(25, page.getTrimBox().getHeight() - 25);

        for (String line : text) {
            contentStream.showText(line);
            contentStream.newLine();
        }

        contentStream.newLine();
        contentStream.showText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        contentStream.endText();
        contentStream.close();

        document.save(directoryPath + "\\" + name + ".pdf");
        document.close();
        System.out.println("File " + directoryPath + "\\" + name + ".pdf" + " with tasks was created");
        return directoryPath + "\\" + name + ".pdf";
    }
}