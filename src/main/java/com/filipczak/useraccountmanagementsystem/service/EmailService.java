package com.filipczak.useraccountmanagementsystem.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMailWithActivationLink(int id, String mail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mail);
            mailMessage.setSubject("Activate your account!");
            mailMessage.setFrom("System-administration");
            mailMessage.setText("To confirm your account, please click here: "
                    + "http://localhost:8080/users/confirm-account?code=" + id);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}
