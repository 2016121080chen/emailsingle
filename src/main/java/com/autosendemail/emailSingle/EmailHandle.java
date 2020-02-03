package com.autosendemail.emailSingle;

import org.springframework.stereotype.Component;

/**
 * Created by wangshuai on 2017/9/7.
 */
@Component
public interface EmailHandle {

    String sendEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo, String apikey);


}
