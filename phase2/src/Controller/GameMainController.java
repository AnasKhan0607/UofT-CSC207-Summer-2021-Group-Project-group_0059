package Controller;

import Gateway.GameGate;
import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The main Controller class for games. Enables users to Create, Edit, and View games.
 */

public class GameMainController {
    public static void main(String[] args) {

//        GameMainController gameController = new GameMainController(new TemplateEditorController(), new RegularUserNavigatorController("Daniel Liu"));
//        gameController.gameMenu();
    }

    private UserData userData;
    private GameUseCase gameUseCase;
    private GameCreateController gameCreator;
    private GamePlayController gamePlayer;
    private GameEditController gameEditor;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);

    public GameMainController(TemplateData templateData, UserData userData){
        gameUseCase = new GameUseCase(new GameGate());
        gameCreator = new GameCreateController(gameUseCase, templateData, userData);
        gameEditor = new GameEditController(gameUseCase, userData);
        gamePlayer = new GamePlayController(gameUseCase, userData);
        this.userData = userData;
    }

    /**
     * The game's menu which takes user input and calls the respective controller for their choice.
     *
     * Interacts with gamePresenter, gameCreator, gameEditor, and gamePlayer.
     */

    public void gameMenu(){
        int userChoice = 0;
        while (true){
            gamePresenter.displayScene(
                    "Choose and enter the corresponding integer. Notice: Games are auto saved " +
                            "unless you are a guest user.",
                    new ArrayList<>(Arrays.asList(new String[]{
                            "1: Create Game", "2: Edit Game", "3: Play Game", "4: View Games", "5: Exit"})));

            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
                continue;
            }

            if(userChoice == 1){
                gameCreator.createGame();
            }
            else if(userChoice == 2){
                gameEditor.editGame();
            }
            else if(userChoice == 3){
                gamePlayer.playGame();
            }
            else if(userChoice == 4){
                this.viewGamesMenu();
            }
            else if(userChoice == 5){
                break;
            }
        }
    }
    public void gameMenuAdminUser(){
        int userChoice = 0;
        while (true){
            gamePresenter.displayScene(
                    "Choose and enter the corresponding integer. Notice: Games are auto saved " +
                            "unless you are a guest user.",
                    new ArrayList<>(Arrays.asList(new String[]{
                            "1: Create Game", "2: Edit or Delete Game", "3: Play Game", "4: View Games", "5: Exit"})));

            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
                continue;
            }

            if(userChoice == 1){
                gameCreator.createGame();
            }
            else if(userChoice == 2){
                gameEditor.editGameAdminUser();
            }
            else if(userChoice == 3){
                gamePlayer.playGame();
            }
            else if(userChoice == 4){
                this.viewGamesMenuAdminUser();
            }
            else if(userChoice == 5){
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
                            "4: View Specific Game Structure",
                            "5: Exit"})));
            try {
                userChoice = Integer.valueOf(scanner.next());
            }
            catch (NumberFormatException e) {
                System.out.println(e);
                continue;
            }

            if (userChoice == 1){
                this.viewGames(gameUseCase.getPrivateGames(userData.currentUser()));
            }
            else if (userChoice == 2){
                this.viewGames(gameUseCase.getPublicGames());
            }
            else if (userChoice == 3){
                this.viewGames(gameUseCase.getPublicGamesByAuthor(userData.currentUser()));
            }
            else if (userChoice == 4){
                this.viewGame();
            }
            else if (userChoice == 5){
                break;
            }
        }
    }
    private void viewGamesMenuAdminUser(){

        int userChoice = 0;
        while (true) {
            gamePresenter.displayScene("Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{"1: View Private Games (Created by You)",
                            "2: View Public Games", "3: View All Public Games Created by You",
                            "4: View Specific Game Structure", "5: view Private Games",
                            "6: Exit"})));
            try {
                userChoice = Integer.valueOf(scanner.next());
            }
            catch (NumberFormatException e) {
                System.out.println(e);
                continue;
            }

            if (userChoice == 1){
                this.viewGames(gameUseCase.getPrivateGames(userData.currentUser()));
            }
            else if (userChoice == 2){
                this.viewGames(gameUseCase.getPublicGames());
            }
            else if (userChoice == 3){
                this.viewGames(gameUseCase.getPublicGamesByAuthor(userData.currentUser()));
            }
            else if (userChoice == 4){
                this.viewGame();
            }
            else if(userChoice == 5){
                this.viewGames(gameUseCase.getAllPrivateGames());
            }
            else if (userChoice == 6){
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
                continue;
            }

            if (userChoice == 1){
                break;
            }
        }
    }

    private void viewGame(){
        gamePresenter.displayScene("Enter the name of the game you want to view.");
        String gameName = String.valueOf(scanner.next());

        boolean privateGame = gameUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        boolean publicGame = gameUseCase.getPublicGames().contains(gameName);
        if (privateGame || publicGame){
            gameUseCase.openGame(gameName);
            gamePresenter.displayScene("Enter anything to exit.", gameUseCase.getGameAsString(gameName));
            scanner.next();
        }
        else{
            gamePresenter.displayScene("No such game! Enter anything to exit.");
            scanner.next();
        }
    }

}
