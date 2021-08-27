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











    public static void errorMessageID(String id){
        System.out.println("Message " +id+ " does not exist!");
        System.out.println();
        System.out.println();
    }



    public static void deleteMessageSuccess(String id){
        System.out.println("Message  " + id + " has been successfully deleted.");
        System.out.println();
        System.out.println();
    }

}
