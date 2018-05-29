package nl.vanbijleveld.imapreader.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class Email implements Serializable {

    private static final long serialVersionUID = 4539457414405302979L;

    @NotEmpty
    private String[] to;

    private Address[] from;

    private String[] cc;

    private String[] bcc;

    private String subject;

    private Date receivedDate;

    @NotNull
    private String message;

    @Valid
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Email() {
    }

    /**
     * Constructor.
     *
     * @param to
     *            Recipient addresses.
     * @param from
     *            From addresses.
     * @param cc
     *            CC addresses.
     * @param bcc
     *            BCC addresses.
     * @param subject
     *            Subject text.
     * @param message
     *            Message text.
     * @param type
     *            Email type.
     */
    public Email(final String to, final Address[] from, final String cc, final String bcc, final String subject, final String message) {
        this.to = new String[] {to};
        this.from = from;
        this.subject = subject;
        this.message = message;
        this.attachments = Collections.emptyList();

        this.cc = new String[] {cc};
        this.bcc = new String[] {bcc};
    }

    /**
     * Constructor.
     *
     * @param to
     *            Recipient addresses.
     * @param from
     *            From addresses.
     * @param cc
     *            CC addresses.
     * @param bcc
     *            BCC addresses.
     * @param subject
     *            Subject text.
     * @param message
     *            Message text.
     * @param type
     *            Email type.
     * @param attachments
     *            Attachments.
     */
    public Email(final String[] to, final Address[] from, final String[] cc, final String[] bcc, final String subject, final String message, final List<Attachment> attachments) {
        this.to = to;
        this.from = from;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(final String[] to) {
        this.to = to;
    }

    public Address[] getFrom() {
        return from;
    }

    public void setFrom(final Address[] addresses) {
        this.from = addresses;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(final String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(final String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }

        final Email email = (Email) o;

        return new EqualsBuilder().append(to, email.to).append(from, email.from).append(cc, email.cc).append(bcc, email.bcc).append(subject, email.subject).append(message,
                email.message).append(attachments, email.attachments).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(to).append(from).append(cc).append(bcc).append(subject).append(message).append(attachments).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("Email{to=%s, from=%s, cc=%s, bcc=%s, subject=%s}", Arrays.toString(to), from, Arrays.toString(cc), Arrays.toString(bcc), subject);
    }
}
