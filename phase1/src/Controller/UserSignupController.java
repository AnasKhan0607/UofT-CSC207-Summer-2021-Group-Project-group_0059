package Controller;

import Presenter.UserSignUpPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller for the user signup process.
 * @author Ahmad I., Ruilin P.
 */
public class UserSignupController {
    private ArrayList<String> userInput;
    private String username;
    private String password;
    private UserManager testUM = new UserManager();

    /**
     * Creates a new UserSignupController object with a UserManager object containing the temporary list of users
     * passed from UserLoginController.
     * @param um1 a UserManager object that has access to the temporary list of users, passed from UserLoginController
     */
    public UserSignupController(UserManager um1) {
        this.username = null;
        this.password = null;
        this.userInput = new ArrayList<>();
        this.testUM = um1;
    }

    /**
     * Gives the user instructions from the presenter, reads user input, and adds the new user to the list of users.
     */
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
