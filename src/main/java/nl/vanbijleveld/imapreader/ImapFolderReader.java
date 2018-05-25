package nl.vanbijleveld.imapreader;

import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class ImapFolderReader {

    private Folder folder = null;
    private Store store = null;

    public ImapFolderReader(String mailServer, String userName, String password) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        // session.setDebug(true);
        store = session.getStore("imaps");
        store.connect(mailServer, userName, password);
    }

    public Folder openFolder(String folderName, boolean write) throws MessagingException {
        if (store == null) {

        }
        folder = store.getFolder(folderName);
        if (write) {
            folder.open(Folder.READ_WRITE);
        } else {
            folder.open(Folder.READ_ONLY);
        }
        return folder;
    }

    public void moveToFolder(Message message, String folderName) throws MessagingException {
        if (folder.getMode() != Folder.READ_WRITE) {
            throw new MessagingException("Folder should be opened read/write");
        }
        Folder newFolder = store.getFolder(folderName);

        Message[] msgs = new Message[] {message};
        folder.copyMessages(msgs, newFolder);

        message.setFlag(Flag.DELETED, true);
    }

    public void close() {
        try {
            folder.expunge();
            if (folder != null) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
