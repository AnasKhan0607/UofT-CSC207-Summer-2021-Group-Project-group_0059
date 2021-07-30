package Controller;

import Interface.UserData;
import Presenter.AdminUserNavigatorPresenter;

import java.util.Scanner;

/**
 * the controller class that interacts with Userinputs from AdminUser
 */
public class AdminUserNavigatorController implements UserData {
    private String username;

    /**
     * the constructor for this controller class
     * @param un
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
        Scanner myObj = new Scanner(System.in);
        AdminUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* Game*/
            TemplateEditorController te = new TemplateEditorController();
            GameMainController gc = new GameMainController(te ,this::currentUser);
            gc.gameMenu();

        } else if (choice == 2) {
            /* TemplateEditor*/
            TemplateEditorController tec = new TemplateEditorController();
            tec.run();

        } else if (choice == 3) {
            /*logout*/

        }
    }
}
