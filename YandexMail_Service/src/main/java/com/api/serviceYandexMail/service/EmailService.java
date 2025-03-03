package com.api.serviceYandexMail.service;

public interface EmailService {

    String sendEmailWithAttachment(String email, String text, byte[] attachment,String filename);
}
