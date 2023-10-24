package com.example.carve.email;

import com.example.carve.order.repository.OrderRepository;
import com.example.carve.user.repository.UserRepository;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleEmail(EmailDetails details) {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            System.out.println("-------Sender: " + sender);
            String userEmail = userRepository.findByUsername(details.getUsername()).getEmail();
            System.out.println("-------Email: " + userEmail);
            mailMessage.setTo(userEmail);
            mailMessage.setText(details.getMsgBody());

            mailMessage.setSubject(details.getSubject());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Success";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            String userEmail = userRepository.findByUsername(details.getUsername()).getEmail();
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Success";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error";
        }
    }

    @Override
    public String sendMessageUsingFreemarkerTemplate(EmailDetails details) {
        try {
            String userEmail = userRepository.findByUsername(details.getUsername()).getEmail();
            String subject = details.getSubject();
            String invoice = orderRepository.findById(details.getOrderId()).get().getInvoice();
            System.out.println("________Invoice: " + invoice);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("invoice", invoice);
            templateModel.put("username", details.getUsername());
            Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("template-freemarker.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

            sendHtmlMessage(userEmail, subject, htmlBody);
            return "Success";
        } catch (Exception e) {
            return "Error";
        }

    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

}
