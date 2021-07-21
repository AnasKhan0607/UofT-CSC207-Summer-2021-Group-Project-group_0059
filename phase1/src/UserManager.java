import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private ArrayList<User> bufferedUsers;

    public UserManager(){
        this.bufferedUsers = new ArrayList<User>();
    }

    public void addUser(ArrayList<String> info){
        User temp = new User(info.get(0));
        this.bufferedUsers.add(temp);
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

    @Override
    public String toString() {
        return "UserManager{" +
                "bufferedUsers=" + bufferedUsers +
                '}';
    }
}


