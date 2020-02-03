package com.autosendemail.emailSingle;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.JsonNode;


/**
 * Created by wangshuai on 2018/1/27.
 */
@Service
public class EmailSendGridHandle implements EmailHandle {

    private static String SendGridUrl = "https://api.sendgrid.com/v3/mail/send";

    private static String SOURCE = "01";

    private static String DOMAIN = "@bosun-mould.com";


    public String sendEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo, String apikey)  {

        String flag = "0";
        String authorization = "Bearer " + apikey;
        System.out.println(authorization);
        String body = "{\"personalizations\":[{\"to\":[{\"email\":\""+ emailInfo.getEmail_to()
                + "\",\"name\":\"" + emailInfo.getEmail_to() + "\"}],"
                + "\"subject\":\"" + emailInfo.getEmail_subject()
                +"\"}],\"from\":{\"email\":\"" + emailInfo.getEmail_from()
                + "\",\"name\":\"" + emailInfo.getEmail_from() +"\"},"
                + "\"reply_to\":{\"email\":\""+ emailInfo.getEmail_reply_to() +"\",\"name\":\" "+ emailInfo.getEmail_reply_to()+"\"},"
                + "\"content\":[{\"type\":\"text/html\",\"value\":\""+ emailInfo.getEmail_content() +"\"}]}";
        try {
            HttpResponse<JsonNode> response = Unirest.post(SendGridUrl)
                    .header("authorization",authorization)
                    .header("content-type","application/json")
                    .body(body)
                    .asJson();

            System.out.println(body);
            String respone = response.getBody().toString();
            System.out.println(respone);
        if(respone.indexOf("error")>=0){
            flag = "1";
        }
        }catch (Exception e){
            flag = "1";
            return flag;
        }
        return flag;
    }


}
