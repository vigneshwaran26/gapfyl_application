package com.gapfyl.services.email;

import com.gapfyl.constants.Email;
import com.gapfyl.constants.EmailSubject;
import com.gapfyl.constants.EmailTemplate;
import com.gapfyl.models.email.EmailContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author vignesh
 * Created on 16/04/21
 **/

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendSimpleMessage(EmailContent emailContent) {
        log.info("sending email with simple text message");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailContent.getFrom());
        simpleMailMessage.setReplyTo(emailContent.getReplyTo());
        simpleMailMessage.setTo(emailContent.getTo());
        simpleMailMessage.setSubject(emailContent.getSubject());
        simpleMailMessage.setText(emailContent.getText());
        javaMailSender.send(simpleMailMessage);
        log.info("sent email with simple text message successfully");
    }

    @Override
    public void sendHtmlMessage(EmailContent emailContent) {
        try {
            log.info("sending email with html message");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(emailContent.getFrom(), emailContent.getPersonal());
            mimeMessageHelper.setReplyTo(emailContent.getReplyTo());
            mimeMessageHelper.setTo(emailContent.getTo());
            mimeMessageHelper.setSubject(emailContent.getSubject());
            mimeMessageHelper.setText(emailContent.getText(), true);
            javaMailSender.send(mimeMessage);
            log.info("sent email with html message successfully");
        } catch (MessagingException e) {
            log.error("error while sending mail with html message {}", e.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("error while sending mail with html message {}", e.toString());
        }
    }

    @Override
    public void sendHtmlMessageWithTemplate(String templateName, EmailContent emailContent) {
        log.debug("processing html template {} with email model {}", templateName, emailContent.getModel());
        Context context = new Context();
        context.setVariables(emailContent.getModel());
        String htmlMessage = springTemplateEngine.process(templateName, context);
        emailContent.setText(htmlMessage);
        log.debug("processed html template & email content");
        sendHtmlMessage(emailContent);
    }

    private void sendEmail(String templateName, String toEmail, String subject, Map<String, Object> emailModel) {
        EmailContent emailContent = new EmailContent();
        emailContent.setTo(new String[] { toEmail });
        emailContent.setSubject(subject);
        emailContent.setModel(emailModel);
        emailContent.setFrom(Email.FROM_EMAIL);
        emailContent.setReplyTo(Email.REPLY_TO_EMAIL);
        emailContent.setPersonal(Email.PERSONAL);
        sendHtmlMessageWithTemplate(templateName, emailContent);
    }

    @Override
    @Async("gapFylTaskExecutor")
    public void sendUserOnBoarding(String toEmail, Map<String, Object> emailModel) {
        sendEmail(EmailTemplate.USER_ON_BOARDING, toEmail, EmailSubject.USER_ON_BOARDING, emailModel);
    }

    @Override
    @Async("gapFylTaskExecutor")
    public void sendResetPassword(String toEmail, Map<String, Object> emailModel) {
        sendEmail(EmailTemplate.RESET_PASSWORD, toEmail, EmailSubject.RESET_PASSWORD, emailModel);
    }

    @Override
    public void sendInvitation(String toEmail, Map<String, Object> emailModel) {

    }


}
