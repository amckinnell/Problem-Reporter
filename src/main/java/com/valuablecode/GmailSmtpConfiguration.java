package com.valuablecode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Knows the email report service configuration for a Gmail account.
 *
 * Notes:
 *
 * (1) The configuration is lazily initialised from system properties. The system properties are read one time only;
 * (2) Gmail provides a public SMTP server that can be used for integration testing;
 * (3) To activate your Gmail account either modify this class or specify system properties for your Gmail account.
 */
class GmailSmtpConfiguration implements EmailReportServiceConfiguration {

    private String emailAddressForAdministrator;

    private String host;
    private String password;
    private String port;
    private String user;


    public String getEmailAddressForAdministrator() {
        if (null == emailAddressForAdministrator) {
            emailAddressForAdministrator = System.getProperty("administrator.email.address",
                    "valuablecode.public@gmail.com");
        }

        return emailAddressForAdministrator;
    }

    public String getHost() {
        if (null == host) {
            host = System.getProperty("gmail.smtp.host", "smtp.gmail.com");
        }

        return host;
    }

    public String getPort() {
        if (null == port) {
            port = System.getProperty("gmail.smtp.port", "587");
        }

        return port;
    }

    public String getPassword() {
        if (null == password) {
            password = System.getProperty("gmail.smtp.password");

            if (null == password) {
                throw new RuntimeException("Missing system property: gmail.smtp.password");
            }

        }

        return password;
    }

    public String getUser() {
        if (null == user) {
            user = System.getProperty("gmail.smtp.user", "valuablecode.public@gmail.com");
        }

        return user;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("host", getHost())
            .append("port", getPort())
            .append("user", getUser())
            .append("password", getPassword())
            .append("administrator", getEmailAddressForAdministrator())
            .toString();
    }

}
