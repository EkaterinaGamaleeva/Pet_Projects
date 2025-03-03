package com.api.serviceYandexMail.service.Impl;

import com.api.serviceYandexMail.exception.CustomException;
import com.api.serviceYandexMail.service.EmailService;
import com.api.serviceYandexMail.util.AppConstants;
import com.api.serviceYandexMail.util.ErrorsCodesAndMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    private final String subject = AppConstants.SUBJECT_EMAIL;

    @Override
    public String sendEmailWithAttachment(String email, String message, byte[] attachment,String filename) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        if (email == null || message == null) {
            email = AppConstants.DEFAULT_EMAIL;
            message = AppConstants.DEFAULT_TEXT_EMAIL;
        }
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message);
            if (attachment != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment, AppConstants.CONTENT_TYPE);
                helper.addAttachment(filename, dataSource);
            }
        } catch (MessagingException e) {
            throw new CustomException(ErrorsCodesAndMessage.FILE_SEND_ERROR);
        }
        mailSender.send(mimeMessage);
        return AppConstants.EMAIL_SEND_OK;
    }
}
