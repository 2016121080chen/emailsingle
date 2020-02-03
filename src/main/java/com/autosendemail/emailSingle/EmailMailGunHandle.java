package com.autosendemail.emailSingle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


/**
 * Created by wangshuai on 2018/1/31.
 */
@Service
public class EmailMailGunHandle implements EmailHandle {

    private String MAILGUNURL = "https://api.mailgun.net/v3/";

    private static String SOURCE = "02";

    private String domain = "premiermfgco.com";

    @Override
    public String sendEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo,String apikey)  {
        String flag = "1";
        try {
            HttpResponse<JsonNode> request = Unirest.post(MAILGUNURL + domain + "/messages")
                    .basicAuth("api", apikey)
                    .queryString("from", emailInfo.getEmail_from() + " <" + emailInfo.getEmail_from() + ">")
                    .queryString("to", emailInfo.getEmail_to())
                    .queryString("subject", emailInfo.getEmail_subject())
                    .queryString("html", emailInfo.getEmail_content())
                    .queryString("h:reply-to", emailInfo.getEmail_reply_to())
                    .asJson();
            String respone = request.getBody().toString();
            System.out.println("MailGun 发送结果:" + respone);
            JSONObject jsonObject = JSON.parseObject(respone);
            String message = jsonObject.getString("message").toString();


            if ("Queued. Thank you.".equalsIgnoreCase(message)) {
                flag = "0";
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return flag;
    }

    public String getSource() {
        return SOURCE;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
