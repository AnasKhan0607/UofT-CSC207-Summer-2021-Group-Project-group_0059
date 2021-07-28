package Controller;

import Presenter.GamePresenter;
import UseCase.GameUseCase;

public class GamePlayController {
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();


    public GamePlayController(GameUseCase gameUseCase){
        this.gameUseCase = gameUseCase;
    }

    public void playGame(String gameName){

    }
}

