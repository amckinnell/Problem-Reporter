package com.valuablecode;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Knows how to send a report by email to the application administrator using the Java Mail API.
 */
class JavaMailReportService implements EmailReportService {

    public static final String PROBLEM_REPORTER_SUBJECT = "Application Problem Reporter";

    private final EmailReportServiceConfiguration configuration;

    public JavaMailReportService() {
        this(new GmailSmtpConfiguration());
    }

    public JavaMailReportService(EmailReportServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    public void sendReport(ProblemReport report) {
        try {
            sendEmail(PROBLEM_REPORTER_SUBJECT, report.getFormattedReport());
        } catch (Exception e) {
            // Do nothing. Is there a better choice?
        }
    }

    private void sendEmail(String subject, String body) throws AddressException, MessagingException {
        Session session = createSession();
        MimeMessage message = createMessage(session, subject, body);

        sendMessage(session, message);
    }

    private MimeMessage createMessage(Session session, String subject, String body) throws AddressException,
            MessagingException {
        InternetAddress administratorAddress = parseAdministratorEmailAddress();

        MimeMessage result = new MimeMessage(session);

        result.setFrom(administratorAddress);
        result.addRecipient(Message.RecipientType.TO, administratorAddress);
        result.setSubject(subject);
        result.setContent(body, "text/html");

        return result;
    }

    private InternetAddress parseAdministratorEmailAddress() throws AddressException {
        return new InternetAddress(configuration.getEmailAddressForAdministrator(), true);
    }

    private Session createSession() {
        Properties props = new Properties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", configuration.getHost());
        props.put("mail.smtp.user", configuration.getUser());
        props.put("mail.smtp.password", configuration.getPassword());
        props.put("mail.smtp.port", configuration.getPort());
        props.put("mail.smtp.auth", "true");

        return Session.getDefaultInstance(props, null);
    }

    // Note: protected to create a testing seam so that the Transport message sending behaviour is controllable.
    protected void sendMessage(Session session, MimeMessage message) throws MessagingException {
        Transport transport = session.getTransport("smtp");

        transport.connect(configuration.getHost(), configuration.getUser(), configuration.getPassword());
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

}
