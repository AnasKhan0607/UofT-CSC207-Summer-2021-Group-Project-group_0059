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
        MessagePresenter.writeMessagePrompt1();
        Scanner myObj = new Scanner(System.in);
        String reciver = myObj.nextLine();
        MessagePresenter.writeMessagePrompt2();
        String msg = myObj.nextLine();
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(msg);
        temp.add(currentUserName);
        temp.add(reciver);
        temp.add(new Date());
        boolean status;
        status = mm.addMessage(temp);
        if (status){MessagePresenter.writeMessageSuccess();} else {MessagePresenter.errorMessage();}

        //myObj.close();

    }




}
