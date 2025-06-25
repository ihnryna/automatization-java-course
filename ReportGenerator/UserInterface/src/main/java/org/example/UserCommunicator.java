package org.example;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserCommunicator {
    public static void main(String[] args) throws IOException, MessagingException {
        System.out.println("Hello and welcome! I am a report generator!");
        System.out.println("Enter your text for report:");
        Scanner scanner = new Scanner(System.in);
        String text;
        List<String> allText = new ArrayList<>();
        do{
            text = scanner.nextLine();
            allText.add(text);
        } while (!text.isEmpty());

        System.out.println("Enter directory for report:");
        String directoryName = scanner.nextLine();
        System.out.println("Enter name for report:");
        String fileName = scanner.nextLine();

        ReportMaker reportMaker = new ReportMaker();
        String path = reportMaker.generatePDF(allText,fileName,directoryName);

        //TODO: some task

        System.out.println("Enter email address to send the report:");
        String email = scanner.nextLine();
        ReportSender reportSender = new ReportSender();
        reportSender.sendReport(email, fileName, path);

    }
}