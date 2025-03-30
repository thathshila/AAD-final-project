package org.example.netwave.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.netwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendPackageNotification(String toEmail, String packageName, String packageType, Double price,
                                        String dataLimit, Integer callMinutes, Integer smsLimit, int validityDays) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("📢 New Telecom Package Available: " + packageName);

            String emailContent = "<html><body>" +
                    "<h2>📢 New Telecom Package Available!</h2>" +
                    "<p>Dear User,</p>" +
                    "<p>A new package has been added to our service:</p>" +
                    "<ul>" +
                    "<li><strong>Package Name:</strong> " + packageName + "</li>" +
                    "<li><strong>Type:</strong> " + packageType + "</li>" +
                    "<li><strong>Price:</strong> $" + price + "</li>" +
                    "<li><strong>Data Limit:</strong> " + dataLimit + "</li>" +
                    "<li><strong>Call Minutes:</strong> " + callMinutes + "</li>" +
                    "<li><strong>SMS Limit:</strong> " + smsLimit + "</li>" +
                    "<li><strong>Validity:</strong> " + validityDays + " Days</li>" +
                    "</ul>" +
                    "<p>Thank you for choosing NetWave Telecom!</p>" +
                    "<hr>" +
                    "<p><small>© 2025 NetWave Telecom. All Rights Reserved.</small></p>" +
                    "</body></html>";

            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
