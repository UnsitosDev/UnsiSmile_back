package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.OtpValidationRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.OtpValidationResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.OtpTokenModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.IOtpTokenRepository;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.EmailRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.repository.IPersonRepository;
import edu.mx.unsis.unsiSmile.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtpTokenService {

    private final IOtpTokenRepository otpTokenRepository;
    private final EmailService emailService;
    private final IPersonRepository personRepository;

    @Transactional
    public void generateAndSendOtp(String email, String subject, String purpose, String textBody, String footer) {
        try {
            PersonModel person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException(ResponseMessages.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));

            otpTokenRepository.findByEmail(email).ifPresent(otpTokenRepository::delete);

            String code = generateSecureOtp();

            OtpTokenModel otp = OtpTokenModel.builder()
                    .code(code)
                    .purpose(purpose)
                    .expiresAt(LocalDateTime.now().plusMinutes(10))
                    .email(email)
                    .build();
            otp.generateId();

            otpTokenRepository.save(otp);
            String htmlBody = emailService.buildOtpHtml(person.getFirstName(), textBody, code, footer);

            EmailRequest request = buildEmailRequest(email, subject, null, htmlBody, code);

            emailService.sendEmail(request);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.EMAIL_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public OtpValidationResponse validateCode(OtpValidationRequest request) {
        try {
            OtpTokenModel token = otpTokenRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AppException(ResponseMessages.OTP_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!token.getCode().equals(request.getOtp())) {
                token.setAttempts((byte) (token.getAttempts() + 1));
                otpTokenRepository.save(token);
                throw new AppException(ResponseMessages.INVALID_OTP_CODE, HttpStatus.UNAUTHORIZED);
            }

            if (token.isUsed()) {
                throw new AppException(ResponseMessages.OTP_ALREADY_USED, HttpStatus.UNAUTHORIZED);
            }

            if (token.getAttempts() >= 3) {
                throw new AppException(ResponseMessages.OTP_TOO_MANY_ATTEMPTS, HttpStatus.UNAUTHORIZED);
            }

            if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new AppException(ResponseMessages.OTP_CODE_EXPIRED, HttpStatus.UNAUTHORIZED);
            }

            token.setUsed(true);
            token.setValidatedAt(LocalDateTime.now());
            otpTokenRepository.save(token);

            return OtpValidationResponse.builder()
                    .valid(true)
                    .message(ResponseMessages.OTP_CODE_VALID)
                    .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.OTP_CODE_VALIDATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateSecureOtp() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1_000_000));
    }

    private EmailRequest buildEmailRequest(String email, String subject, String textBody,
                                           String htmlBody, String code) {
        if (textBody != null) textBody = textBody.replace("{code}", code);
        if (htmlBody != null) htmlBody = htmlBody.replace("{code}", code);

        return EmailRequest.builder()
                .to(List.of(email))
                .subject(subject)
                .textBody(textBody)
                .htmlBody(htmlBody)
                .build();
    }

    @Transactional(readOnly = true)
    public void verifyPreviouslyValidatedOtp(String email, String otp, String purpose) {
        try {
            OtpTokenModel token = otpTokenRepository.findByEmailAndPurpose(email, Constants.RECOVERY_PASSWORD)
                    .orElseThrow(() -> new AppException(ResponseMessages.OTP_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!token.isUsed() || token.getValidatedAt() == null) {
                throw new AppException(ResponseMessages.OTP_CODE_NOT_VALIDATED, HttpStatus.UNAUTHORIZED);
            }

            if (!token.getCode().equals(otp)) {
                throw new AppException(ResponseMessages.OTP_CODE_NOT_MATCH, HttpStatus.UNAUTHORIZED);
            }

            if (token.getValidatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
                throw new AppException(ResponseMessages.OTP_CODE_VALIDATION_EXPIRED, HttpStatus.UNAUTHORIZED);
            }
        } catch (AppException e) {
            throw e;
        }
    }
}
