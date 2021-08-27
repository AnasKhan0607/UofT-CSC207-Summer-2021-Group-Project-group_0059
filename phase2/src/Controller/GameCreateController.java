package Controller;

import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GamesUseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Game creation controller class which is called by GameMainController.
 */
public class GameCreateController {

    private TemplateData templateData;
    private UserData userData;
    private GamesUseCase gamesUseCase;
    private GamePresenter gamePresenter;

    /**
     * Contructor for the class.
     *
     * @param gamesUseCase A <GamesUseCase> containing current game data.
     * @param templateData A templateData interface containing current templates.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameCreateController(GamesUseCase gamesUseCase, TemplateData templateData, UserData userData, GamePresenter gamePresenter){
        this.gamesUseCase = gamesUseCase;
        this.userData = userData;
        this.templateData = templateData;
        this.gamePresenter = gamePresenter;
    }

    /**
     * Method to create new games from existing templates.
     * Interacts with the gamePresenter to communicate with the user and gamesUseCase to create games.
     */

    public void createGame(){
        List<Object> templateInfo = templateData.chooseTemplate();
        if (templateInfo.size() == 0){
            return;
        }

        int choiceNumLimit = (int) templateInfo.get(0);
        String styleSheetName = (String) templateInfo.get(1);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Game Name");
        tags.add("First Dialogue");

        List<String> inputs = gamePresenter.displayInputs(this, tags, "");
        boolean createSuccess = gamesUseCase.createGame(choiceNumLimit, inputs.get(0), userData.currentUser(), inputs.get(1), styleSheetName);
        if (createSuccess){
            gamePresenter.displayTextScene(this, "Game creation completed.");
            gamesUseCase.saveGames();
        }
        else{
            gamePresenter.displayTextScene(this, "Game creation failed. A game with that name already exist.");
        }
    }
}
