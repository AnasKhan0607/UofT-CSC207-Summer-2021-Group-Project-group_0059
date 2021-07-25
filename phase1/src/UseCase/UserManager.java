package UseCase;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> bufferedUsers;

    public UserManager(){
        this.bufferedUsers = new ArrayList<>();
    }

    public void addUser(ArrayList<String> info){
        if (info.size() == 2){
            String username = info.get(0);
            String password = info.get(1);
            if (username.charAt(0) == 'A'){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
            } else {
                RegularUser tempUser = new RegularUser(username, password);
                this.bufferedUsers.add(tempUser);
            }
        } else {
            String username = info.get(0);
            GuestUser tempUser = new GuestUser(username);
            this.bufferedUsers.add(tempUser);
        }
    }

    public User SearchUser(String username){
        int i;
        User temp;
        for (i = 0; i < bufferedUsers.size(); i++) {
            temp = bufferedUsers.get(i);
            if (temp.getUsername().equals(username)) {
                return temp;
            }
        }
        /* Username not found */
        return null;
    }

    public void Load(){}
    /* for the constructor */
    public void Save(){}
    /* for the addUser()*/
    @Override
    public String toString() {
        return "UserManager{" +
                "bufferedUsers=" + bufferedUsers +
                '}';
    }
}


