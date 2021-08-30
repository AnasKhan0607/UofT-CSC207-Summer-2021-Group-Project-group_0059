package Controller;

import Gateway.GameGate;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GamesUseCase;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class which allows the user to play an existing game.
 *
 * Interacts with GamesUseCase, GameTextPresenter, and userData
 *
 * @author Daniel Liu
 */

public class GamePlayController {

    private final GamesUseCase gamesUseCase;
    private final GameUseCase gameUseCase = new GameUseCase();
    private final GamePresenter gamePresenter;
    private final UserData userData;

    /**
     * Constructor method for GamePlayController
     *
     * @param gamesUseCase gamesUseCase class to interact with current games
     * @param userData Interface class for interacting with userData
     */

    public GamePlayController(GamesUseCase gamesUseCase, UserData userData, GamePresenter gamePresenter){
        this.gamesUseCase = gamesUseCase;
        this.userData = userData;
        this.gamePresenter = gamePresenter;
    }

    // for the purpose of playing games from message attachments only

    /**
     * A class that lets the user play a game
     * @param userData the userData which provides the user name
     * @param gamePresenter the gui class
     */
    public GamePlayController(UserData userData, GamePresenter gamePresenter){
        this.gamesUseCase = new GamesUseCase(new GameGate());
        this.userData = userData;
        this.gamePresenter = gamePresenter;
    }

    /**
     * The method which allows users to play games and select different choices.
     * Handles what should happen when different choices are made.
     *
     * Interacts with gameTextPresenter, gamesUseCase, and userData to play the game
     */
    public void playGame(){
        presentAvailableGames();

        List<String> tags = new ArrayList<>();
        tags.add("Game Name");
        String gameName = gamePresenter.displayInputs(this, tags,
                "Enter the name of the game you want to play.").get(0);

        if (checkGameExistense(gameName)) return;

        ArrayList<Object> dialogueArrayList = gameUseCase.openGame(gamesUseCase, gameName);
        Integer dialogueId = (Integer) dialogueArrayList.get(0);
        String dialogue = (String) dialogueArrayList.get(1);

        ArrayList<String> childrenChoices = gameUseCase.getDialogueChoices(dialogueId);
        ArrayList<Integer> childrenChoiceIds = gameUseCase.getDialogueChoiceIds(dialogueId);
        ArrayList<String> choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);

        presentGame(dialogue, childrenChoiceIds, choices);
    }

    /**
     * Plays and presents a game with the name gameName
     * @param gameName the name of a game
     */
    public void playSpecificGame(String gameName){
        if (checkGameExistense(gameName)) return;

        ArrayList<Object> dialogueArrayList = gameUseCase.openGame(gamesUseCase, gameName);
        Integer dialogueId = (Integer) dialogueArrayList.get(0);
        String dialogue = (String) dialogueArrayList.get(1);

        ArrayList<String> childrenChoices = gameUseCase.getDialogueChoices(dialogueId);
        ArrayList<Integer> childrenChoiceIds = gameUseCase.getDialogueChoiceIds(dialogueId);
        ArrayList<String> choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);

        presentGame(dialogue, childrenChoiceIds, choices);
    }

    private boolean checkGameExistense(String gameName) {
        boolean privateGame;
        if (userData.currentUser().startsWith("Admin_")){
            privateGame = gamesUseCase.getPrivateGames().contains(gameName);

        }
        else{
            privateGame = gamesUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        }
        boolean publicGame = gamesUseCase.getPublicGames().contains(gameName);
        if (!privateGame && !publicGame){
            gamePresenter.displayTextScene(this, "No such game!");
            return true;
        }
        return false;
    }

    private void presentAvailableGames() {
        List<String> newGames = new ArrayList<>(gamesUseCase.getPublicGames());
        if (userData.currentUser().startsWith("Admin_")){
            newGames.addAll(gamesUseCase.getPrivateGames());
        }
        else{
            newGames.addAll(gamesUseCase.getPrivateGames(userData.currentUser()));
        }
        gamePresenter.displayList(this, newGames, "This is the list of available games.");
    }

    private void presentGame(String dialogue, ArrayList<Integer> childrenChoiceIds, ArrayList<String> choices) {
        gamePresenter.setStyleSheet(gameUseCase.getStyleSheetName());
        ArrayList<String> childrenChoices;
        int userChoice = 0;
        while (!childrenChoiceIds.contains(userChoice)){
            String d = dialogue.split("#;")[1];
            gamePresenter.displayTextScene(this, d);
            if (childrenChoiceIds.size() == 0){ break; }
            else if(childrenChoiceIds.size() == 1){
                userChoice = childrenChoiceIds.get(0);
            }
            else{
                ArrayList<String> a = new ArrayList<String>();
                for (String choice : choices){
                    String c = choice.split("#;")[0];
                    a.add(c);
                }
                userChoice = childrenChoiceIds.get(gamePresenter.displayChoices(this, a, d));
            }

            if(!childrenChoiceIds.contains(userChoice)){
                System.out.println(userChoice + " " + childrenChoiceIds);
                gamePresenter.displayTextScene(this, "Incorrect Input!");
                continue;
            }

            childrenChoices = gameUseCase.getDialogueChoices(userChoice);
            childrenChoiceIds = gameUseCase.getDialogueChoiceIds(userChoice);
            choices = addPrefixesToStrings(childrenChoiceIds, childrenChoices);
            dialogue = gameUseCase.getDialogueById(userChoice);
        }
        gamePresenter.setStyleSheet("");
    }

    private ArrayList<String> addPrefixesToStrings(ArrayList<Integer> prefixes, ArrayList<String> strings){
        for(int i = 0; i < strings.size(); i++){
            strings.set(i, prefixes.get(i) + ": " + strings.get(i));
        }

        return strings;
    }
}

