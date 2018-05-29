package nl.vanbijleveld.imapreader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import nl.vanbijleveld.imapreader.entities.Attachment;
import nl.vanbijleveld.imapreader.entities.Email;

import org.apache.commons.io.IOUtils;

public class MessageToMailWrapper {

    static public Email wrap(Message message) throws MessagingException, IOException {
        Email newMail = new Email();

        newMail.setFrom(message.getFrom());
        newMail.setSubject(message.getSubject());
        newMail.setMessage(getTextFromMessage(message));
        newMail.setAttachments(getAttachments(message));
        newMail.setReceivedDate(message.getReceivedDate());
        return newMail;
    }

    static public List<Email> wrap(Message[] messages) throws MessagingException, IOException {
        List<Email> emails = new ArrayList<Email>();
        for (Message message : messages) {
            emails.add(wrap(message));
        }
        return emails;
    }

    private static List<Attachment> getAttachments(Message message) throws IOException, MessagingException {
        List<Attachment> fileList = new ArrayList<>();
        String contentType = message.getContentType();

        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();

            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    fileList.add(new Attachment(part.getFileName(), IOUtils.toByteArray(part.getInputStream())));
                }
            }
            return fileList;
        } else {
            return null;
        }

    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}
