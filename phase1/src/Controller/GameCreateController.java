package Controller;

import Interface.TemplateData;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

public class GameCreateController {
    private TemplateData templateData;
    private UserData userData;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();

    public GameCreateController(GameUseCase gameUseCase, TemplateData templateData){
        this.gameUseCase = gameUseCase;
        this.templateData = templateData;
    }

    public GameCreateController(GameUseCase gameUseCase, TemplateData templateData, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
        this.templateData = templateData;
    }

    public void createGame(){
        int choiceNumLimit = templateData.chooseTemplate();
        String gameName = "";
        String initialDialogue = "";
        gameUseCase.createGame(choiceNumLimit, gameName, userData.currentUserName(), initialDialogue);



    }
}
