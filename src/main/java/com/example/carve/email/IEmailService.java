package com.example.carve.email;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface IEmailService {
    String sendSimpleEmail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    String sendMessageUsingFreemarkerTemplate(EmailDetails details)
            throws IOException, TemplateException, MessagingException;
}
