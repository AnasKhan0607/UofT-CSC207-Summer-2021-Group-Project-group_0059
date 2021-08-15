package Presenter;

public class SuspensionPresenter {

    public static void display1(){
        System.out.println("Please input the username you need to suspend:");
    }

    public static void successMessage(String un){
        System.out.println(un + " has been successfully suspended");
    }
}
