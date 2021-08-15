package Controller;

import Interface.UserData;
import Presenter.GameTextPresenter;
import UseCase.GameUseCase;
import UseCase.GameUseCase2;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller class which allows the user to play an existing game.
 *
 * Interacts with GameUseCase, GameTextPresenter, and userData
 */

public class GamePlayController {

    private GameUseCase gameUseCase;
    private GameUseCase2 gameUseCase2;
    private GameTextPresenter gameTextPresenter = new GameTextPresenter();
    private Scanner scanner = new Scanner(System.in);
    private UserData userData;

    /**
     * Constructor method for GamePlayController
     *
     * @param gameUseCase gameUseCase class to interact with current games
     * @param userData Interface class for interacting with userData
     */

    public GamePlayController(GameUseCase gameUseCase, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
    }

    /**
     * The method which allows users to play games and select different choices.
     * Handles what should happen when different choices are made.
     *
     * Interacts with gameTextPresenter, gameUseCase, and userData to play the game
     */

    public void playGame(){
        ArrayList<String> newGames = new ArrayList<>();
        ArrayList<String> publicGames = gameUseCase.getPublicGames();
        for (String game: publicGames){
            newGames.add(game);
        }
        newGames.addAll(gameUseCase.getPrivateGames(userData.currentUser()));

        gameTextPresenter.displayScene("Enter the name of the game you want to play.", newGames);
        String gameName = String.valueOf(scanner.next());
        boolean privateGame = gameUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        boolean publicGame = gameUseCase.getPublicGames().contains(gameName);
        if (!privateGame && !publicGame){
            gameTextPresenter.displayScene("No such game! Enter anything to exit.");
            scanner.next();
            return;
        }

        ArrayList<Object> dialogueArrayList = gameUseCase2.openGame(gameName);
        Integer dialogueId = (Integer) dialogueArrayList.get(0);
        String dialogue = (String) dialogueArrayList.get(1);

        ArrayList<String> childrenChoices = gameUseCase2.getDialogueChoices(dialogueId);
        ArrayList<Integer> childrenChoiceIds = gameUseCase2.getDialogueChoiceIds(dialogueId);
        ArrayList<String> choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);

        int userChoice = 0;
        while (!childrenChoiceIds.contains(userChoice)){
            gameTextPresenter.displayScene(dialogue);
            System.out.println("Enter anything to continue: ");
            scanner.next();
            gameTextPresenter.displayScene(dialogue, choices);
            System.out.println("Enter the integer corresponding to a choice or -1 to exit: ");
            try{
                userChoice = Integer.valueOf(scanner.next());
            }

            catch(NumberFormatException e){
                System.out.println("Incorrect Input!");
                continue;
            }

            if (userChoice == -1){
                return;
            }
            else if(!childrenChoiceIds.contains(userChoice)){
                System.out.println("Incorrect Input!");
                continue;
            }

            childrenChoices = gameUseCase2.getDialogueChoices(userChoice);
            childrenChoiceIds = gameUseCase2.getDialogueChoiceIds(userChoice);
            choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);
            dialogue = gameUseCase2.getDialogueById(userChoice);
        }
    }

    private ArrayList<String> addPrefixesToStrings(ArrayList<Integer> prefixes, ArrayList<String> strings){
        for(int i = 0; i < strings.size(); i++){
            strings.set(i, prefixes.get(i) + ": " + strings.get(i));
        }

        return strings;
    }
}

