package nl.vanbijleveld.imapreader;

import java.io.IOException;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import nl.vanbijleveld.imapreader.entities.Email;

public class ImapService {

    private final ImapFolderReader imapFolder;
    private Folder inbox;
    private final String PROCESSED_MAIL_FOLDER = "processed";

    public List<Email> getAllNewEmail() throws MessagingException, IOException {
        Message[] newMessages = checkForNewEmail();
        List<Email> emails = MessageToMailWrapper.wrap(newMessages);
        setProcessed(newMessages);
        return emails;
    }

    private ImapService(String mailServer, String userName, String password) throws MessagingException {
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
    /*
     * public void processNewMessages() { Message[] newMessages; try { newMessages = checkForNewEmail(); for (Message
     * mail : newMessages) { System.out.println("processing mail: " + mail.getSubject() + " received on " +
     * mail.getReceivedDate() + " from " + mail.getFrom()); setProcessed(mail); } } catch (Exception e) { // TODO
     * Auto-generated catch block e.printStackTrace();
     * 
     * } finally { imapFolder.close(); }
     * 
     * }
     */
}
