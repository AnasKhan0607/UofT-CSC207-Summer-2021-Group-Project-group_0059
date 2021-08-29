package UseCase;


import Entity.Message;

import Entity.User;
import Gateway.MessageGate;
import Gateway.UserGate;
import Interface.LoadSave;

import java.util.*;
/**
 * The use case class for the Messages.
 * @author Ahmad I., Ruilin P.
 */
public class MessageManager {
    private List<Message> bufferedMessages;
    private UserManager um;
    private LoadSave MG;

    /**
     * create a list of all messages obtained from file
     */
    public MessageManager(LoadSave gate1, LoadSave gate2){
        bufferedMessages = new ArrayList<>();
        MG = gate1;
        um = new UserManager(gate2);
        MessageGate MG = new MessageGate();
        HashMap<String, List<Object>> tempMessages = (HashMap<String, List<Object>>) MG.load().get(0);

        for (Map.Entry mapElement :tempMessages.entrySet()){
            String id = (String)mapElement.getKey();
            List lst = (List)mapElement.getValue();
            String msg = (String)lst.get(0);
            String from = (String)lst.get(1);
            String to = (String)lst.get(2);
            String attachment = (String)lst.get(3);
            Date time = (Date)lst.get(4);
            boolean status = (boolean) lst.get(5);

            Message tempMessage = new Message(id, msg, from, to ,attachment,time, status);
            bufferedMessages.add(tempMessage);


        }

    }

    /**
     * add a Message to the List of Messages and the file
     * @param info the List of User input
     * @return the boolean if the recipient exist(action successful or not)
     */
    public boolean addMessage(ArrayList<Object> info){
        String to = (String)info.get(2);
        if (um.SearchUser(to) != null){
            String id = UUID.randomUUID().toString();
            String from = (String)info.get(1);
            String attachment = (String)info.get(3);
            Date time = (Date)info.get(4);
            String msg = (String)info.get(0);
            bufferedMessages.add(new Message(id, msg,from,to, attachment, time,false));

            save(id, msg,from,to,attachment, time,false);
            return true;
        } else {return false;}


    }

    /**
     * add a same Message to every user
     * @param info the List of User inputs
     */
    public void addMessageEveryone(ArrayList<Object> info){
        String from = (String)info.get(0);
        Date time = (Date)info.get(3);
        String msg = (String)info.get(1);
        String attachment = (String)info.get(2);
        List<User> tos = um.getBufferedUsers();
        for (User temp: tos){
            String id = UUID.randomUUID().toString();
            bufferedMessages.add(new Message(id,msg,from,temp.getUsername(),attachment,time,false));
            save(id, msg,from,temp.getUsername(),attachment,time,false);
        }
    }

    /**
     * retrieve the message of given recipient
     * @param username the name of recipient
     * @return a List of all Message sent to this User
     */
    public List<Message> getMessage(String username){
        ArrayList<Message> msgs = new ArrayList<>();
        for (Message msg: bufferedMessages){
            if (msg.getTo().equals(username)) {
                msgs.add(msg);

                save(msg.getid(), msg.getMsg(),msg.getFrom(),msg.getTo(),msg.getAttachment(),msg.getTime(),true);
            }
        }
        return msgs;
    }

    /**
     * delete a Message of given id
     * @param id the id of the message
     * @return boolean of if action is successful
     */
    public boolean deleteMessage(String id){
        for (Message msg: bufferedMessages){
            if (msg.getid().equals(id)) {
                bufferedMessages.remove(msg);
                save2(msg.getid(), msg.getMsg(),msg.getFrom(),msg.getTo(),msg.getAttachment(),msg.getTime(),true);
                return true;
            }
        }
        return false;
    }

    /**
     * mark every Message of this User as read
     * @param username
     */
    public void markAllAsRead(String username){
        List<Message> bufferedMessages = getMessage(username);
        for (Message msg: bufferedMessages){

            msg.markAsRead();
        }
    }

    /**
     * save a message to file for adding purpose
     * @param id the id of the message
     * @param msg the content of the message
     * @param from the author of the message
     * @param to the recipient of the message
     * @param attachment the attachment attached in the Message
     * @param time the time this message is composed
     * @param status the status or READ or UNREAD
     */
    private void save(String id,String msg, String from, String to,String attachment, Date time, boolean status){
        MessageGate MG = new MessageGate();
        HashMap<String, List<Object>> oldMessages = (HashMap<String, List<Object>>) MG.load().get(0);

        List<Object> sections = new ArrayList <Object>();
        sections.add(msg);
        sections.add(from);
        sections.add(to);
        sections.add(attachment);
        sections.add(time);
        sections.add(status);
        oldMessages.put(id, sections);
        List<HashMap> MessageData = new ArrayList<HashMap>();
        MessageData.add(oldMessages);

        MG.save(MessageData);
    }
    /**
     * save a message to file for delete purpose
     * @param id the id of the message
     * @param msg the content of the message
     * @param from the author of the message
     * @param to the recipient of the message
     * @param attachment the attachment attached in the Message
     * @param time the time this message is composed
     * @param status the status or READ or UNREAD
     */
    private void save2(String id,String msg, String from, String to,String attachment, Date time, boolean status){
        MessageGate MG = new MessageGate();
        HashMap<String, List<Object>> oldMessages = (HashMap<String, List<Object>>) MG.load().get(0);

        List<Object> sections = new ArrayList <Object>();
        sections.add(msg);
        sections.add(from);
        sections.add(to);
        sections.add(attachment);
        sections.add(time);
        sections.add(status);
        oldMessages.remove(id, sections);
        List<HashMap> MessageData = new ArrayList<HashMap>();
        MessageData.add(oldMessages);

        MG.save(MessageData);
    }
}
