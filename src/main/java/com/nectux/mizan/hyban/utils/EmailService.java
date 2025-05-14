package com.nectux.mizan.hyban.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, Map<String, String> variables)
            throws MessagingException, IOException {

        // Charger le fichier HTML
        String htmlTemplate = Files.readString(
                new ClassPathResource("templates/email-template.html").getFile().toPath(),
                StandardCharsets.UTF_8
        );

        // Remplacer les variables {{key}}
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            htmlTemplate = htmlTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlTemplate, true);
        helper.setFrom("ybijoseph@gmail.com");

        mailSender.send(message);
    }


}
