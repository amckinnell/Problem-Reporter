package com.valuablecode;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.PrintWriter;

import org.junit.Test;

public class HtmlProblemReportTest {

    final TestException unhandledException = new TestException();

    final ProblemReport sut = new HtmlProblemReport();

    @Test public void
    creates_bold_header() {
        sut.formatReport(unhandledException);
        String formattedReport = sut.getFormattedReport();

        String expectedBoldHeader =
                "<p style=\"font-weight: bold;\">Problem:&nbsp;" +
                unhandledException.getMessage() +
                "</p>";

        assertThat(formattedReport, containsString(expectedBoldHeader));
    }

    @Test public void
    creates_preformatted_stack_trace() {
        sut.formatReport(unhandledException);
        String formattedReport = sut.getFormattedReport();

        String expectedPreformattedStackTrace =
                "<pre><code>\n" +
                unhandledException.getFakeStackTrace() +
                "\n</code></pre>\n";

        assertThat(formattedReport, containsString(expectedPreformattedStackTrace));
    }


    /**
     * Provides a test exception.
     *
     * Note: implements the <a href="http://xunitpatterns.com/Test%20Stub.html">Test Stub</a> variation of
     * <a href="http://xunitpatterns.com/Test%20Double.html">Test Double</a>.
     */
    public static class TestException extends RuntimeException {

        public TestException() {
            super("a_message");
        }

        public void printStackTrace(PrintWriter writer) {
            writer.println(getFakeStackTrace());
        }

        public String getFakeStackTrace() {
            return "fake_stack_trace";
        }

    }

}
