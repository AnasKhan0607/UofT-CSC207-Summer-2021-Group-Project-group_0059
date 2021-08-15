package Controller;

import Interface.UserData;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.GamePresenter;
import Presenter.RegularUserNavigatorPresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * the controller class that interacts with Userinputs from RegularUser
 */
public class RegularUserNavigatorController implements UserData {
    private String username;
    /**
     * the constructor for this controller class
     * @param un
     */
    public RegularUserNavigatorController(String un){
        username = un;
    }
    /**
     * return the current User's username
     * @return: the username of current (Regular)User
     */
    public String currentUser(){
        return username;
    }


    /**
     * the main method that is run to accept user inputs and redirect to corresponding controllers
     */
    public void run() {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println();
            System.out.println();

            /**
            RegularUserNavigatorPresenter.display(username);
            Integer choice = Integer.valueOf(myObj.nextLine());
            **/
            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Select a Game to create/edit/play");
            choices.add("2. Open message box");
            choices.add("3. Logout");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, "+ username + ". what would you like to do?");
             if (choice == 1) {
                /* Game*/
                RegularUserNavigatorPresenter.redirectingMessage();
                TemplateEditorController te = new TemplateEditorController();
                GameMainController gc = new GameMainController(te ,this::currentUser);
                gc.gameMenu();

            } else if (choice == 2){
                MessageController c1 = new MessageController(username);
                c1.run();
            }
             else if(choice == 3) {
                /*logout*/
                 gamePresenter.displayTextScene(this, "CONTINUE", "Successfully logged out");

                break;

            }  else {
                 gamePresenter.displayTextScene(this, "Invalid choice, please try again");
            }
        }
    }
}
