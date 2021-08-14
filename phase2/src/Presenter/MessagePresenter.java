package Presenter;

import Entity.Message;

public class MessagePresenter {
    public static void welcomingMessage(String username){
        System.out.println(username + "'s messagebox:");
    }


    public static void printBoxup(String un){
        System.out.println("===================================================================");
        System.out.println("                " +un + "'s Message Box");
    }

    public static void printBoxDown(){
        System.out.println("===================================================================");
    }

    public static void printMessage(Message currentMessage){

        System.out.print(currentMessage.getid()+ " ");
        System.out.print(currentMessage.getTime() + " ");
        if (currentMessage.getStatus()){
            System.out.print(" READ ");
        } else {
            System.out.print(" UNREAD ");
        }

        System.out.print(currentMessage.getFrom() + "> ");
        System.out.println(currentMessage.getMsg());
    }

    public static void writeMessagePrompt1(){
        System.out.println("Please input the name of the receiver, type EVERYONE to send the same message" +
                "to every user recorded in the system(for use of ADMIN ONLY), then press ENTER to continue:");
    }

    public static void writeMessagePrompt2(){
        System.out.println("Please input the message, then press ENTER to continue:");


    }

    public static void errorMessage(){
        System.out.println("Username does not exist!");
    }

    public static void errorMessageNotAdmin(){System.out.println("Since you are not an admin," +
            "you are not authorized to do that");}

    public static void writeMessageSuccess(String rec){
        System.out.println("Message successfully composed and sent to " + rec + " .");
    }

}
