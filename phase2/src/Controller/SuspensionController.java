package Controller;

import Presenter.SuspensionPresenter;
import UseCase.UserManager;

import java.util.Scanner;

public class SuspensionController {
    private String username;

    public SuspensionController(){
        Scanner myObj = new Scanner(System.in);
        SuspensionPresenter.display1();
        String choice = myObj.nextLine();
        username = choice;
    }

    public void suspend(){
        UserManager tempum = new UserManager();
        tempum.suspendUser(username);
        SuspensionPresenter.successMessage(username);
    }

    public String getUsername() {
        return username;
    }


}
