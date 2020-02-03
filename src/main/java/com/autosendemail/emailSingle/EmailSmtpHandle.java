package com.autosendemail.emailSingle;

public interface EmailSmtpHandle {

    String sendSmtpEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo, EmailSmtpCredentials emailSmtpCredentials);
}
