package Controller;

import Presenter.GamePresenter;
import Presenter.UserSignUpPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
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
        /**
        Scanner myObj = new Scanner(System.in);
        UserSignUpPresenter.display();
        this.username = myObj.nextLine();
        this.password = myObj.nextLine();
         **/
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Username:");
        inputs.add("Password:");
        inputs.add("Enter your password again:");
        List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Signup");
        if (userinputs.get(1).equals(userinputs.get(2))){
            this.username = userinputs.get(0);
            if (testUM.SearchUser(this.username) != null) {
                gamePresenter.displayTextScene(this, "BACK", "Sorry, there is already an account with this username. " +
                        "Please try again with another username and press enter.");
                //UserSignUpPresenter.errorMessage();
            } else {
                this.username = userinputs.get(0);
                this.password = userinputs.get(1);
                userInput.add(this.username);
                userInput.add(this.password);
                testUM.addUser(userInput);
                UserSignUpPresenter.successMessage();
            }} else {
            //UserSignUpPresenter.errorMessageUnmatch();
            gamePresenter.displayTextScene(this, "BACK", "Sorry, but that's not a match!");

        }


    }
}
