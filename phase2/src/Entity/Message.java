package Entity;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class Message {
    private String id;
    private String msg;
    private String from;
    private String to;
    private Date time;
    private boolean status;

    public Message(String id,String msg, String from, String to, Date time, boolean status){
        this.id = id;
        this.msg = msg;
        this.from = from;
        this.to = to;
        this.time = time;
        this.status = status;
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

    public void markAsRead(){
        this.status = true;
    }


}

