package Controller;

import Presenter.UserSignUpPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class UserSignupController {
    private ArrayList<String> userInput;
    private String username;
    private String password;
    private UserManager testUM = new UserManager();

    public UserSignupController() {
        this.username = null;
        this.password = null;
        this.userInput = new ArrayList<>();
    }

    public void UserInput() {
        Scanner myObj = new Scanner(System.in);
        UserSignUpPresenter.display();
        this.username = myObj.nextLine();
        this.password = myObj.nextLine();
        while (testUM.SearchUser(this.username) != null) {
            UserSignUpPresenter.errorMessage();
            this.username = myObj.nextLine();
        }
        userInput.add(this.username);
        userInput.add(this.password);
        testUM.addUser(userInput);
        UserSignUpPresenter.successMessage();
    }
}
