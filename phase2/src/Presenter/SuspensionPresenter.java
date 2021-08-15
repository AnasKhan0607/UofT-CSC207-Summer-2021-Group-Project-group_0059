package Presenter;

public class SuspensionPresenter {

    public static void welcome(){
        System.out.println("Would you like to:");
        System.out.println("1. suspend a User?(cannot suspend an admin)");
        System.out.println("2. unsuspend a User?");
        System.out.println("3. quit");
        System.out.println("input the integer to indicate your choice: ");
    }

    public static void display1(){
        System.out.println("Please input the username you need to suspend:");
    }

    public static void display2(){
        System.out.println("Please input the username you need to unsuspend:");
    }

    public static void successMessage(String un){
        System.out.println(un + " has been successfully suspended");
        System.out.println();
        System.out.println();
    }

    public static void successMessage2(String un){
        System.out.println(un + " has been successfully unsuspended");
        System.out.println();
        System.out.println();
    }

    public static void errorMessage(){System.out.println("Username not found!");
        System.out.println();
        System.out.println();}
    public static void errorMessage2(){System.out.println("You cannot suspend another Admin !");
        System.out.println();
        System.out.println();}
    public static void errorChoice(){System.out.println("Invalid choice. Please try again!");
        System.out.println();
        System.out.println();}
}
