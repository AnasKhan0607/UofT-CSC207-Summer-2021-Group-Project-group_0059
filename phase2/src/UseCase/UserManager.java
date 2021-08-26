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
     * Creates a list of users using the UserGate class. and for every suspended user, checks his/her suspension end date that if it's today and decide whether to unsuspend automatically
     */
    public UserManager(){
        this.bufferedUsers = new ArrayList<>();

        UserGate myGate = new UserGate();
        HashMap<String, List<Object>> tempUsers = (HashMap<String, List<Object>>) myGate.load().get(0);

        for (Map.Entry <String, List<Object>> mapElement :tempUsers.entrySet()){
            String username = mapElement.getKey();
            List lst = mapElement.getValue();
            String password = (String)lst.get(0);
            boolean suspension = (boolean) lst.get(1);



            if (username.startsWith("Admin_")){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
            } else if (username.startsWith("Temp_")) {
                LocalDate startDate = (LocalDate) lst.get(3); // Temp have their account creation/expiry dates stored
                LocalDate endDate = (LocalDate) lst.get(4);
                TempUser tempUser = new TempUser(username, password, startDate, endDate); // expired accounts will be unable to login
                this.bufferedUsers.add(tempUser);
                if (suspension) {
                    tempUser.raiseFlag();
                    tempUser.setsuspensionEndTime((LocalDate) lst.get(2));
                }
            } else {
                RegularUser tempUser = new RegularUser(username, password);
                if (suspension) {
                    tempUser.raiseFlag();
                    tempUser.setsuspensionEndTime((LocalDate) lst.get(2));
                }
                this.bufferedUsers.add(tempUser);
            }

                if (suspension){
                    LocalDate suspensionEndTime = (LocalDate) lst.get(2);
                    if (suspensionEndTime.equals(LocalDate.now())){
                        unsuspendUser(username);
                    } else if (suspensionEndTime == null){
                        unsuspendUser(username);
                    }
                }
        }
        /*buffered array gets updated with what's in tempUsers*/
    }

    /**
     * reset the password of given user(Name)
     * @param userName the username requested to be reset of password
     * @param newPassword the desired new password
     * @return boolean whether the action is successful
     */
    public boolean resetPassword(String userName, String newPassword){
        User temp = SearchUser(userName);
        if (temp != null){
            if (temp.getUsername().startsWith("Admin_")){
                ((AdminUser) temp).setPassword(newPassword);
            } else if (temp.getUsername().startsWith("Temp_")){
                ((TempUser) temp).setPassword(newPassword);

            } else {
                ((RegularUser) temp).setPassword(newPassword);
            }
            save(temp, false, null);
            return true;
        }
        return false;

    }

    /**
     * Adds a user to the list of users in UserManager and in the file.
     * @param info Contains the user's username and password.
     */
    public void addUser(ArrayList<String> info){
        if (info.size() == 2){
            String username = info.get(0);
            String password = info.get(1);
            if (username.startsWith("Admin_")){
                AdminUser tempUser = new AdminUser(username, password);
                this.bufferedUsers.add(tempUser);
                save(tempUser, false, null);
            } else if (username.startsWith("Temp_")) {
                LocalDate startDate = LocalDate.now();
                LocalDate endDate = LocalDate.now().plusDays(30);
                TempUser tempUser = new TempUser (username, password, startDate, endDate);
                this.bufferedUsers.add(tempUser);
                save(tempUser, false, null);
            }else {
                RegularUser tempUser = new RegularUser(username, password);
                this.bufferedUsers.add(tempUser);
                save(tempUser, false, null);
            }
        } else {
            String username = "Guest";
            GuestUser tempUser = new GuestUser(username);
            this.bufferedUsers.add(tempUser);
        }
    }

    /**
     * Checks if a user exists with the given username.
     * @param username The user's username.
     * @return The user object that the username corresponds to, or null if user not found.
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

    /**
     * return all the users in record right now
     * @return a List of all existing Users
     */
    public List<User> getBufferedUsers() {
        return bufferedUsers;
    }


    /**
     * suspend a User
     * @param username the user to be suspended
     * @param x the number of days to be suspended
     * @return a boolean meaning if the action is successful
     */
    public boolean suspendUser(String username, int x){
        int i;
        boolean result = false;
        User temp;
        LocalDate suspensionEndTime;
        for (i = 0; i < bufferedUsers.size(); i++) {
            temp = bufferedUsers.get(i);
            if (temp.getUsername().equals(username)) {
               temp.raiseFlag();

               suspensionEndTime = LocalDate.now().plusDays(x);
               temp.setsuspensionEndTime(suspensionEndTime);
               result = true;
               save(temp, true, suspensionEndTime);
               break;
            }
        }


        return result;


    }

    /**
     * unsuspend given user
     * @param username the user to be unsuspended
     * @return boolean of if action is successful
     */
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
        save(temp, false, null);
        return status;
    }

    /**
     * save the user to file
     * @param user the User object to be saved
     * @param status if the User's suspended
     * @param suspensionEndTime the time when user's suspension is over(null if not suspended)
     */
    private void save(User user, boolean status, LocalDate suspensionEndTime){
        UserGate myGate = new UserGate();
        HashMap<String, List<Object>> oldUsers = (HashMap<String, List<Object>>) myGate.load().get(0);


        List<Object> sections = new ArrayList <Object>();
        sections.add(user.getPassword());
        sections.add(status);
        sections.add(suspensionEndTime);
        String username = user.getUsername();
        if (username.startsWith("Temp_")) {
            LocalDate startDate = ((TempUser) user).getStartDate();
            LocalDate endDate = ((TempUser) user).getEndDate();
            sections.add(startDate);
            sections.add(endDate);
        }
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


