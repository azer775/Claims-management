package org.example.gestionsinistre.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.gestionsinistre.entities.Token;
import org.example.gestionsinistre.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailingService {
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void sendEmail(User user, String subject, String token) throws MessagingException {
        String html="<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">\n" +
                "\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "        <tr>\n" +
                "            <td align=\"center\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"background-color: #ffffff; border-radius: 10px; box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.1);\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px;\">\n" +
                "                            <h2 style=\"color: #333333; margin-bottom: 20px;\">Hello!</h2>\n" +
                "                            <p style=\"color: #666666; line-height: 1.6;\">Hello Mr/Ms "+user.getPrenom()+" "+ user.getNom() +"your activation code is "+token+": </p>\n" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top: 30px;\">\n" +
                "                                <tr>\n" +
                "                                    <td align=\"center\" bgcolor=\"#007bff\" style=\"border-radius: 4px;\">\n" +
                "                                        <a href=\"http://localhost:4200/activate"+"\" target=\"_blank\" style=\"font-size: 16px; font-weight: bold; color: #ffffff; text-decoration: none; padding: 12px 30px; display: inline-block; border-radius: 4px;\">Click Here</a>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "</body>";
        MimeMessage message = mailSender.createMimeMessage();
        //message.setText(html);
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setFrom("azer.bennasr@esprit.tn");
        message.setSubject(subject);
        message.setContent(html, "text/html; charset=utf-8");
        // message.setText(body);


        mailSender.send(message);
    }

    public MailingService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
