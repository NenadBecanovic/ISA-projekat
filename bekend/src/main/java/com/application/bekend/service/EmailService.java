package com.application.bekend.service;

import com.application.bekend.DTO.EmailDTO;
import com.application.bekend.model.MyUser;
import com.application.bekend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final VerificationTokenService verificationTokenService;
    private final TemplateEngine templateEngine;        // za generisanje izgleda mejla (u html-u) -> template/verification.html
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(VerificationTokenService verificationTokenService, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendHTMLMail(MyUser user) throws MessagingException{
        VerificationToken verificationToken = this.verificationTokenService.findByUser(user);
        if(verificationToken != null){
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "Verify your email address ");

            context.setVariable("link", "http://localhost:4200/email-activation?token="+token+"&userId="+user.getId());

            String body = templateEngine.process("verification", context);

            //Send verification email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Email verification");
            helper.setText(body, true);
            javaMailSender.send(message);

        }
    }
    
    public void sendAnswerEmail(EmailDTO emailDTO) throws MessagingException{
            Context context = new Context();
            context.setVariable("title", emailDTO.getTitle());

            context.setVariable("content", emailDTO.getContent());

            String body = templateEngine.process("adminAnswer", context);

            //Send verification email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailDTO.getUserEmail());
            helper.setSubject(emailDTO.getTitle());
            helper.setText(body, true);
            javaMailSender.send(message);
    }
}
