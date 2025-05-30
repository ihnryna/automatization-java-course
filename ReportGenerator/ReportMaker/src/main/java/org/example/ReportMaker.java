package org.example;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportMaker {
    public String generatePDF(List<String> text, String name, String directoryPath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDMMType1Font.TIMES_ROMAN, 14);
        contentStream.setLeading(16.0f);
        contentStream.newLineAtOffset(25, page.getTrimBox().getHeight()-25);

        for (String line : text) {
            contentStream.showText(line);
            contentStream.newLine();
        }

        contentStream.newLine();
        contentStream.showText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        contentStream.endText();
        contentStream.close();

        document.save(directoryPath+"\\"+name+".pdf");
        document.close();
        System.out.println("PDF report " +directoryPath+"\\"+name+".pdf"+" created");
        return directoryPath+"\\"+name+".pdf";
    }

}