package nl.vanbijleveld.imapreader;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class ImapService {

    private final ImapFolderReader imapFolder;
    private Folder inbox;
    private final String PROCESSED_MAIL_FOLDER = "processed";

    public ImapService(String mailServer, String userName, String password) throws MessagingException {
        imapFolder = new ImapFolderReader(mailServer, userName, password);
    }

    public Message[] checkForNewEmail() throws MessagingException {
        inbox = imapFolder.openFolder("Inbox", true);
        return inbox.getMessages();
    }

    public void setProcessed(Message mail) throws MessagingException {
        imapFolder.moveToFolder(mail, PROCESSED_MAIL_FOLDER);
    }

    public void processNewMessages() {
        Message[] newMessages;
        try {
            newMessages = checkForNewEmail();
            for (Message mail : newMessages) {
                System.out.println("processing mail: " + mail.getSubject() + " received on " + mail.getReceivedDate() + " from " + mail.getFrom());
                setProcessed(mail);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            imapFolder.close();
        }

    }
}
