package edu.mx.unsis.unsiSmile.service.mails;

import edu.mx.unsis.unsiSmile.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendPasswordRecoveryEmail(@NonNull String userName, @NonNull String email, @NonNull String token, String subject) {
        try {
            String recoveryLink = urlFront + "password-recovery?token=" + token;

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("user", userName);
            context.setVariable("recoveryLink", recoveryLink);
            context.setVariable("email", email);

            String htmlText = templateEngine.process("email-template", context);
            helper.setFrom(mailFrom);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (MessagingException m) {
            throw new AppException("Error al procesar el mensaje de correo", HttpStatus.INTERNAL_SERVER_ERROR, m);
        } catch (Exception ex) {
            throw new AppException("Failed to send password recovery email", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
