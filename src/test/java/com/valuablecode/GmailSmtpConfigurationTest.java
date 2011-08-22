package com.valuablecode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.junit.Test;

public class GmailSmtpConfigurationTest {

    final GmailSmtpConfiguration sut = new GmailSmtpConfiguration();

    @Test public void
    throws_runtime_exception_when_system_property_for_smtp_user_is_missing() {
        try {
            System.clearProperty("gmail.smtp.password");

            sut.getPassword();
            fail("Expected a RuntimeException when the Gmail SMTP password is missing");
        } catch (RuntimeException expected) {
            assertThat(expected.getMessage(), both(containsString("Missing"))
                    .and(containsString("gmail.smtp.password")));
        }

    }

    @Test public void
    lazily_initialises_email_address_for_administrator() {
        System.clearProperty("administrator.email.address");

        assertThat(sut.getEmailAddressForAdministrator(),equalTo("valuablecode.public@gmail.com"));

        System.setProperty("administrator.email.address", "other_value");

        assertThat(sut.getEmailAddressForAdministrator(),equalTo("valuablecode.public@gmail.com"));
    }

    @Test public void
    lazily_initialises_host() {
        System.clearProperty("gmail.smtp.host");

        assertThat(sut.getHost(),equalTo("smtp.gmail.com"));

        System.setProperty("gmail.smtp.host", "other_value");

        assertThat(sut.getHost(),equalTo("smtp.gmail.com"));
    }

    @Test public void
    lazily_initialises_password() {
        System.setProperty("gmail.smtp.password", "password");

        assertThat(sut.getPassword(),equalTo("password"));

        System.setProperty("gmail.smtp.password", "other_value");

        assertThat(sut.getPassword(),equalTo("password"));
    }

    @Test public void
    lazily_initialises_port() {
        System.clearProperty("gmail.smtp.port");

        assertThat(sut.getPort(),equalTo("587"));

        System.setProperty("gmail.smtp.port", "other_value");

        assertThat(sut.getPort(),equalTo("587"));
    }

    @Test public void
    lazily_initialises_user() {
        System.clearProperty("gmail.smtp.user");

        assertThat(sut.getUser(),equalTo("valuablecode.public@gmail.com"));

        System.setProperty("gmail.smtp.user", "other_value");

        assertThat(sut.getUser(),equalTo("valuablecode.public@gmail.com"));
    }

}
