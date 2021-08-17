package Controller;


import Entity.TempUser;
import Entity.User;
import Presenter.GamePresenter;
import Presenter.UserLoginPresenter;
import UseCase.MessageManager;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.time.LocalDate;

/**
 * Controller for the user login process.
 * @author Ahmad I., Ruilin P.
 */
public class UserLoginController {

    private String userName;
    private String password;
    private UserManager testUM;

    /**
     * Creates a new UserLoginController object that will set the userName and password attributes to what the
     * user inputs.
     */
    public UserLoginController(){
        this.userName = null;
        this.password = null;
    }

    /**
     * Uses user input as well as the UserLoginPresenter to allow the user to login to their account, or login as guest.
     */
    public void NormalUserinput(){


        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Login");
        choices.add("Signup");
        choices.add("Login as Guest");
        choices.add("Forget Password");
        choices.add("EXIT");
        while (true){
            testUM = new UserManager();
            int choice = gamePresenter.displayChoices(this, choices, "Please login first:");
            //Scanner myObj = new Scanner(System.in);
            if (choice == 0){


                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("Username:");
                inputs.add("Password:");
                List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Login");
                this.userName = userinputs.get(0);
                this.password = userinputs.get(1);
                RegularLogin();
            } else if (choice == 1){
                UserSignupController signup1 = new UserSignupController(this.testUM);
                signup1.UserInput();
                // Going through the login process again


            } else if (choice == 2){
                GuestUserInput();
            } else if(choice == 3){
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("Your Username:");
                List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Forget password");
                String userName = userinputs.get(0);
                gamePresenter.displayTextScene(this, "CONTINUE", "Thank you for letting us know," +
                        "a message has been sent to admin. If you registered with an email, we'll send you an email containing temporary info" +
                        " for logging in");
                MessageManager mm = new MessageManager();
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(userName + "password forgot. Please process");
                temp.add(userName);
                temp.add("Admin_Ruilin");
                temp.add("N/A");
                temp.add(new Date());
                mm.addMessage(temp);
            } else{
                //UserLoginPresenter.exitMessage();
                gamePresenter.displayTextScene(this, "CONTINUE", "Thank you for using our program");
                gamePresenter.terminateGUI();
                break;
            }
        }

    }



    /**
     * Unique prompts, inputs, and actions taken if the user selects logging in as a guest.
     */
    public void GuestUserInput(){

        UserLoginPresenter.successMessage("Guest");
        redirect(testUM.CreateGuestUser());
    }

    /**
     * Uses the UserNavigator classes to redirect the user to a new UI once they have logged in.
     * @param user a User object that shows whether it is an Admin, Regular, or Guest user to redirect to a new UI
     */
    public void redirect(User user){
        String username = user.getUsername();
        if (username.startsWith("Admin_")){
            AdminUserNavigatorController aunc = new AdminUserNavigatorController(username);
            aunc.run();
        } else if(username.startsWith("Guest")){
            GuestUserNavigatorController gunc = new GuestUserNavigatorController();
            gunc.run();

        } else {
            RegularUserNavigatorController runc = new RegularUserNavigatorController(username);
            runc.run();
        }

    }

    // helper method

    private void RegularLogin() {

        GamePresenter gamePresenter = new GamePresenter();
        if (testUM.SearchUser(this.userName) == null) {

            gamePresenter.displayTextScene(this, "BACK", "Sorry, but either the username or the password is incorrect");
        } else {
            User tempUser = testUM.SearchUser(this.userName);
            String temppassword = tempUser.getPassword();
            if (!temppassword.equals(this.password)) {

                gamePresenter.displayTextScene(this, "BACK", "Sorry, but either the username or the password is incorrect");
            } else if (tempUser.getflag()){

                gamePresenter.displayTextScene(this, "BACK", "(" + tempUser.getUsername() + ") is currently suspended until "+ tempUser.getsuspensionEndTime()+" . Please contact Ruilin or Ahmad for support.");
            } else {
                if (this.userName.startsWith("Admin_")) {

                    gamePresenter.displayTextScene(this, "CONTINUE", "Logged in as Admin" );
                } else if (this.userName.startsWith("Temp_")) {
                    LocalDate endDate = ((TempUser)tempUser).getEndDate();
                    LocalDate today = LocalDate.now();
                    if (today.isAfter(endDate)) {

                        gamePresenter.displayTextScene(this, "BACK", "Sorry, your temporary account has expired." );
                    }
                    else {

                        gamePresenter.displayTextScene(this, "BACK", "Temporary. After " + endDate + " your account will be unavailable.");
                    }
                }
                else {

                    gamePresenter.displayTextScene(this, "CONTINUE", "Logged in as Regular" );
                }
                redirect(tempUser);
            }
        }
    }
}


