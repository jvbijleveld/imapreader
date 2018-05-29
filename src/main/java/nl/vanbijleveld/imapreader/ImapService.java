package nl.vanbijleveld.imapreader;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import nl.vanbijleveld.imapreader.entities.Email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImapService {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ImapFolderReader imapFolder;
    private Folder inbox;
    private final String PROCESSED_MAIL_FOLDER = "processed";

    public List<Email> getAllNewEmail() throws MessagingException, IOException {
        Message[] newMessages = checkForNewEmail();
        List<Email> emails = MessageToMailWrapper.wrap(newMessages);
        setProcessed(newMessages);
        imapFolder.close();
        return emails;
    }

    public ImapService(String mailServer, String userName, String password) throws MessagingException {
        imapFolder = new ImapFolderReader(mailServer, userName, password);
    }

    private Message[] checkForNewEmail() throws MessagingException {
        inbox = imapFolder.openFolder("Inbox", true);
        return inbox.getMessages();
    }

    private void setProcessed(Message[] messages) throws MessagingException {
        for (Message message : messages) {
            setProcessed(message);
        }
    }

    private void setProcessed(Message mail) throws MessagingException {
        imapFolder.moveToFolder(mail, PROCESSED_MAIL_FOLDER);
    }

}
