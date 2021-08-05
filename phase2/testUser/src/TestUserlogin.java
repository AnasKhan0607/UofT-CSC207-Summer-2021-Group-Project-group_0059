import Controller.UserLoginController;
import UseCase.UserManager;

import java.util.ArrayList;

public class TestUserlogin {

    public static void main(String[] args){
        ArrayList<String> u1info = new ArrayList<String>();
        u1info.add("Admin_AhmadI");
        u1info.add("123");
        UserManager testUM = new UserManager();
        /* tese initializing a UserManager */
        testUM.addUser(u1info);
        /* test adding a user directly through UserManager*/
        UserLoginController c1 = new UserLoginController();
        c1.NormalUserinput();
        /* test login, signup. With manual inputs(run)*/

    }




}
