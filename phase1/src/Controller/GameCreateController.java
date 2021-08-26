package Controller;

import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;
import java.util.Scanner;

/**
 * Game creation controller class which is called by GameMainController.
 */
public class GameCreateController {
    private TemplateData templateData;
    private UserData userData;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Contructor for the class.
     *
     * @param gameUseCase A <GamesUseCase> containing current game data.
     * @param templateData A templateData interface containing current templates.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameCreateController(GameUseCase gameUseCase, TemplateData templateData, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
        this.templateData = templateData;
    }

    /**
     * Method to create new games from existing templates.
     * Interacts with the gamePresenter to communicate with the user and gameUseCase to create games.
     */

    public void createGame(){
        int choiceNumLimit = templateData.chooseTemplate();
        if (choiceNumLimit == -1){
            return;
        }
        gamePresenter.displayScene("Please enter the game name.");
        String gameName = String.valueOf(scanner.nextLine());
        gamePresenter.displayScene("Please enter the first dialogue.");
        String initialDialogue = String.valueOf(scanner.nextLine());
        gameUseCase.createGame(choiceNumLimit, gameName, userData.currentUser(), initialDialogue);
        gamePresenter.displayScene("Game creation complete. Enter anything to continue.");
        scanner.nextLine();
        gameUseCase.saveGames();
    }

    public void closeScanner(){
        scanner.close();
    }
}
