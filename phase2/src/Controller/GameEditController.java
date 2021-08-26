package Controller;
import Interface.UserData;
import Presenter.GameTextPresenter;
import UseCase.GamesUseCase;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Controller class to edit games.
 *
 * @author Daniel Liu
 */

public class GameEditController {

    private UserData userData;
    private GamesUseCase gamesUseCase;
    private GameUseCase gameUseCase = new GameUseCase();
    private GameTextPresenter gameTextPresenter = new GameTextPresenter();
    private Scanner scanner = new Scanner(System.in);
    private final String editGameDialogueChoices = "Enter r to return to the parent dialogue, " +
            "v + id to view the dialogue with that id (e.g. v1), " +
            "c + id to change a game dialogue, " +
            "a + id to add a dialogue, d + id to delete a dialogue, " +
            "and e to exit.";

    /**
     * Contructor for the class.
     *
     * @param gamesUseCase A <GamesUseCase> containing current game data.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameEditController(GamesUseCase gamesUseCase, UserData userData){
        this.gamesUseCase = gamesUseCase;
        this.userData = userData;
    }

    /**
     * Method to edit existing games. Interacts with GamesUseCase and allows users to make changes to existing games.
     */

    public void editGame(){
        ArrayList<String> newGames = new ArrayList<>();
        ArrayList<String> publicGames = gamesUseCase.getPublicGamesByAuthor(userData.currentUser());
        for (String game: publicGames){
            newGames.add(game);
        }
        newGames.addAll(gamesUseCase.getPrivateGames(userData.currentUser()));

        gameTextPresenter.displayScene("Enter the name of the game you want to edit.", newGames);
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRight(gameName)){ return; }
        ArrayList<Object> initialIdAndDialogue = gameUseCase.openGame(gamesUseCase, gameName);

        int userChoice = 0;
        while (true){
            gameTextPresenter.displayScene(
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
                gamesUseCase.changeGameState(gameName);
                gamesUseCase.saveGames();
                break;
            }
            else if(userChoice == 2){
                editGameDialogues(gameName, (String) initialIdAndDialogue.get(1), (int) initialIdAndDialogue.get(0));
            }
            else if(userChoice == 3){
                gamesUseCase.saveGames();
                break;
            }
        }
    }

    public void editGameAdminUser(){
        ArrayList<String> newGames = new ArrayList<>();
        ArrayList<String> publicGames = gamesUseCase.getPublicGames();
        for (String game: publicGames){
            newGames.add(game);
        }
        newGames.addAll(gamesUseCase.getPrivateGames());

        gameTextPresenter.displayScene("Enter the name of the game you want to edit.", newGames);
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRightAdmin(gameName)){ return; }
        ArrayList<Object> initialIdAndDialogue = gameUseCase.openGame(gamesUseCase, gameName);

        int userChoice = 0;
        while (true){
            gameTextPresenter.displayScene(
                    "Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{
                            "1: change game state", "2: Edit Game Dialogues", "3: Exit and Save", "4:Delete Game"})));

            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }

            if(userChoice == 1){
                gamesUseCase.changeGameState(gameName);
                gamesUseCase.saveGames();
                break;
            }
            else if(userChoice == 2){
                editGameDialogues(gameName, (String) initialIdAndDialogue.get(1), (int) initialIdAndDialogue.get(0));
            }
            else if(userChoice == 3){
                gamesUseCase.saveGames();
                break;
            }
            else if(userChoice == 4){
                gamesUseCase.deleteGame(gameName);
                break;
            }
        }
    }

    private boolean verifyEditGameRight(String gameName){
        boolean privateGameByUser = gamesUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        boolean publicGameByUser = gamesUseCase.getPublicGamesByAuthor(userData.currentUser()).contains(gameName);
        if(!privateGameByUser && publicGameByUser){
            int userChoice = 0;
            while(true){
                gameTextPresenter.displayScene("You created this game, but it must be private to edit! " +
                        "Make it private? Enter 1 to make it private, enter 2 to cancel this edit request.");
                try{
                    userChoice = Integer.valueOf(scanner.next());
                }
                catch(NumberFormatException e){
                    System.out.println(e);
                }

                if(userChoice == 1){
                    gamesUseCase.changeGameState(gameName);
                    return true;
                }
                else if(userChoice == 2){
                    return false;
                }
            }
        }
        else if(!privateGameByUser && !publicGameByUser){
            gameTextPresenter.displayScene("You did not create this game! Enter anything to exit.");
            scanner.next();
            return false;
        }
        return true;
    }

    private boolean verifyEditGameRightAdmin(String gameName){
        boolean privateGames = gamesUseCase.getPrivateGames().contains(gameName);
        boolean publicGames = gamesUseCase.getPublicGames().contains(gameName);
        if(!privateGames && publicGames){
            int userChoice = 0;
            while(true){
                gameTextPresenter.displayScene("there is a game with this name, but it must be private to edit! " +
                        "Make it private? Enter 1 to make it private, enter 2 to cancel this edit request.");
                try{
                    userChoice = Integer.valueOf(scanner.next());
                }
                catch(NumberFormatException e){
                    System.out.println(e);
                }

                if(userChoice == 1){
                    gamesUseCase.changeGameState(gameName);
                    return true;
                }
                else if(userChoice == 2){
                    return false;
                }
            }
        }
        else if(!privateGames && !publicGames){
            gameTextPresenter.displayScene("there is not any match game! Enter anything to exit.");
            scanner.next();
            return false;
        }
        return true;
    }
    private void editGameDialogues(String gameName, String currentDialogue, int currentId){
        while (true) {
            gameTextPresenter.displayScene("Dialogue ID " + currentId + ": " + currentDialogue +
                            " (Enter anything to continue)",
                    gamesUseCase.getGameAsString(gameName, 69, currentId));
            scanner.next();
            gameTextPresenter.displayScene(this.editGameDialogueChoices,
                    gamesUseCase.getGameAsString(gameName, 69, currentId));
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

            if (editGameDialoguesActionHelper(gameName, currentId, action, id)) return;
        }
    }

    private boolean editGameDialoguesActionHelper(String gameName, int currentId, char action, int id) {
        switch (action){
            case 'r':
                int parentId = gameUseCase.getParentDialogueId(currentId);
                if (parentId != -1){
                    this.editGameDialogues(gameName, gameUseCase.getDialogueById(parentId), parentId);
                    return true;
                }
                break;
            case 'v':
                this.editGameDialogues(gameName, gameUseCase.getDialogueById(id), id);
                return true;
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
                return true;
        }
        return false;
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
        gameTextPresenter.displayScene("Enter the new dialogue",
                gamesUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase.setDialogueById(id, newDialogue);
    }

    private void addDialogue(String gameName, int id, int currentId){
        gameTextPresenter.displayScene("Enter the new choice you want to add to the dialogue with id " + id,
                gamesUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase.addChoiceToDialogue(newDialogue, id);
    }
}
