package com.valuablecode;

/**
 * Knows the email report service configuration.
 */
public interface EmailReportServiceConfiguration {

    String getEmailAddressForAdministrator();
    String getHost();
    String getPort();
    String getPassword();
    String getUser();

}
