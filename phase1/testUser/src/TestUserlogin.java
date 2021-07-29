import Controller.UserLoginController;
import UseCase.UserManager;

import java.util.ArrayList;

public class TestUserlogin {

    public static void main(String[] args){
        ArrayList<String> u1info = new ArrayList<String>();
        u1info.add("Admin_RuilinP");
        u1info.add("123");
        UserManager testUM = new UserManager();
        testUM.addUser(u1info);
        UserLoginController c1 = new UserLoginController();
        c1.NormalUserinput();

    }




}
