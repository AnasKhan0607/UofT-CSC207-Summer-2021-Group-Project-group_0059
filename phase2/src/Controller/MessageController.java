package Controller;


import Gateway.MessageGate;
import Interface.LoadSave;
import Presenter.GamePresenter;

import UseCase.MessageManager;

import java.util.*;
/**
 * Controller for the Messages.
 * @author Ahmad I., Ruilin P.
 */
public class MessageController {
    private String currentUserName;
    private MessageManager mm;

    /**
     * create the controller, call to get Messages loaded and also load the one operating
     * @param un a MessageManager to be used
     */
    public MessageController(String un){
        this.currentUserName = un;
        LoadSave gate = new MessageGate();
        this.mm = new MessageManager(gate);

    }

    /**
     * the process of making choices
     */
    public void run(){

        GamePresenter messagePresenterV2 = new GamePresenter();

        while (true) {

            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Read the messages");
            choices.add("2. Compose a message");
            choices.add("3. QUIT");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, "+ currentUserName + ". what would you like to do?");

            if (choice == 1){
                String gameName = messagePresenterV2.displayMessages(this, mm.getMessage(currentUserName));
                if (!gameName.equals("")){
                    new GamePlayController(new AdminUserNavigatorController("Admin_"), gamePresenter).
                            playSpecificGame(gameName);
                }
                mm.markAllAsRead(currentUserName);
            } else if (choice == 2){
                writeMessage();
            } else if(choice == 3){
                break;
            } else{
                gamePresenter.displayTextScene(this, "CONTINUE", "Invalid choice. Please try again");
            }


        }

    }





    private void writeMessage(){

        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Please input the name of the receiver, type EVERYONE to send the same message " +
                "to every user recorded in the system(for use of ADMIN ONLY)");
        inputs.add("Your message:");
        inputs.add("Your attachment(the gameName you want to share. Type N/A if doesn't apply):");
        List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Login");
        String receiver = userinputs.get(0);
        String msg = userinputs.get(1);
        String attachment = userinputs.get(2);
        if (receiver.equals("EVERYONE")){
            if (currentUserName.startsWith("Admin")) {
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(currentUserName);
                temp.add(msg);
                temp.add(attachment);
                temp.add(new Date());
                mm.addMessageEveryone(temp);
                gamePresenter.displayTextScene(this, "CONTINUE", "Message successfully composed and sent to " + receiver + " .");
            } else {gamePresenter.displayTextScene(this, "BACK", "Since you are not an admin,\" +\n" +
                    "            \"you are not authorized to do that");}

        } else {
            ArrayList<Object> temp = new ArrayList<>();
            temp.add(msg);
            temp.add(currentUserName);
            temp.add(receiver);
            temp.add(attachment);
            temp.add(new Date());
            boolean status;
            status = mm.addMessage(temp);
            if (status) {
                gamePresenter.displayTextScene(this, "CONTINUE", "Message successfully composed and sent to " + receiver + " .");
            } else {
                gamePresenter.displayTextScene(this, "CONTINUE", "User " + receiver + " does not exist!");
            }
        }


    }

    /**
     * remove a Message of given id
     * @param id the id of the Message
     */
    public void removeMessage(String id){


        mm.deleteMessage(id);

    }




}
