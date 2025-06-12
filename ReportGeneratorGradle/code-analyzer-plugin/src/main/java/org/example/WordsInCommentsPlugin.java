package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.gradle.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA;

public class WordsInCommentsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.task("wordsInProjectReport").doLast(task -> {
            try {

                WordAnalyzer analyzer = new WordAnalyzer();

                List<String> words = new ArrayList<>();
                words.add("Words used in the comments:");
                words.addAll(analyzer.getAllWords(project.getProjectDir()));

                generatePDF(words, "Words of "+ project.getProjectDir().getName(), "D:\\CodeAnalyzer");

            } catch (Exception e) {
                throw new GradleException("Failed to generate words report", e);
            }
        });
    }

    public String generatePDF(List<String> text, String name, String directoryPath) throws IOException {
        Files.createDirectories(Paths.get(directoryPath));

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(HELVETICA), 12);
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