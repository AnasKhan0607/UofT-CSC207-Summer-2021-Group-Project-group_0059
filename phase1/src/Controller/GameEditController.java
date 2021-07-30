package Controller;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class GameEditController {

    private UserData userData;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);
    private final String editGameDialogueChoices = "Enter r to return to the parent dialogue, " +
            "v + id to view the dialogue with that id (e.g. v1), " +
            "c + id to change a game dialogue, " +
            "a + id to add a dialogue, d + id to delete a dialogue, " +
            "and e to exit.";


    public GameEditController(GameUseCase gameUseCase, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
    }

    public void editGame(){
        gamePresenter.displayScene("Enter the name of the game you want to edit.");
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRight(gameName)){ return; }
        ArrayList<Object> initialIdAndDialogue = gameUseCase.openGame(gameName);

        int userChoice = 0;
        while (true){
            gamePresenter.displayScene(
                    "Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{
                            "1: Make Game Public", "2: Edit Game Dialogues", "3: Exit and Save"})));

            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }

            if(userChoice == 1){
                gameUseCase.changeGameState(gameName);
                break;
            }
            else if(userChoice == 2){
                editGameDialogues(gameName, (String) initialIdAndDialogue.get(1), (int) initialIdAndDialogue.get(0));
            }
            else if(userChoice == 3){
                gameUseCase.saveGames();
                break;
            }
        }
    }

    private boolean verifyEditGameRight(String gameName){
        boolean privateGameByUser = gameUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        boolean publicGameByUser = gameUseCase.getPublicGamesByAuthor(userData.currentUser()).contains(gameName);
        if(!privateGameByUser && publicGameByUser){
            int userChoice = 0;
            while(true){
                gamePresenter.displayScene("You created this game, but it must be private to edit! " +
                        "Make it private? Enter 1 to make it private, enter 2 to cancel this edit request.");
                try{
                    userChoice = Integer.valueOf(scanner.next());
                }
                catch(NumberFormatException e){
                    System.out.println(e);
                }

                if(userChoice == 1){
                    gameUseCase.changeGameState(gameName);
                    return true;
                }
                else if(userChoice == 2){
                    return false;
                }
            }
        }
        else if(!privateGameByUser && !publicGameByUser){
            gamePresenter.displayScene("You did not create this game! Enter anything to exit.");
            scanner.next();
            return false;
        }
        return true;
    }

    private void editGameDialogues(String gameName, String currentDialogue, int currentId){
        while (true) {
            gamePresenter.displayScene("Dialogue ID " + currentId + ": " + currentDialogue +
                            " (Enter anything to continue)",
                    gameUseCase.getGameAsString(gameName, 69, currentId));
            scanner.next();
            gamePresenter.displayScene(this.editGameDialogueChoices,
                    gameUseCase.getGameAsString(gameName, 69, currentId));
            String userChoice = String.valueOf(scanner.next());

            ArrayList<Object> actionAndId = this.EGDVerifyUserChoice(userChoice);
            if (actionAndId.size() == 0){
                System.out.println("Wrong input.");
                continue;
            }
            char action = (char) actionAndId.get(0);
            int id = 0;
            if(actionAndId.size() == 2){
                id = (int) actionAndId.get(1);
            }

            switch (action){
                case 'r':
                    int parentId = gameUseCase.getParentDialogueId(currentId);
                    if (parentId != -1){
                        this.editGameDialogues(gameName, gameUseCase.getDialogueById(parentId), parentId);
                        return;
                    }
                    break;
                case 'v':
                    this.editGameDialogues(gameName, gameUseCase.getDialogueById(id), id);
                    return;
                case 'c':
                    this.changeDialogue(gameName, id, currentId);
                    break;
                case 'a':
                    this.addDialogue(gameName, id, currentId);
                    break;
                case 'd':
                    if (!gameUseCase.deleteDialogueById(id)){
                        System.out.println("You cannot delete the first dialogue of the game!");
                    }
                    break;
                case 'e':
                    return;
            }
        }
    }

    private ArrayList<Object> EGDVerifyUserChoice(String userChoice){
        ArrayList<Object> actionAndId = new ArrayList<>();
        Character action = userChoice.charAt(0);
        if (action.equals('r') || action.equals('e')) {
            if (userChoice.length() == 1){
                actionAndId.add(action);
                return actionAndId;
            }
            return actionAndId;
        }

        Integer id = -1;
        try {
            id = Integer.parseInt(userChoice.substring(1));
        } catch (NumberFormatException b) {
            return actionAndId;
        }

        boolean rightAction = action == 'v' ||  action == 'c' || action == 'a' ||  action == 'd';
        boolean rightId = gameUseCase.isIdInGame(id);

        if ((!rightAction || !rightId)){
            return actionAndId;
        }

        actionAndId.add(action);
        actionAndId.add(id);
        return actionAndId;
    }

    private void changeDialogue(String gameName, int id, int currentId){
        gamePresenter.displayScene("Enter the new dialogue",
                gameUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase.setDialogueById(id, newDialogue);
    }

    private void addDialogue(String gameName, int id, int currentId){
        gamePresenter.displayScene("Enter the new choice you want to add to the dialogue with id " + id,
                gameUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase.addChoiceToDialogue(newDialogue, id);
    }
}
