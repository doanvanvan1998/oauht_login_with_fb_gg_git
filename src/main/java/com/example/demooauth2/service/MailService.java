package com.example.demooauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public static final String MAIL_FROM = "dang.dev04@gmail.com";
    private static final String HTML_TEMPLATE = "mail";

    public void sendTextMail(String from, String toAddress, String subject, Object model,
                             String filePath, String password, String content){
        try {
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("model", model);

            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toAddress);
            helper.setFrom(from, "Reset Password");
            helper.setSubject(subject);

            String htmlContent = this.templateEngine.process(HTML_TEMPLATE, ctx);
            helper.setText(htmlContent, true);
            this.javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("error send mail" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

}
