package Controller;

import Gateway.UserGate;
import Interface.UserMessageLoadSave;
import Presenter.GamePresenter;

import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
/**
 * Controller for the suspension process.
 * @author Ahmad I., Ruilin P.
 */
public class SuspensionController {
    private String username;


    /**
     * the process of doing choices(suspend? unsuspend? or just quit)
     */
    public void run(){
        while (true) {
            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. suspend a User?(cannot suspend an admin)");
            choices.add("2. unsuspend a User?");
            choices.add("3. quit");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, what would you like to do?");
            if (choice == 1){
                suspend();
            } else if (choice == 2){
                unsuspend();
            } else if (choice == 3){
                break;
            } else {
                gamePresenter.displayTextScene(this, "BACK", username + "Invalid choice. Please try again!");
            }
        }
    }

    /**
     * the process doing suspend
     */
    private void suspend(){
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Please input the username you need to suspend:");
        inputs.add("For how many days:");
        List<String> lst = gamePresenter.displayInputs(this, inputs, "");
        String choice = lst.get(0);
        int x = Integer.parseInt((String) lst.get(1));
        username = choice;
        if (!username.startsWith("Admin_")){
            UserMessageLoadSave gate = new UserGate();
            UserManager tempum = new UserManager(gate);
            boolean rst = tempum.suspendUser(username, x);
            if (rst){
                gamePresenter.displayTextScene(this, "CONTINUE", username + "has been successfully unsuspended");
            } else {gamePresenter.displayTextScene(this, "BACK", "Username not found");}} else {gamePresenter.displayTextScene(this, "BACK", "You cannot suspend another Admin !");}

    }
    /**
     * the process doing unsuspend
     */
    private void unsuspend(){
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Please input the username you need to unsuspend:");
        String choice = (String)gamePresenter.displayInputs(this, inputs, "").get(0);
        username = choice;
        UserMessageLoadSave gate = new UserGate();
        UserManager tempum = new UserManager(gate);
        boolean rst = tempum.unsuspendUser(username);
        if (rst){gamePresenter.displayTextScene(this, "CONTINUE", username + "has been successfully unsuspended");}
        else {gamePresenter.displayTextScene(this, "BACK", "Username not found");}
    }

    /**
     * get the username doing this action
     * @return a String of the username operating
     */
    public String getUsername() {
        return username;
    }


}
