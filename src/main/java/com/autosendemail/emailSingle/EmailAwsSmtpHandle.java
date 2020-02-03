package com.autosendemail.emailSingle;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailAwsSmtpHandle implements EmailSmtpHandle {
    @Override
    public String sendSmtpEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo, EmailSmtpCredentials emailSmtpCredentials) {
        String flag = "1";
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", emailSmtpCredentials.port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(emailInfo.getEmail_from(), emailInfo.getEmail_from()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailInfo.getEmail_to()));
            msg.setReplyTo(InternetAddress.parse(emailInfo.getEmail_reply_to(),true));
            msg.setSubject(emailInfo.getEmail_subject());
            msg.setContent(emailInfo.getEmail_content(), "text/html");
        } catch (Exception ex) {
            return flag;
        }

        Transport transport = null;

        try {
            transport = session.getTransport();
            System.out.println("Sending...");
            transport.connect(emailSmtpCredentials.smtp_host, emailSmtpCredentials.getSmtp_user(), emailSmtpCredentials.getSmtp_password());
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            flag = "0";
            return flag;

        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            return flag;
        } finally {
            try {
                transport.close();
            } catch (Exception ex) {
                System.out.println("The email was not sent.");
                return flag;
            }
        }
    }


    public static void main(String args[]) {

        EmailUserInfo emailUserInfo = new EmailUserInfo();

        EmailInfo emailInfo = new EmailInfo();

        EmailSmtpCredentials emailSmtpCredentials = new EmailSmtpCredentials();

        emailInfo.setEmail_content("dsfdfdsf");

        emailInfo.setEmail_from("phyllis@bosun-mould.com");

        emailInfo.setEmail_to("wangshuaiws0716@163.com");

        emailInfo.setEmail_reply_to("wangshuaiws0716@aliyun.com");

        emailInfo.setEmail_subject("djsfkdjfsdjf");

        emailSmtpCredentials.setSmtp_host("email-smtp.us-west-2.amazonaws.com");

        emailSmtpCredentials.setPort("587");

        emailSmtpCredentials.setSmtp_user("AKIAQG2OL4QLX3M7ZBFP");

        emailSmtpCredentials.setSmtp_password("BFhHfdbXpZ82YU63OnMFocp9ByFXer+8aCBCJhEfBpgW");

        EmailAwsSmtpHandle emailAwsSmtpHandle = new EmailAwsSmtpHandle();

        emailAwsSmtpHandle.sendSmtpEmailResult(emailUserInfo, emailInfo, emailSmtpCredentials);


    }
}
