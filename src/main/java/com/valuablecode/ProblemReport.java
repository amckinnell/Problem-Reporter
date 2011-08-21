package com.valuablecode;

/**
 * Knows how to format a problem report given an unhandled exception.
 */
public interface ProblemReport {

    void formatReport(Exception unhandled);
    String getFormattedReport();

}
