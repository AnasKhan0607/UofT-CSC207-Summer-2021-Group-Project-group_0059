package UseCase;
import Entity.Game;
import UseCase.GameUseCase;
import java.util.*;
/**
 * The UseCase class for games. it has some method for using the given game stored data in the game entity and
 * let work with it with the put rules.
 *
 * @author Daniel Liu Kimia Raminrad
 */
public class GameUseCase {

    private Game currentGame;
    /**
     * Contructor for the class. Gets all the games saved using GameGate and load them into this.publicGames and this.privateGames
     *
     */

    /**
     * method for opening a game by giving the game's name.
     * @param gameName String for game name.
     */
    public ArrayList<Object> openGame(GamesUseCase gamesUseCase, String gameName){
        Game game = gamesUseCase.getGameByName(gameName);
        this.currentGame = game;
        ArrayList<Integer> parentDialogue = game.getAllId();
        int initialDialogueId = parentDialogue.get(0);
        String initialDialogueSt = currentGame.getDialogueById(initialDialogueId);
        ArrayList<Object> arrayList= new ArrayList<>();
        arrayList.add(initialDialogueId);
        arrayList.add(initialDialogueSt);
        return arrayList;
    }

    /**
     * method for checking if the id of a dialogue or choice(subtree) is inside the ides of a game.
     * @return true if it is insde the game ides , if not returns false.
     * @param id , integers for initial subtree id.
     */
    public boolean isIdInGame(int id){
        return currentGame.getAllId().contains(id);
    }
    /**
     * method for getting parent id of last opend game by user with giving a children id.
     * @return parent's id of the given id inside last open game.
     * @param childrenDialogueId , integers for children dialogue subtree id.
     */
    public int getParentDialogueId(int childrenDialogueId){
        ArrayList<Integer> childrenDialogueIds = new ArrayList<>();
        childrenDialogueIds.add(childrenDialogueId);
        if (currentGame.getParentDialogueIds(childrenDialogueIds).get(0) == null){
            return -1;
        }
        return currentGame.getParentDialogueIds(childrenDialogueIds).get(0);
    }
    /**
     * method for getting dialogue string by giving it's id inside last opened game by user.
     * @param id , integers for dialogue subtree id.
     * @return string of the given id inside the last opened gameTree.
     */
    public String getDialogueById(int id){
        return currentGame.getDialogueById(id);
    }
    /**
     * method for setting dialogue string by giving it's id inside last opened game by user.
     * @param id , integers for dialogue subtree id.
     * @param dialogue , String for dialogue.
     * @return true if it set, else it returns false.
     */
    public boolean setDialogueById(int id, String dialogue){
        return currentGame.setDialogueById(id, dialogue);
    }
    /**
     * method for add a new dialogue  inside last opened game by user,by giving it's id and string.
     * @param parentDialogueId , integers for dialogue subtree id.
     * @param childDialogue, String for dialogue.
     * @return true if it added, else it returns false.
     */
    public boolean addChoiceToDialogue(String childDialogue, int parentDialogueId){
        return currentGame.addChoiceToDialogue(childDialogue, parentDialogueId);
    }
    /**
     * method for deleting a dialogue  inside last opened game by user,by giving it's id.
     * @param id , integers for dialogue subtree id.
     * @return true if it deleted , else it returns false.
     */
    public boolean deleteDialogueById(int id){
        return currentGame.deleteDialogueById(id);
    }
    /**
     * method for getting choices of a dialogue inside the last opened game of user, bu giving the dialogue id.
     * @param dialogueId , integers for dialogue subtree id.
     * @return arraylist of choice's strings saved for the id of dialogue inside the last open game.
     */
    public ArrayList<String> getDialogueChoices(int dialogueId){
        return currentGame.getChildrenDialogues(dialogueId);
    }
    /**
     * method for getting choices of a dialogue inside the last opened game of user, bu giving the dialogue id.
     * @param dialogueId , integers for dialogue subtree id.
     * @return arraylist of choice's ids saved for the id of dialogue inside the last open game.
     */
    public ArrayList<Integer> getDialogueChoiceIds(int dialogueId){
        ArrayList<String> childrenDialogues = this.getDialogueChoices(dialogueId);
        ArrayList<Integer> childrenIds = new ArrayList<>();
        for (String children: childrenDialogues){
            childrenIds.add(currentGame.getIdByDialogue(children));
        }
        return childrenIds;
    }
}