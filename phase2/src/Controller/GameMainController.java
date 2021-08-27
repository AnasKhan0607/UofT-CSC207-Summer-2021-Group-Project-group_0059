package Controller;

import Gateway.GameGate;
import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import Presenter.GameTextPresenter;
import UseCase.GamesUseCase;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * The main Controller class for games. Enables users to Create, Edit, and View games.
 *
 * @author Daniel Liu
 */

public class GameMainController {
    public static void main(String[] args) {

//        GameMainController gameController = new GameMainController(new TemplateEditorController(), new RegularUserNavigatorController("Daniel Liu"));
//        gameController.gameMenu();
    }

    private UserData userData;
    private GamesUseCase gamesUseCase;
    private GameUseCase gameUseCase = new GameUseCase();
    private GameCreateController gameCreator;
    public GamePlayController gamePlayer;
    private GameEditController gameEditor;
    private GamePresenter gamePresenter = new GamePresenter();

    public GameMainController(TemplateData templateData, UserData userData){
        gamesUseCase = new GamesUseCase(new GameGate());
        gameCreator = new GameCreateController(gamesUseCase, templateData, userData, gamePresenter);
        gameEditor = new GameEditController(gamesUseCase, userData, gamePresenter);
        gamePlayer = new GamePlayController(gamesUseCase, userData, gamePresenter);
        this.userData = userData;
    }

    /**
     * The game's menu which takes user input and calls the respective controller for their choice.
     *
     * Interacts with gameTextPresenter, gameCreator, gameEditor, and gamePlayer.
     */

    public void gameMenu(){
        List<String> choices = new ArrayList<>();
        choices.add("Create Game");
        choices.add("Edit Game");
        choices.add("Play Game");
        choices.add("View Game");
        choices.add("Exit");
        while (true){
            int userChoice = gamePresenter.displayChoices(this, choices);

            if(userChoice == 0){
                gameCreator.createGame();
            }
            else if(userChoice == 1){
                gameEditor.editGame();
            }
            else if(userChoice == 2){
                gamePlayer.playGame();
            }
            else if(userChoice == 3){
                this.viewGamesMenu();
            }
            else if(userChoice == 4){
                break;
            }
        }
    }

    private void viewGamesMenu(){
        List<String> choices = new ArrayList<>();
        choices.add("View Private Games");
        choices.add("View Public Games");
        choices.add("View Specific Game Structure");
        choices.add("Exit");
        while (true) {
            int userChoice = gamePresenter.displayChoices(this, choices);

            if (userChoice == 0){
                if (userData.currentUser().startsWith("Admin_")){
                    gamePresenter.displayList(this, gamesUseCase.getPrivateGames());
                }
                else{
                    gamePresenter.displayList(this, gamesUseCase.getPrivateGames(userData.currentUser()));
                }
            }
            else if (userChoice == 1){
                gamePresenter.displayList(this, gamesUseCase.getPublicGames());
            }
            else if (userChoice == 2){
                this.viewGame();
            }
            else if (userChoice == 3){
                break;
            }
        }
    }

    private void viewGame(){
        List<String> tags = new ArrayList<>();
        tags.add("Game Name");
        String gameName = gamePresenter.displayInputs(this, tags,
                "Enter the name of the game you want to view.").get(0);

        boolean privateGame;
        if (userData.currentUser().startsWith("Admin_")){
            privateGame = gamesUseCase.getPrivateGames().contains(gameName);
        }
        else{
            privateGame = gamesUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        }
        boolean publicGame = gamesUseCase.getPublicGames().contains(gameName);
        if (privateGame || publicGame){
            gameUseCase.openGame(gamesUseCase, gameName);
            gamePresenter.displayTextScene(this, "This is the structure of the game.",
                    gamesUseCase.getGameAsString(gameName));
        }
        else{
            gamePresenter.displayTextScene(this, "No such game!");
        }
    }
}
