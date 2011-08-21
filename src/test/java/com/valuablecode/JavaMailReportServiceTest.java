package com.valuablecode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class JavaMailReportServiceTest {

    private MimeMessage messageToSend;

    @Test public void
    creates_message_based_on_configuration() throws Exception {
        TestConfiguration configuration = new TestConfiguration() ;
        EmailReportService sut = createJavaMailReportService(configuration);

        sut.sendReport(new TestReport());

        assertThat(content(), equalTo(TestReport.FORMATTED_REPORT));
        assertThat(subject(), equalTo(JavaMailReportService.PROBLEM_REPORTER_SUBJECT));
        assertThat(from(), equalTo(configuration.getEmailAddressForAdministrator()));
        assertThat(to(), equalTo(configuration.getEmailAddressForAdministrator()));
    }

    private String content() throws Exception {
        return (String) messageToSend.getContent();
    }

    private String subject() throws Exception {
        return messageToSend.getSubject();
    }

    private String from() throws Exception {
        return getFirstHeader("From");
    }

    private String to() throws Exception {
        return getFirstHeader("To");
    }

    private String getFirstHeader(String headerName) throws Exception {
        return messageToSend.getHeader(headerName, null);
    }

    /**
     * Create a JavaMailReportService instance that records the message to send without actually
     * sending an email.
     */
    private EmailReportService createJavaMailReportService(EmailReportServiceConfiguration configuration) {
        return new JavaMailReportService(configuration) {

            @Override
            protected void sendMessage(Session session, MimeMessage message) throws MessagingException {
                messageToSend = message;
            }

        };
    }

    /**
     * Provides a test email report service configuration.
     *
     * Note: implements the <a href="http://xunitpatterns.com/Test%20Stub.html">Test Stub</a> variation of
     * <a href="http://xunitpatterns.com/Test%20Double.html">Test Double</a>.
     */
    public static class TestConfiguration implements EmailReportServiceConfiguration {

        @Override
        public String getEmailAddressForAdministrator() {
            return "admin@foo.com";
        }

        @Override
        public String getHost() {
            return "host";
        }

        @Override
        public String getPort() {
            return "587";
        }

        @Override
        public String getPassword() {
            return "password";
        }

        @Override
        public String getUser() {
            return "user";
        }

    }

    /**
     * Provides a test formatted report.
     *
     * Note: implements the <a href="http://xunitpatterns.com/Test%20Stub.html">Test Stub</a> variation of
     * <a href="http://xunitpatterns.com/Test%20Double.html">Test Double</a>.
     */
    public static class TestReport implements ProblemReport {

        public static final String FORMATTED_REPORT = "formatted_report";

        @Override
        public void formatReport(Exception unhandled) {
            // Do nothing.
        }

        @Override
        public String getFormattedReport() {
            return FORMATTED_REPORT;
        }

    }

}
