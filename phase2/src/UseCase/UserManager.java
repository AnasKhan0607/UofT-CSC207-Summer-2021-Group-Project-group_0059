package UseCase;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.TempUser;
import Entity.User;
import Gateway.UserGate;

import java.util.*;
import java.time.LocalDate;

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
        HashMap<String, List<Object>> tempUsers = (HashMap<String, List<Object>>) myGate.load().get(0);

        for (Map.Entry <String, List<Object>> mapElement :tempUsers.entrySet()){
            System.out.println(mapElement.getKey() + "    " + mapElement.getValue());
        }



            for (Map.Entry <String, List<Object>> mapElement :tempUsers.entrySet()){
            String username = mapElement.getKey();
            List lst = mapElement.getValue();
            String password = (String)lst.get(0);
            boolean suspension = (boolean) lst.get(1);

            if (username.startsWith("Admin_")){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
            } else if (username.startsWith("Temp_")) {
                LocalDate startDate = (LocalDate) lst.get(2); // Temp have their account creation/expiry dates stored
                LocalDate endDate = (LocalDate) lst.get(3);
                TempUser tempUser = new TempUser(username, password, startDate, endDate); // expired accounts will be unable to login
                this.bufferedUsers.add(tempUser);
            } else {
                RegularUser tempUser = new RegularUser(username, password);
                if (suspension) {
                    tempUser.raiseFlag();
                }
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

            UserGate myGate = new UserGate();
            HashMap<String, List<Object>> oldUsers = (HashMap<String, List<Object>>) myGate.load().get(0);
            List<Object> sections = new ArrayList <Object>();
            sections.add(password);
            sections.add(false);

            if (username.startsWith("Admin_")){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
                save(username, password, false);
            } else if (username.startsWith("Temp_")) {
                LocalDate startDate = LocalDate.now();
                LocalDate endDate = LocalDate.now().plusDays(30);
                TempUser tempUser = new TempUser (username, password, startDate, endDate);
                this.bufferedUsers.add(tempUser);
                sections.add(startDate); // storing account creation and expiry dates
                sections.add(endDate);
                oldUsers.put(username, sections);
                List<HashMap> userData = new ArrayList<HashMap>();
                userData.add(oldUsers);

                myGate.save(userData);
            }else {
                RegularUser tempUser = new RegularUser(username, password);
                this.bufferedUsers.add(tempUser);
                save(username, password, false);
            }
            // need this UserGate method that adds the new user to the file

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

    /**
     * create a GuestUser instance and return it
     * @return the GuestUser instance
     */
    public GuestUser CreateGuestUser(){
        return new GuestUser("Guest");
    }

    public List<User> getBufferedUsers() {
        return bufferedUsers;
    }

    public boolean suspendUser(String username){
        int i;
        boolean result = false;
        User temp = bufferedUsers.get(0);
        for (i = 0; i < bufferedUsers.size(); i++) {
            temp = bufferedUsers.get(i);
            if (temp.getUsername().equals(username)) {
               temp.raiseFlag();
               result = true;
               break;
            }
        }

        save(username, temp.getPassword(), true);
        return result;


    }

    public boolean unsuspendUser(String username){
        int i;
        boolean status = false;
        User temp = bufferedUsers.get(0);
        for (i = 0; i < bufferedUsers.size(); i++) {
            temp = bufferedUsers.get(i);
            if (temp.getUsername().equals(username)) {
                temp.lowerFlag();
                status = true;
                break;
            }
        }
        save(username, temp.getPassword(), false);
        return status;
    }

    private void save(String username, String password, boolean status){
        UserGate myGate = new UserGate();
        HashMap<String, List<Object>> oldUsers = (HashMap<String, List<Object>>) myGate.load().get(0);


        List<Object> sections = new ArrayList <Object>();
        sections.add(password);
        sections.add(status);
        oldUsers.put(username, sections);
        List<HashMap> userData = new ArrayList<HashMap>();
        userData.add(oldUsers);

        myGate.save(userData);
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


