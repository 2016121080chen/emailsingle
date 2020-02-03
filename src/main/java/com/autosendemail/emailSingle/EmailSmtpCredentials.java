package com.autosendemail.emailSingle;

public class EmailSmtpCredentials {

    String smtp_host;

    String port;

    String smtp_user;

    String smtp_password;

    public String getSmtp_host() {
        return smtp_host;
    }

    public void setSmtp_host(String smtp_host) {
        this.smtp_host = smtp_host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSmtp_user() {
        return smtp_user;
    }

    public void setSmtp_user(String smtp_user) {
        this.smtp_user = smtp_user;
    }

    public String getSmtp_password() {
        return smtp_password;
    }

    public void setSmtp_password(String smtp_password) {
        this.smtp_password = smtp_password;
    }
}
