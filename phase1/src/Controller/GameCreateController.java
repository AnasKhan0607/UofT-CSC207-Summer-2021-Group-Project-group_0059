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

    public GameCreateController(GameUseCase gameUseCase){
        this.gameUseCase = gameUseCase;
    }

    public void createGame(){
        int choiceNumLimit;
        // if statement for testing purpose only
        if (this.templateData != null){
            choiceNumLimit = templateData.chooseTemplate();

        }

        else{
            choiceNumLimit = 4;
        }

    }
}
