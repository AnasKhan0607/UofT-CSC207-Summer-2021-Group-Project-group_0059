package UseCase;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.TempUser;
import Entity.User;
import Gateway.UserGate;
import Interface.LoadSave;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The use case class for the users.
 * @author Ahmad I., Ruilin P.
 */
public class UserManager {
    private List<User> bufferedUsers;
    private LoadSave myGate;

    /**
     * Creates a list of users using the UserGate class. and for every suspended user, checks his/her suspension end date that if it's today and decide whether to unsuspend automatically
     */
    public UserManager(LoadSave gate){
        this.bufferedUsers = new ArrayList<>();
        this.myGate = gate;
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
        if (newPassword.length() < 8){
            return false;
        }

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
     * return all the users in record right now
     * @return a List of all existing Users
     */
    public List<User> getBufferedUsers() {
        return bufferedUsers;
    }

    /**
     * validate the username and password given with what's in the record(given that there's a corresponding username)
     * @param username the username we are validating
     * @param passwordInput the password given
     * @return a boolean if the password given matches the record
     */
    public boolean validatePassword(String username, String passwordInput){
        return SearchUser(username).getPassword().equals(passwordInput);
    }

    /**
     * check if the given username is suspended
     * @param username the username to be checked
     * @return a boolean indicating if the username is suspended
     */
    public boolean validateFlag(String username){
        return SearchUser(username).getflag();
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
     * reset the given username's password to a temporal password(randomly generated) and generate a text file of it
     * @param username the username who needs to be performed with this action
     * @return a boolean indicating if the action is successful
     */
    public boolean resetTempPassword(String username){
        if (SearchUser(username) == null){
            return false;
        }
        //https://www.baeldung.com/java-random-string
        byte[] array = new byte[8]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedPassword = new String(array, Charset.forName("UTF-8"));
        resetPassword(username, generatedPassword);
        try {
            FileWriter myWriter = new FileWriter(username +".txt");
            myWriter.write(generatedPassword);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return true;

    }

    public static boolean PasswordStrength(String password) {
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

    /**
     * save the user to file
     * @param user the User object to be saved
     * @param status if the User's suspended
     * @param suspensionEndTime the time when user's suspension is over(null if not suspended)
     */
    private void save(User user, boolean status, LocalDate suspensionEndTime){
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


