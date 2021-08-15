package Controller;

import Interface.UserData;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.GamePresenter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * the controller class that interacts with Userinputs from AdminUser
 */
public class AdminUserNavigatorController implements UserData {
    private String username;
    private Scanner scanner = new Scanner(System.in);

    /**
     * the constructor for this controller class
     * @param un the username received as a parameter
     */
    public AdminUserNavigatorController(String un){
        username = un;
    }

    /**
     * return the current User's username
     * @return: the username of current (Admin)User
     */
    public String currentUser(){
        return username;
    }

    /**
     * the main method that is run to accept user inputs and redirect to corresponding controllers
     */
    public void run(){

        int choice = 0;
        while (true){
            System.out.println();
            System.out.println();
            System.out.println();
            /**
            AdminUserNavigatorPresenter.display(username);

            choice = Integer.valueOf(scanner.next());
             **/
            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Select a Game to create/edit/play");
            choices.add("2.Select a Template to edit");
            choices.add("3.Open message box");
            choices.add("4.Suspend/Unsuspend a User(RegularUser only)");
            choices.add("5.Logout");
            choice = gamePresenter.displayChoices(this, choices, "Hello, "+ username + ". what would you like to do?");



            if (choice == 0) {
                /* Game*/
                AdminUserNavigatorPresenter.redirectingMessage();
                TemplateEditorController te = new TemplateEditorController();
                GameMainController gc = new GameMainController(te ,this::currentUser);
                gc.gameMenuAdminUser();

            } else if (choice == 1) {
                /* TemplateEditor*/
                AdminUserNavigatorPresenter.redirectingMessage();
                TemplateEditorController tec = new TemplateEditorController();
                tec.run();

            } else if (choice == 2){
                MessageController c1 = new MessageController(username);
                c1.run();
            }
            else if (choice == 4){
                /*logout*/

                gamePresenter.displayTextScene(this, "CONTINUE", "Successfully logged out");

                break;

            } else if (choice == 3){
                SuspensionController sc1 = new SuspensionController();
                sc1.run();

            }
            else {//AdminUserNavigatorPresenter.errorMessage();
                gamePresenter.displayTextScene(this, "Invalid choice, please try again"); }
        }



    }
}
