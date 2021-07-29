package Controller;

import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GamePlayController {

    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);

    public GamePlayController(GameUseCase gameUseCase){
        this.gameUseCase = gameUseCase;
    }

    public void playGame(String gameName){
        ArrayList<Object> dialogueArrayList = gameUseCase.openGame(gameName);
        Integer dialogueId = (Integer) dialogueArrayList.get(0);
        String dialogue = (String) dialogueArrayList.get(1);
        ArrayList<String> childrenChoices = gameUseCase.getDialogueChoices(dialogue);
        ArrayList<Integer> childrenChoiceIds = gameUseCase.getDialogueChoiceIds(dialogue);
        ArrayList<String> choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);

        gamePresenter.displayScene(dialogue);
        gamePresenter.displayScene(dialogue + " (Enter the integer corresponding " +
                "to a choice to confirm choosing that action)", choices);

        int userChoice = 0;
        while (!childrenChoiceIds.contains(userChoice)){
            userChoice = Integer.valueOf(scanner.next());
        }

    }

    private ArrayList<String> addPrefixesToStrings(ArrayList<Integer> prefixes, ArrayList<String> strings){
        return null;
    }
}

