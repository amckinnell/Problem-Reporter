package com.valuablecode.spike;


/**
 * Drive a ProblemReporter to report an unhandled RuntimeException.
 */
public class ProblemReportSpikeDriver {

    public static void main(String[] args) {
        ProblemReporterSpike problemReporter = new ProblemReporterSpike();

        Exception unhandled = new RuntimeException("error_message");

        problemReporter.report(unhandled);
    }

}
