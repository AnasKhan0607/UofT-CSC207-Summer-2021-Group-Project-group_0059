package Controller;

import Entity.Message;
import Entity.MessageSortByTime;
import Presenter.GamePresenter;
import Presenter.MessagePresenter;
import Presenter.MessagePresenterV2;
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
        GamePresenter messagePresenterV2 = new GamePresenter();

        while (true) {

            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Read the messages");
            choices.add("2. Compose a message");
            choices.add("3. Play the creation(just provide the gameName you find in messages)");
            choices.add("4. QUIT");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, "+ currentUserName + ". what would you like to do?");
            while (true){
                if (choice == 1){
                    messagePresenterV2.displayMessages(this, currentUserName, mm.getMessage(currentUserName));
                } else if (choice == 2){
                    writeMessage();
                } else if (choice == 3){
                    gamePresenter.displayTextScene(this, "CONTINUE", "UNDER CONSTRUCTION");
                } else {
                    gamePresenter.displayTextScene(this, "CONTINUE", "Invalid choice. Please try again");
                }
            }
            /**
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
             **/
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
    public void removeMessage(String id){
        MessageManager mm = new MessageManager();
        mm.deleteMessage(id);
        /**
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
         **/
    }




}
