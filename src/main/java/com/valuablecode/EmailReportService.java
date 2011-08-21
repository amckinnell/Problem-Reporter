package com.valuablecode;

/**
 * Knows how to send a report by email to the application administrator.
 */
public interface EmailReportService {

    void sendReport(ProblemReport report);

}
