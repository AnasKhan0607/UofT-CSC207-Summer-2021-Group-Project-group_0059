package Controller;

import Gateway.GameGate;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameMainController {
    public static void main(String[] args) {
        GameMainController gameController = new GameMainController();
        gameController.regularGameMenu();
        gameController.guestViewsGame();
    }

    private GameUseCase gameUseCase;
    private GameCreateController gameCreator;
    private GamePlayController gamePlayer;
    private GameEditController gameEditor;
    private GamePresenter gamePresenter = new GamePresenter();

    public GameMainController(){
        gameUseCase = new GameUseCase(new GameGate());
        gameCreator = new GameCreateController(gameUseCase);
        gameEditor = new GameEditController(gameUseCase);
        gamePlayer = new GamePlayController(gameUseCase);
    }

    // This game menu is for regular/admin users
    public void regularGameMenu(){
        gamePresenter.displayScene("Choose and enter the corresponding integer.",
                new ArrayList<>(Arrays.asList(new String[]{
                "1: Create Game", "2: Edit Game", "3: Play Game", "4: View Games"})));
        Scanner scanner = new Scanner(System.in);

        int userName;
        while (true){
            try{
                userName = Integer.valueOf(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }
        }
        switch (userName){
            case 1:{
                System.out.println("bruh");
            }
            case 2:{
                System.out.println("bruh");
            }
            case 3:{
                System.out.println("bruh");
            }
            case 4:{
                System.out.println("bruh");
            }
        }

        scanner.close();
    }


    // This game menu is for guest users
    public void guestGameMenu(){
        gamePresenter.displayScene("Choose and enter the corresponding integer.",
                new ArrayList<>(Arrays.asList(new String[]{"1: Play Game", "2: View Games"})));
        Scanner scanner = new Scanner(System.in);

        int userName;
        while (true){
            try{
                userName = Integer.valueOf(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }
        }
        switch (userName){
            case 1:{
                System.out.println("bruh");
            }
            case 2:{
                System.out.println("bruh");
            }
        }

        scanner.close();
    }

    private void guestViewsGame(){
        ArrayList<String> publicGames = gameUseCase.getPublicGames();
        publicGames.add("bruh");
        gamePresenter.displayScene("Enter 1 to exist.", publicGames);
    }
}
