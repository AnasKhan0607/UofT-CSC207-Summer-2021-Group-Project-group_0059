package Entity;

import Interface.GameStorage;

import java.util.ArrayList;
/**
 * The Entity class for games. it has setters and getters for game variables, makes games storage to string,
 * give or set OR delete dialogues by the given id, give the dialogue children.
 *
 * @author Daniel Liu
 */
public class Game {
    // game name must be unique
    private String gameName;
    private String gameAuthor;
    private boolean gamePublic = false;
    private int choiceNumLimit;
    private GameStorage gameStorage;
    /**
     * Contructor for the class.
     *
     * @param gameName A <gameName> containing name of game.
     * @param  gameAuthor A <gameAuthor> containing name of author.
     * @param  gamePublic A <gamePublic> containing game public situation, if it contains false,
     *                    It means our game is a private game, if it is true , our game is public game.
     *                    It is false by default but can be changed by author.
     * @param  choiceNumLimit A <choiceNumLimit> maximum of choice author picked for the game by picking one of
     *                        the given template.
     * @param  gameStorage A <gameStorage> it contains the initialized storage of gameTree for our game.
     */

//    public Game(String gameName, String gameAuthor, boolean gamePublic, int choiceNumLimit, GameStorage gameStorage) {
//        this.gameName = gameName;
//        this.gameAuthor = gameAuthor;
//        this.gamePublic = gamePublic;
//        this.choiceNumLimit = choiceNumLimit;
//        this.gameStorage = gameStorage;
//    }
    /**
     * the setter class for game entity variables.
     */
    public Game(String gameName, String gameAuthor, boolean gamePublic, int choiceNumLimit, String initialDialogue) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.gamePublic = gamePublic;
        this.choiceNumLimit = choiceNumLimit;
        this.gameStorage = GameStorage.initializeStorage(choiceNumLimit, initialDialogue);
    }
    /**
     * the getter method for game name which set by author of the game.
     * @return name of game
     */
    public String getGameName(){return gameName;}
    /**
     * the setter method for game name which set by author of the game.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    /**
     * the getter method for name of game's author which set by author of the game.
     * @return name of author
     */
    public String getGameAuthor(){return gameAuthor;}
    /**
     * the setter method for name of game's author which set by author of the game.
     */
    public void setGameAuthor(String gameAuthor) {
        this.gameAuthor = gameAuthor;
    }
    /**
     * the getter method for public situation of game which set by author of the game.
     * @return boolean, if returns false our game is private, if returns true, our game is a public game.
     */
    public boolean getGamePublic(){return gamePublic;}
    /**
     * the setters method for public situation of game which set by author of the game.
     * it is boolean, if set false , our game is private, if set true, our game is a public game.
     */
    public void setGamePublic(boolean gamePublic) {
        this.gamePublic = gamePublic;
    }
    /**
     * the getter method for maximum number of choice in game which set by author of the game with
     * choosing one of the given templates.
     * @return maximum number of chose the author give in the hole of the game.
     */
    public int getchoiceNumLimit(){return choiceNumLimit;}
    /**
     * the setter method for maximum number of choices in game which set by author of the game with
     * choosing one of the given templates.
     */
    public void setChoiceNumLimit(int choiceNumLimit) {
        this.choiceNumLimit = choiceNumLimit;
    }
    /**
     * the getter method for the stored game tree(it print the tree).
     * @return it prints the stored gameTree for the given game.
     */
    public String toString(){
        return gameStorage.toString();
    }
    /**
     * the method for the stored game tree, it will start to return(print)the tree from the given id subtree.
     * @return it prints the stored gameTree from the id of the subtree we give it.
     */
    public String toString(int id){
        return gameStorage.toString(id);
    }
    /**
     * the getter method for the size of the tree for this game.
     * @return the size of the tree which stored for this game.
     */
    public int size(){
        return gameStorage.size();
    }
    /**
     * getter method for id of a dialogue by giving the stored string.
     * @return id of the given dialogue inside the gametree.
     */
    public String getDialogueById(int id) {
        return gameStorage.getDialogueById(id);
    }
    /**
     * getter method for a dialogue by giving its Id inside the gametree.
     * @return dialogue string for the given id.
     */
    public int getIdByDialogue(String dialogue){
        return gameStorage.getIdByDialogue(dialogue);
    }
    /**
     * setter method for a dialogue by giving its Id inside the gameTree.
     * @return if dialogue added it returns true, if anything goes wrong it returns false.
     */
    public boolean setDialogueById(int id, String dialogue){
        return gameStorage.setDialogueById(id, dialogue);
    }
    /**
     *  method for deleting a dialogue by giving its Id inside the gameTree.
     * @return if dialogue deleted it returns true, if anything goes wrong it returns false.
     */
    public boolean deleteDialogueById(int id){
        return gameStorage.deleteDialogueById(id);
    }
    /**
     * getter method for childeren of a dialogue by giving its Id inside the gametree.
     * @return Arraylist of stored string as a children of the given dialogue.
     */
    public ArrayList<String> getChildrenDialogues(int parentDialogueId){
        return gameStorage.getChildrenDialogues(parentDialogueId);
    };
    /**
     * getter method for parents dialogues by giving its children Ids inside the gametree.
     * @return Arraylist of ids for parents of given ids as children.
     */
    public ArrayList<Integer> getParentDialogueIds(ArrayList<Integer> childrenDialogueIds){
        return gameStorage.getParentDialogueIds(childrenDialogueIds);
    };
    /**
     * getter method for getteng all ids inside the saved Gametree for our game.
     * @return Arraylist of ids subtrees inside our game tree.
     */
    public ArrayList<Integer> getAllId(){
        return gameStorage.getAllId();
    };
    /**
     * method for adding a new choice the a dialogue.
     * @return true if the given string as dialogue added to the given id as its parent,
     * if anything goes wrong it returns false.
     */
    public boolean addChoiceToDialogue(String childDialogue, int parentDialogueId){
        return gameStorage.addChoiceToDialogue(childDialogue, parentDialogueId);
    }
}