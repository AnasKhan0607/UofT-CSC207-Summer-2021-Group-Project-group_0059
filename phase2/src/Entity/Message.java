package Entity;

import java.util.Comparator;
import java.util.Date;

public class Message {
    private String msg;
    private String from;
    private String to;
    private Date time;
    private boolean status;

    public Message(String msg, String from, String to, Date time, boolean status){
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

    public void markAsRead(){
        this.status = true;
    }


}

