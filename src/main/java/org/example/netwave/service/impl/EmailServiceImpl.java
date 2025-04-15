package org.example.netwave.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.netwave.dto.PayEmailRequestDTO;
import org.example.netwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public void sendPaymentConfirmationEmail(PayEmailRequestDTO emailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());

        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
            Date date = inputFormat.parse(emailRequest.getTimestamp());
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            formattedDate = emailRequest.getTimestamp();
        }


        String htmlContent =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <style>" +
                        "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                        "        .container { width: 600px; margin: 0 auto; padding: 20px; }" +
                        "        .header { background-color: #0066cc; color: white; padding: 10px 20px; }" +
                        "        .content { padding: 20px; border: 1px solid #ddd; }" +
                        "        .footer { font-size: 12px; color: #777; margin-top: 20px; }" +
                        "        .details { margin: 20px 0; }" +
                        "        .details table { width: 100%; border-collapse: collapse; }" +
                        "        .details td { padding: 8px; border-bottom: 1px solid #eee; }" +
                        "        .details td:first-child { font-weight: bold; width: 40%; }" +
                        "        .amount { font-size: 24px; color: #0066cc; font-weight: bold; }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class='container'>" +
                        "        <div class='header'>" +
                        "            <h2>SLT Mobitel Payment Confirmation</h2>" +
                        "        </div>" +
                        "        <div class='content'>" +
                        "            <p>Dear Customer,</p>" +
                        "            <p>Thank you for your payment. Your transaction has been completed successfully.</p>" +
                        "            <div class='details'>" +
                        "                <table>" +
                        "                    <tr>" +
                        "                        <td>Transaction ID:</td>" +
                        "                        <td>" + emailRequest.getTransactionId() + "</td>" +
                        "                    </tr>" +
                        "                    <tr>" +
                        "                        <td>Phone Number:</td>" +
                        "                        <td>" + emailRequest.getPhoneNumber() + "</td>" +
                        "                    </tr>" +
                        "                    <tr>" +
                        "                        <td>Amount:</td>" +
                        "                        <td class='amount'>Rs. " + emailRequest.getAmount() + "</td>" +
                        "                    </tr>" +
                        "                    <tr>" +
                        "                        <td>Date & Time:</td>" +
                        "                        <td>" + formattedDate + "</td>" +
                        "                    </tr>" +
                        "                </table>" +
                        "            </div>" +
                        "            <p>Your account has been recharged with the above amount. The balance will be reflected in your account shortly.</p>" +
                        "            <p>If you have any questions, please contact our customer support at <a href='mailto:support@sltmobitel.lk'>support@sltmobitel.lk</a> or call our hotline at 1717.</p>" +
                        "        </div>" +
                        "        <div class='footer'>" +
                        "            <p>This is an automated message. Please do not reply to this email.</p>" +
                        "            <p>&copy; " + new SimpleDateFormat("yyyy").format(new Date()) + " SLT Mobitel. All rights reserved.</p>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}

