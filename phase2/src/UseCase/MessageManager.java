package UseCase;

import Entity.AdminUser;
import Entity.Message;
import Entity.RegularUser;
import Gateway.MessageGate;

import java.util.*;

public class MessageManager {
    private List<Message> bufferedMessages;

    public MessageManager(){
        bufferedMessages = new ArrayList<>();

        MessageGate MG = new MessageGate();
        HashMap<String, List<Object>> tempMessages = (HashMap<String, List<Object>>) MG.load().get(0);

        for (Map.Entry mapElement :tempMessages.entrySet()){
            String msg = (String)mapElement.getKey();
            List lst = (List)mapElement.getValue();
            String from = (String)lst.get(0);
            String to = (String)lst.get(1);
            Date time = (Date)lst.get(2);
            boolean status = (boolean) lst.get(3);

            Message tempMessage = new Message(msg, from, to ,time, status);
            bufferedMessages.add(tempMessage);


        }

    }

    public void addMessage(ArrayList<Object> info){
        String from = (String)info.get(1);
        String to = (String)info.get(2);
        Date time = (Date)info.get(3);
        String msg = (String)info.get(0);
        bufferedMessages.add(new Message(msg,from,to,time,false));

        save(msg,from,to,time,false);

    }


    public List<Message> getMessage(String username){
        ArrayList<Message> msgs = new ArrayList<>();
        for (Message msg: bufferedMessages){
            if (msg.getTo().equals(username)) {
                msgs.add(msg);

                save(msg.getMsg(),msg.getFrom(),msg.getTo(),msg.getTime(),true);
            }
        }
        return msgs;
    }

    private void save(String msg, String from, String to, Date time, boolean status){
        MessageGate MG = new MessageGate();
        HashMap<String, List<Object>> oldMessages = (HashMap<String, List<Object>>) MG.load().get(0);

        List<Object> sections = new ArrayList <Object>();
        sections.add(from);
        sections.add(to);
        sections.add(time);
        sections.add(status);
        oldMessages.put(msg, sections);
        List<HashMap> MessageData = new ArrayList<HashMap>();
        MessageData.add(oldMessages);

        MG.save(MessageData);
    }
}
