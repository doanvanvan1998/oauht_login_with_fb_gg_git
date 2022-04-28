package com.example.quanlysanpham.Service.Serviceimpl;

import com.example.quanlysanpham.Dto.CartDto;
import com.example.quanlysanpham.Dto.ProductEntityDto;
import com.example.quanlysanpham.Repository.ProductRepository;
import com.example.quanlysanpham.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    ProductRepository  productRepository;

    @Value("${mail.smtp.username}")
    String mailFrom;

    @Override
    public void sendMail(String toAddress, String subject, Object model, String filePath, String content, List<CartDto> lstCart) {
        try{
            List<ProductEntityDto> lstCartDto = new ArrayList<>();
            ProductEntityDto p = null;
            for (CartDto cart : lstCart){
                p = new ProductEntityDto(productRepository.findById(cart.getId()).get());
                p.setSl(cart.getSl());
                lstCartDto.add(p);
            }
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("lstCartDto", lstCartDto);
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setSubject(subject);
            String templateHtml = templateEngine.process("MailTemplate", ctx);
            mimeMessageHelper.setText(templateHtml, true);

            this.javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void resetPassWord(String from, String toAddress, String subject, Object model, String filePath, String content, String tokken) {
//        try{
//            final Context ctx = new Context(LocaleContextHolder.getLocale());
//            ctx.setVariable("token_mail", tokken);
//            ctx.setVariable("content", content);
//            final MimeMessage message = this.javaMailSender.createMimeMessage();
//            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
//            mimeMessageHelper.setTo(toAddress);
//            mimeMessageHelper.setFrom(mailFrom);
//            mimeMessageHelper.setSubject(subject);
//            String templateHtml = templateEngine.process("templeteResetEmail", ctx);
//            mimeMessageHelper.setText(templateHtml, true);
//
//            this.javaMailSender.send(message);
//
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
    }
}
