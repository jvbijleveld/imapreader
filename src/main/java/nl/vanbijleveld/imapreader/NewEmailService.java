package nl.vanbijleveld.imapreader;

import java.util.concurrent.Callable;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class NewEmailService {

    private final ImapFolderReader imapFolder;
    private Folder inbox;
    private final String PROCESSED_MAIL_FOLDER = "processed";

    public NewEmailService(String mailServer, String userName, String password) throws MessagingException {
        imapFolder = new ImapFolderReader(mailServer, userName, password);
    }

    public Message[] checkForNewEmail() throws MessagingException {
        inbox = imapFolder.openFolder("Inbox", true);
        return inbox.getMessages();
    }

    public void processNewMessages(Callable<Void> func) {
        Message[] newMessages;
        try {
            newMessages = checkForNewEmail();
            for (Message mail : newMessages) {
                System.out.println("processing mail: " + mail.getSubject() + " received on " + mail.getReceivedDate() + " from " + mail.getFrom());

                func.call();
                imapFolder.moveToFolder(mail, PROCESSED_MAIL_FOLDER);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            imapFolder.close();
        }

    }
}
