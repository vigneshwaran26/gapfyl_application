package com.gapfyl.services.email;

import com.gapfyl.models.email.EmailContent;

import java.util.Map;

/**
 * @author vignesh
 * Created on 16/04/21
 **/

public interface EmailService {

    void sendSimpleMessage(EmailContent emailContent);

    void sendHtmlMessage(EmailContent emailContent);

    void sendHtmlMessageWithTemplate(String templateName, EmailContent emailContent);

    void sendUserOnBoarding(String toEmail, Map<String, Object> emailModel);

    void sendResetPassword(String toEmail, Map<String, Object> emailModel);

    void sendInvitation(String toEmail, Map<String, Object> emailModel);

}
