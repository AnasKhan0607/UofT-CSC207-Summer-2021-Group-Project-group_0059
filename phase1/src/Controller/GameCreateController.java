package Controller;

import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;
import java.util.Scanner;

public class GameCreateController {
    private TemplateData templateData;
    private UserData userData;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);

    public GameCreateController(GameUseCase gameUseCase, TemplateData templateData, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
        this.templateData = templateData;
    }

    public void createGame(){
        int choiceNumLimit = templateData.chooseTemplate();
        gamePresenter.displayScene("Please enter the game name.");
        String gameName = String.valueOf(scanner.nextLine());
        gamePresenter.displayScene("Please enter the first dialogue.");
        String initialDialogue = String.valueOf(scanner.nextLine());
        gameUseCase.createGame(choiceNumLimit, gameName, userData.currentUser(), initialDialogue);
        gamePresenter.displayScene("Game creation complete. Enter anything to continue.");
        scanner.nextLine();
        gameUseCase.saveGames();
    }
}
