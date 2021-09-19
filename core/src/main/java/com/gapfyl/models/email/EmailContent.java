package com.gapfyl.models.email;

import com.gapfyl.constants.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author vignesh
 * Created on 16/04/21
 **/

@Getter
@Setter
public class EmailContent {

    private String from = Email.FROM_EMAIL;

    private String personal = Email.PERSONAL;

    private String replyTo = Email.REPLY_TO_EMAIL;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private String subject;

    private String text;

    private Map<String, Object> model;

}