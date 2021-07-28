package Controller;

import Gateway.GameGate;
import Interface.TemplateInteracter;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

public class GameCreateController {
    private TemplateInteracter templateInteractor;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();


    public GameCreateController(GameUseCase gameUseCase){
        this.gameUseCase = gameUseCase;
    }

    public void createGame(){
        int choiceNumLimit = templateInteractor.chooseTemplate();

    }
}
