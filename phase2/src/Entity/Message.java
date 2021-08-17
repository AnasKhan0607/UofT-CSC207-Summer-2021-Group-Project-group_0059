package Entity;


import java.util.Date;


/**
 * Represents a Message.
 * @author Ahmad I., Ruilin P.
 */
public class Message {
    private String id;
    private String msg;
    private String from;
    private String to;
    private String attachment;
    private Date time;
    private boolean status;

    /**
     * create a Message
     * @param id the id of the message, used for sorting and deletion internally
     * @param msg the content of the message
     * @param from the author of the message
     * @param to the recipient of the message
     * @param attachment the Game(Name) attached in a message, "N/A" if doesn't apply
     * @param time the time this Message is generated
     * @param status whether this message has been read by the recipient or not
     */
    public Message(String id,String msg, String from, String to, String attachment, Date time, boolean status){
        this.id = id;
        this.msg = msg;
        this.from = from;
        this.to = to;
        this.time = time;
        this.status = status;
        this.attachment = attachment;
    }

    /**
     * returns the message content of the Message
     * @return a String representing the content of the message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * return the time it's created
     * @return the Date it's created
     */
    public Date getTime() {
        return time;
    }

    /**
     * return the author of the message
     * @return a String of author's username
     */
    public String getFrom() {
        return from;
    }

    /**
     * return the recipient of the Message
     * @return a String of recipient's username
     */
    public String getTo(){
        return to;
    }

    /**
     * return the status of whether read or not
     * @return boolean of the read status
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * get the id of the message
     * @return the String of the id
     */
    public String getid(){
        return id;
    }

    /**
     * return the attachment (GameName)
     * @return a String representing the GameName of attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * set the status as read
     */
    public void markAsRead(){
        this.status = true;
    }


}

