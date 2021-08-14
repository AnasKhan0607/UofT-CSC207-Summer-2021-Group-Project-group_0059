package Controller;

import Entity.Message;
import Entity.MessageSortByTime;
import Presenter.MessagePresenter;
import UseCase.MessageManager;

import java.util.*;

public class MessageController {
    private String currentUserName;
    private MessageManager mm = new MessageManager();

    public MessageController(String un){
        this.currentUserName = un;

    }

    public void run(){
        readMessage();
        while (true) {
            MessagePresenter.welcomingPrompt();
            Scanner myObj = new Scanner(System.in);
            int choice = Integer.valueOf(myObj.next());
            if (choice == 1){
                writeMessage();
            } else if (choice == 2){
                removeMessage();
            } else if (choice == 3){
                break;
            } else {MessagePresenter.errorChoice();}
        }

    }

    public void readMessage(){

        List<Message> buffered = mm.getMessage(currentUserName);
        MessagePresenter.printBoxup(currentUserName);
        MessagePresenter.printBoxDown();
        buffered.sort(new MessageSortByTime());
        for (Message msg: buffered){
            MessagePresenter.printMessage(msg);
            msg.markAsRead();
        }
        MessagePresenter.printBoxDown();
    }



    public void writeMessage(){
        while (true){
            MessagePresenter.writeMessagePrompt1();
            Scanner myObj = new Scanner(System.in);
            String reciver = myObj.nextLine();
            if (reciver.equals("EVERYONE")){
                if (currentUserName.startsWith("Admin")) {MessagePresenter.writeMessagePrompt2();
                    String msg = myObj.nextLine();
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(currentUserName);
                    temp.add(msg);
                    temp.add(new Date());
                    mm.addMessageEveryone(temp);
                    MessagePresenter.writeMessageSuccess(reciver);
                } else {MessagePresenter.errorMessageNotAdmin();}

            } else if (reciver.equals("QUIT")) {
                break;
            } else {
                MessagePresenter.writeMessagePrompt2();
                String msg = myObj.nextLine();
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(msg);
                temp.add(currentUserName);
                temp.add(reciver);
                temp.add(new Date());
                boolean status;
                status = mm.addMessage(temp);
                if (status) {
                    MessagePresenter.writeMessageSuccess(reciver);
                } else {
                    MessagePresenter.errorMessage();
                }
            }
        }




        //myObj.close();

    }
    public void removeMessage(){

        while (true){
            MessagePresenter.writeMessagePrompt3();
            Scanner myObj = new Scanner(System.in);
            String id = myObj.nextLine();
            MessageManager mm = new MessageManager();
            if (mm.deleteMessage(id)){
                MessagePresenter.deleteMessageSuccess(id);

            } else if(id.equals("QUIT")){
                break;
            }else {
                MessagePresenter.errorMessageID(id);
            }
        }
    }




}
