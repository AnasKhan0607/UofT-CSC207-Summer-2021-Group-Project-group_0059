package Controller;

import Presenter.GamePresenter;
import UseCase.UserManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the user signup process.
 * @author Ahmad I., Ruilin P.
 */
public class UserSignupController {
    private ArrayList<String> userInput;
    private String username;
    private String password;
    private UserManager testUM;

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

        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Username:");
        inputs.add("Password:");
        inputs.add("Enter your password again:");
        List<String> userinputs = gamePresenter.displayInputs(this, inputs, "When creating your username, put 'Temp_' at the beginning for a Temporary account (lasting 30 days), otherwise input the username normally.");
        if (userinputs.get(1).equals(userinputs.get(2))){
            this.username = userinputs.get(0);
            if (testUM.SearchUser(this.username) != null) {
                gamePresenter.displayTextScene(this, "BACK", "Sorry, there is already an account with this username. " +
                        "Please try again with another username and press enter.");

            } else if (userinputs.get(1).length() < 8) {
                gamePresenter.displayTextScene(this, "BACK", "Sorry, but this password is " +
                        "too weak. Please try again using a password with a minimum of 8 characters.");
            } else {
                this.username = userinputs.get(0);
                this.password = userinputs.get(1);
                userInput.add(this.username);
                userInput.add(this.password);
                testUM.addUser(userInput);
                if (PasswordStrength(password)){
                    gamePresenter.displayTextScene(this, "CONTINUE", "Successfully signed up. Password " +
                            "Strength: HIGH");
                } else {
                    gamePresenter.displayTextScene(this, "CONTINUE", "Successfully signed up. Password " +
                            "Strength: MEDIUM");
                }
            }} else {

            gamePresenter.displayTextScene(this, "BACK", "Sorry, but that's not a match!");

        }


    }

    // helper method

    private boolean PasswordStrength(String password) {
        // Source for the Regex for what is considered a good password: https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
        /* Criteria for a good password:
        Password must contain at least one digit [0-9].
        Password must contain at least one lowercase Latin character [a-z].
        Password must contain at least one uppercase Latin character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters (in our case we are allowing more than 20 characters)
         */
        String goodPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";
        Pattern pattern = Pattern.compile(goodPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
