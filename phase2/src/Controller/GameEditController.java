package Controller;
import Interface.UserData;
import Presenter.GameTextPresenter;
import UseCase.GameUseCase;
import UseCase.GameUseCase2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Controller class to edit games.
 */

public class GameEditController {

    private UserData userData;
    private GameUseCase gameUseCase;
    private GameUseCase2 gameUseCase2;
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
     * @param gameUseCase A <GameUseCase> containing current game data.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameEditController(GameUseCase gameUseCase, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
    }

    /**
     * Method to edit existing games. Interacts with GameUseCase and allows users to make changes to existing games.
     */

    public void editGame(){
        ArrayList<String> newGames = new ArrayList<>();
        ArrayList<String> publicGames = gameUseCase.getPublicGamesByAuthor(userData.currentUser());
        for (String game: publicGames){
            newGames.add(game);
        }
        newGames.addAll(gameUseCase.getPrivateGames(userData.currentUser()));

        gameTextPresenter.displayScene("Enter the name of the game you want to edit.", newGames);
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRight(gameName)){ return; }
        ArrayList<Object> initialIdAndDialogue = gameUseCase2.openGame(gameName);

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
                gameUseCase2.changeGameState(gameName);
                gameUseCase2.saveGames();
                break;
            }
            else if(userChoice == 2){
                editGameDialogues(gameName, (String) initialIdAndDialogue.get(1), (int) initialIdAndDialogue.get(0));
            }
            else if(userChoice == 3){
                gameUseCase2.saveGames();
                break;
            }
        }
    }

    public void editGameAdminUser(){
        ArrayList<String> newGames = new ArrayList<>();
        ArrayList<String> publicGames = gameUseCase.getPublicGames();
        for (String game: publicGames){
            newGames.add(game);
        }
        newGames.addAll(gameUseCase2.getAllPrivateGames());

        gameTextPresenter.displayScene("Enter the name of the game you want to edit.", newGames);
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRight(gameName)){ return; }
        ArrayList<Object> initialIdAndDialogue = gameUseCase2.openGame(gameName);

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
                gameUseCase2.changeGameState(gameName);
                gameUseCase2.saveGames();
                break;
            }
            else if(userChoice == 2){
                editGameDialogues(gameName, (String) initialIdAndDialogue.get(1), (int) initialIdAndDialogue.get(0));
            }
            else if(userChoice == 3){
                gameUseCase2.saveGames();
                break;
            }
            else if(userChoice == 4){
                gameUseCase2.deleteGame(gameName);
                gameUseCase2.saveGames();
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
                gameTextPresenter.displayScene("You created this game, but it must be private to edit! " +
                        "Make it private? Enter 1 to make it private, enter 2 to cancel this edit request.");
                try{
                    userChoice = Integer.valueOf(scanner.next());
                }
                catch(NumberFormatException e){
                    System.out.println(e);
                }

                if(userChoice == 1){
                    gameUseCase2.changeGameState(gameName);
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

    private void editGameDialogues(String gameName, String currentDialogue, int currentId){
        while (true) {
            gameTextPresenter.displayScene("Dialogue ID " + currentId + ": " + currentDialogue +
                            " (Enter anything to continue)",
                    gameUseCase.getGameAsString(gameName, 69, currentId));
            scanner.next();
            gameTextPresenter.displayScene(this.editGameDialogueChoices,
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
                    int parentId = gameUseCase2.getParentDialogueId(currentId);
                    if (parentId != -1){
                        this.editGameDialogues(gameName, gameUseCase2.getDialogueById(parentId), parentId);
                        return;
                    }
                    break;
                case 'v':
                    this.editGameDialogues(gameName, gameUseCase2.getDialogueById(id), id);
                    return;
                case 'c':
                    this.changeDialogue(gameName, id, currentId);
                    break;
                case 'a':
                    this.addDialogue(gameName, id, currentId);
                    break;
                case 'd':
                    if (!gameUseCase2.deleteDialogueById(id)){
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
        boolean rightId = gameUseCase2.isIdInGame(id);

        if ((!rightAction || !rightId)){
            return actionAndId;
        }

        actionAndId.add(action);
        actionAndId.add(id);
        return actionAndId;
    }

    private void changeDialogue(String gameName, int id, int currentId){
        gameTextPresenter.displayScene("Enter the new dialogue",
                gameUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase2.setDialogueById(id, newDialogue);
    }

    private void addDialogue(String gameName, int id, int currentId){
        gameTextPresenter.displayScene("Enter the new choice you want to add to the dialogue with id " + id,
                gameUseCase.getGameAsString(gameName, 69, currentId));
        String newDialogue = String.valueOf(scanner.next());
        gameUseCase2.addChoiceToDialogue(newDialogue, id);
    }
}
