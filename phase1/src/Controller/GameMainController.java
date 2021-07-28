package Controller;

import Gateway.GameGate;
import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameMainController {
    public static void main(String[] args) {
//        GameMainController gameController = new GameMainController();
//        gameController.regularGameMenu();
//        gameController.guestViewsGame();
//        gameController.guestGameMenu();
    }

    private UserData userData;
    private GameUseCase gameUseCase;
    private GameCreateController gameCreator;
    private GamePlayController gamePlayer;
    private GameEditController gameEditor;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);


    public GameMainController(TemplateData templateData){
        gameUseCase = new GameUseCase(new GameGate());
        gameCreator = new GameCreateController(gameUseCase, templateData);
        gameEditor = new GameEditController(gameUseCase);
        gamePlayer = new GamePlayController(gameUseCase);
    }

    public GameMainController(TemplateData templateData, UserData userData){
        gameUseCase = new GameUseCase(new GameGate());
        gameCreator = new GameCreateController(gameUseCase, templateData, userData);
        gameEditor = new GameEditController(gameUseCase, userData);
        gamePlayer = new GamePlayController(gameUseCase, userData);
        this.userData = userData;
    }

    public void gameMenu(){
        if (userData == null){
            guestGameMenu();
        }
        else{
            regularGameMenu();
        }
    }


    // This game menu is for regular/admin users
    private void regularGameMenu(){
        gamePresenter.displayScene("Choose and enter the corresponding integer.",
                new ArrayList<>(Arrays.asList(new String[]{
                "1: Create Game", "2: Edit Game", "3: Play Game", "4: View Games", "5: Exit"})));

        int userChoice = 0;
        while (true){
            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }

            if(userChoice == 1){
                System.out.println("bruh");
            }
            else if(userChoice == 2){
                System.out.println("?");
            }
            else if(userChoice == 3){
//                    this.viewGames(gameUseCase.getPublicGames());
            }
            else if(userChoice == 4){
                this.viewGamesMenu();
            }
            else if(userChoice == 5){
                break;
            }
        }
    }

    // This game menu is for guest users
    private void guestGameMenu(){

        int userChoice = 0;
        while (true){
            gamePresenter.displayScene("Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{"1: Create Game (Unsavable)",
                            "2: Play Game", "3: View Games", "4: Exit"})));
            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }

            if(userChoice == 1){
                System.out.println("bruh");
            }
            else if(userChoice == 2){
                System.out.println("?");
            }
            else if(userChoice == 3){
                this.viewGames(gameUseCase.getPublicGames());
            }
            else if(userChoice == 4){
                break;
            }
        }
    }

    private void viewGamesMenu(){

        int userChoice = 0;
        while (true) {
            gamePresenter.displayScene("Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{"1: View Private Games (Created by You)",
                            "2: View Public Games", "3: View All Public Games Created by You",
                            "4: Exit"})));
            try {
                userChoice = Integer.valueOf(scanner.next());
            }
            catch (NumberFormatException e) {
                System.out.println(e);
            }

            if (userChoice == 1){
                this.viewGames(gameUseCase.getPrivateGames(userData.currentUserName()));
            }
            else if (userChoice == 2){
                this.viewGames(gameUseCase.getPublicGames());
            }
            else if (userChoice == 3){
                this.viewGames(gameUseCase.getPublicGamesByAuthor(userData.currentUserName()));
            }
            else if (userChoice == 4){
                break;
            }
        }
    }

    private void viewGames(ArrayList<String> games){
        int userChoice = 0;
        while (true) {
            gamePresenter.displayScene("Enter 1 to exit.", games);
            try {
                userChoice = Integer.valueOf(scanner.next());
            }
            catch (NumberFormatException e) {
                System.out.println(e);
            }

            if (userChoice == 1){
                break;
            }
        }
    }


}
