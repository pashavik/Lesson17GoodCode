package task1;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class ReportNotifier {
    public static void sendToMail(final String recipients, final String htmlReport) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            mailSender.setHost("mail.google.com");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(htmlReport, true);
            helper.setSubject("Monthly department salary report");
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
