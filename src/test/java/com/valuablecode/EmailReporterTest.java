package com.valuablecode;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailReporterTest {

    @Test public void
    formats_and_emails_problem_report() {
        TestEmailReportService emailReportService = new TestEmailReportService();
        TestProblemReport report = new TestProblemReport();

        ProblemReporter sut = new EmailReporter(emailReportService, report);

        sut.report(new RuntimeException());

        assertTrue("Failed to call the formatReport() method", report.isFormatReportCalled());
        assertTrue("Failed to call the sendReport() method", emailReportService.isSendReportCalled());
    }

    /**
     * Provides a test email report service.
     *
     * Note: implements the <a href="http://xunitpatterns.com/Test%20Spy.html">Test Spy</a> variation of
     * <a href="http://xunitpatterns.com/Test%20Double.html">Test Double</a>.
     */
    public static class TestEmailReportService implements EmailReportService {

        private boolean sendReportCalled;

        public boolean isSendReportCalled() {
            return sendReportCalled;
        }

        @Override
        public void sendReport(ProblemReport report) {
            this.sendReportCalled = true;
        }

    }

    /**
     * Provides a test problem report.
     *
     * Note: implements the <a href="http://xunitpatterns.com/Test%20Spy.html">Test Spy</a> variation of
     * <a href="http://xunitpatterns.com/Test%20Double.html">Test Double</a>.
     */
    public static class TestProblemReport implements ProblemReport {

        private boolean formatReportCalled;

        public boolean isFormatReportCalled() {
            return formatReportCalled;
        }

        @Override
        public void formatReport(Exception unhandled) {
            this.formatReportCalled  = true;
        }

        @Override
        public String getFormattedReport() {
            return "dont_care";
        }

    }

}
