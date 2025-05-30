package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ReportSender {
    Session newSession;
    MimeMessage message;


    public void sendReport(String receiverEmail, String reportName, String filePath) throws MessagingException, IOException {

        this.setupProperties();

        String emailBody = "Sending you a report";

        message = new MimeMessage(newSession);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
        message.setSubject("Report " + reportName);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "text/html");

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File(filePath));

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(attachmentBodyPart);
        message.setContent(multipart);

        this.sendEmail();

    }

    private void sendEmail() throws MessagingException {
        String fromEmail = "irihskaa@gmail.com";
        String password = "iadp iehv ugpc czwy";
        String host = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(host, fromEmail, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("Email sent!");
    }


    private void setupProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        newSession = Session.getDefaultInstance(props, null);

    }

}