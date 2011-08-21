package com.valuablecode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Knows how to format a problem report as HTML given an unhandled exception.
 */
class HtmlProblemReport implements ProblemReport {

    private String formattedReport = null;

    public void formatReport(Exception unhandled) {
        StringBuffer report = new StringBuffer();

        createHeader(report, unhandled);
        createStackTrace(report, unhandled);

        formattedReport = report.toString();
    }

    private void createHeader(StringBuffer report, Exception unhandled) {
        report.append("<p style=\"font-weight: bold;\">");
        report.append("Problem:&nbsp;");
        report.append(unhandled.getMessage());
        report.append("</p>\n");
    }

    private void createStackTrace(StringBuffer report, Exception unhandled) {
        report.append("<pre>\n");
        report.append(extractStackTrace(unhandled));
        report.append("</pre>\n");
    }

    private String extractStackTrace(Exception unhandled) {
        Writer stackTrace = new StringWriter();

        unhandled.printStackTrace(new PrintWriter(stackTrace));

        return stackTrace.toString();
    }

    public String getFormattedReport() {
        return formattedReport;
    }

}
