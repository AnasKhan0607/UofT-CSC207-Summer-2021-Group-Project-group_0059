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
        Scanner myObj = new Scanner(System.in);
        AdminUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* Game*/
            AdminUserNavigatorPresenter.redirectingMessage();
            TemplateEditorController te = new TemplateEditorController();
            GameMainController gc = new GameMainController(te ,this::currentUser);
            gc.gameMenu();

        } else if (choice == 2) {
            /* TemplateEditor*/
            AdminUserNavigatorPresenter.redirectingMessage();
            TemplateEditorController tec = new TemplateEditorController();
            tec.run();

        } else if (choice == 3) {
            /*logout*/
            AdminUserNavigatorPresenter.logoutMessage();

        } else if (choice == 4) {
            SuspensionController sc1 = new SuspensionController();
            sc1.suspend();
        }
    }
}
