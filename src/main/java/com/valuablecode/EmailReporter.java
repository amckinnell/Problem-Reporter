package com.valuablecode;

/**
 * Knows how to email a problem report.
 */
public class EmailReporter implements ProblemReporter {

    private final EmailReportService emailReportService;
    private final ProblemReport report;

    public EmailReporter() {
        this(new JavaMailReportService(), new HtmlProblemReport());
    }

    public EmailReporter(EmailReportService emailReportService, ProblemReport report) {
        this.emailReportService = emailReportService;
        this.report = report;
    }

    public void report(Exception unhandled) {
        report.formatReport(unhandled);

        emailReportService.sendReport(report);
    }

}
