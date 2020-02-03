package com.autosendemail.emailSingle;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailElasticemailHandle  implements EmailSmtpHandle {
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

        emailSmtpCredentials.setSmtp_host("smtp.elasticemail.com");

        emailSmtpCredentials.setPort("2525");

        emailSmtpCredentials.setSmtp_user("wangshuaiws0716@gmail.com");

        emailSmtpCredentials.setSmtp_password("c01b96b9-fc19-46bc-a151-c87dbd53a9e4");

        com.autosendemail.emailSingle.EmailAwsSmtpHandle emailAwsSmtpHandle = new com.autosendemail.emailSingle.EmailAwsSmtpHandle();

        emailAwsSmtpHandle.sendSmtpEmailResult(emailUserInfo, emailInfo, emailSmtpCredentials);

    }
}