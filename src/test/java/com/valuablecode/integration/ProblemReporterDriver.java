package com.valuablecode.integration;

import com.valuablecode.EmailReporter;
import com.valuablecode.ProblemReporter;

/**
 * Drive a ProblemReporter to report an unhandled RuntimeException.
 */
public class ProblemReporterDriver {

    public static void main(String[] args) {
        ProblemReporter problemReporter = new EmailReporter();

        Exception unhandled = new RuntimeException("running the ProblemReporterDriver");

        problemReporter.report(unhandled);
    }

}
