package com.valuablecode.spike;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Spike: Knows how to send an email describing an application problem given an unhandled exception.
 */
public class ProblemReporterSpike {

    public void report(Exception unhandled) {
        sendEmailReport(createReport(unhandled));
    }

    private String createReport(Exception unhandled) {
        StringBuffer report = new StringBuffer();

        report.append("<p>");
        report.append(unhandled.getMessage());
        report.append("</p>");

        report.append("<p>");
        report.append(extractStackTrace(unhandled));
        report.append("</p>");

        return report.toString();
    }

    private String extractStackTrace(Exception unhandled) {
        Writer stackTrace = new StringWriter();

        unhandled.printStackTrace(new PrintWriter(stackTrace));

        return stackTrace.toString();
    }

    private void sendEmailReport(String report) {
        try {
            sendEmail("Application Problem Reporter", report);
        } catch (Exception e) {
            // Do nothing. Is there a better choice?
        }
    }

    private void sendEmail(String subject, String body) throws AddressException, MessagingException {
        String host = "smtp.gmail.com";
        String password = System.getProperty("gmail.smtp.password");
        String user = "alistair.mckinnell@gmail.com";

        String fromEmailAddress = "alistair.mckinnell@gmail.com";
        String toEmailAddress = "alistair.mckinnell@gmail.com";

        InternetAddress to = new InternetAddress(toEmailAddress);
        InternetAddress from = new InternetAddress(fromEmailAddress);

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(from);
        message.addRecipient(Message.RecipientType.TO, to);

        message.setSubject(subject);
        message.setContent(body, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(host, user, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

}
