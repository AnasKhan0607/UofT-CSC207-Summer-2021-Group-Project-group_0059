package Controller;

import Interface.UserData;
import Presenter.RegularUserNavigatorPresenter;

import java.util.Scanner;

public class RegularUserNavigatorController implements UserData {
    private String username;

    public RegularUserNavigatorController(String un){
        username = un;
    }

    public String currentUser(){
        return username;
    }



    public void run() {
        Scanner myObj = new Scanner(System.in);
        RegularUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* Game*/
            TemplateEditorController te = new TemplateEditorController();
            GameMainController gc = new GameMainController(te ,this::currentUser);
            gc.gameMenu();

        } else if (choice == 2) {
            /*logout*/

        }
    }
}
