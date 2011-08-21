package com.valuablecode;

/**
 * Knows how to report an unhandled exception.
 */
public interface ProblemReporter {

    void report(Exception unhandled);

}
