package com.autosendemail.emailSingle;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSendinblueHandle implements EmailSmtpHandle {
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

        emailInfo.setEmail_content("dsfdfdsfueuireuri");

        emailInfo.setEmail_from("wangshuaiws0716@gmail.com");

        emailInfo.setEmail_to("wangshuaiws0716@aliyun.com");

        emailInfo.setEmail_reply_to("wangshuaiws0716@aliyun.com");

        emailInfo.setEmail_subject("djsfkdjfsdjf");

        emailSmtpCredentials.setSmtp_host("smtp-relay.sendinblue.com");

        emailSmtpCredentials.setPort("587");

        emailSmtpCredentials.setSmtp_user("wangshuaiws0716@gmail.com");

        emailSmtpCredentials.setSmtp_password("R14PEj93GbdasfHg");

        EmailSendinblueHandle emailSendinblueHandle = new EmailSendinblueHandle();

        emailSendinblueHandle.sendSmtpEmailResult(emailUserInfo, emailInfo, emailSmtpCredentials);

    }
}