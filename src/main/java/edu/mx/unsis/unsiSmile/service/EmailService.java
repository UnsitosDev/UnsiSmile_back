package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.EmailRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendEmail(EmailRequest request) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailFrom);
            helper.setTo(request.getTo().toArray(new String[0]));

            if (request.getCc() != null && !request.getCc().isEmpty()) {
                helper.setCc(request.getCc().toArray(new String[0]));
            }

            helper.setSubject(request.getSubject());

            if (request.getHtmlBody() != null) {
                helper.setText(request.getHtmlBody(), true);
            } else {
                helper.setText(request.getTextBody(), false);
            }

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new AppException(ResponseMessages.EMAIL_SEND_ERROR, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}