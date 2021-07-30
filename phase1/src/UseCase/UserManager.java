package UseCase;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.User;
import Gateway.UserGate;

import java.util.*;

/**
 * The use case class for the users.
 * @author Ahmad I., Ruilin P.
 */
public class UserManager {
    private List<User> bufferedUsers;

    /**
     * Creates a list of users using the UserGate class.
     */
    public UserManager(){
        this.bufferedUsers = new ArrayList<>();

        UserGate myGate = new UserGate();
        HashMap<String, String> tempUsers = (HashMap<String, String>) myGate.load().get(0);;




        for (Map.Entry mapElement :tempUsers.entrySet()){
            String username = (String)mapElement.getKey();
            String password = (String)mapElement.getValue();
            if (username.charAt(0) == 'A'){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
            } else {
                RegularUser tempUser = new RegularUser(username, password);
                this.bufferedUsers.add(tempUser);
            }
        }


        /*buffered array gets updated with what's in tempUsers*/
    }

    /**
     * Adds a user to the list of users in UserManager and in the file.
     * @param info Contains the user's username and password.
     */
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
            UserGate myGate = new UserGate();
            HashMap<String, String> oldUsers = (HashMap<String, String>) myGate.load().get(0);



            oldUsers.put(username, password);
            List<HashMap> userData = new ArrayList<HashMap>();
            userData.add(oldUsers);

            myGate.save(userData); // need this UserGate method that adds the new user to the file

        } else {
            String username = "Guest";
            GuestUser tempUser = new GuestUser(username);
            this.bufferedUsers.add(tempUser);
        }
    }

    /**
     * Checks if a user exists with the given username.
     * @param username The user's username.
     * @return The user object that the username corresponds to, or null.
     */
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

    public GuestUser CreateGuestUser(){
        return new GuestUser("Guest");
    }

    /**
     * Converts the list of users to a string.
     * @return A string containing the list of users.
     */
    @Override
    public String toString() {
        return "UserManager{" +
                "bufferedUsers=" + bufferedUsers +
                '}';
    }
}


