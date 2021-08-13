package Presenter;

public class SuspensionPresenter {

    public static void display1(){
        System.out.println("Please input the username you need to suspend:");
    }

    public static void display2(){
        System.out.println("Please input the username you need to unsuspend:");
    }

    public static void successMessage(String un){
        System.out.println(un + " has been successfully suspended");
    }

    public static void successMessage2(String un){
        System.out.println(un + " has been successfully unsuspended");
    }

    public static void errorMessage(){System.out.println("Username not found!");}
}
