package Entity;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class Message {
    private String id;
    private String msg;
    private String from;
    private String to;
    private String attachment;
    private Date time;
    private boolean status;

    public Message(String id,String msg, String from, String to, String attachment, Date time, boolean status){
        this.id = id;
        this.msg = msg;
        this.from = from;
        this.to = to;
        this.time = time;
        this.status = status;
        this.attachment = attachment;
    }

    public String getMsg() {
        return msg;
    }

    public Date getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }

    public String getTo(){
        return to;
    }

    public boolean getStatus(){
        return status;
    }

    public String getid(){
        return id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void markAsRead(){
        this.status = true;
    }


}

