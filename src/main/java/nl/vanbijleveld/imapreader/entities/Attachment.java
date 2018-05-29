package nl.vanbijleveld.imapreader.entities;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Attachment implements Serializable {

    private static final long serialVersionUID = 9106880831272504591L;

    private String fileName;

    private byte[] attachment;

    public Attachment() {
    }

    public Attachment(final String fileName, final byte[] attachment) {
        this.fileName = fileName;
        this.attachment = attachment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(final byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }

        final Attachment that = (Attachment) o;

        return new EqualsBuilder().append(fileName, that.fileName).append(attachment, that.attachment).isEquals();
    }

    @Override
    public String toString() {
        return "Attachment{fileName=" + fileName + "}";
    }

}
