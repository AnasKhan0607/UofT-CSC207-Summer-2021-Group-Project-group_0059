package Entity;

import Interface.GameStorage;

public class Game {
    public static void main(String[] args) {
        Game bruh = new Game("BRUH", "le bruh", 4, "Bro Bruh Bruh Bro");
        bruh.addChoiceToDialogue("1", 0);
        bruh.addChoiceToDialogue("2", 0);
        bruh.addChoiceToDialogue("3", 0);
        bruh.addChoiceToDialogue("4", 0);
        bruh.addChoiceToDialogue("9", 2);



        System.out.println(bruh);
    }

    // game name must be unique
    private String gameName;
    private String gameAuthor;
    private boolean gamePublic = false;
    private int choiceNumLimit;
    private GameStorage gameStorage;


    public Game(String gameName, String gameAuthor, boolean gamePublic, int choiceNumLimit, GameStorage gameStorage) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.gamePublic = gamePublic;
        this.choiceNumLimit = choiceNumLimit;
        this.gameStorage = gameStorage;
    }

    public Game(String gameName, String gameAuthor, int choiceNumLimit, String initialDialogue) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.choiceNumLimit = choiceNumLimit;
        this.gameStorage = GameStorage.initializeStorage(choiceNumLimit, initialDialogue);
    }

    public String getGameName(){return gameName;}
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameAuthor(){return gameAuthor;}
    public void setGameAuthor(String gameAuthor) {
        this.gameAuthor = gameAuthor;
    }

    public boolean getGamePublic(){return gamePublic;}
    public void setGamePublic(boolean gamePublic) {
        this.gamePublic = gamePublic;
    }

    public int getchoiceNumLimit(){return choiceNumLimit;}
    public void setChoiceNumLimit(int choiceNumLimit) {
        this.choiceNumLimit = choiceNumLimit;
    }

    public String toString(){
        return gameStorage.toString();
    }

    public int size(){
        return gameStorage.size();
    }

    String getDialogueById(int id) {
        return gameStorage.getDialogueById(id);
    }

    public int getIdByDialogue(String dialogue){
        return gameStorage.getIdByDialogue(dialogue);
    }

    public boolean setDialogueById(int id, String dialogue){
        return gameStorage.setDialogueById(id, dialogue);
    }

    public boolean deleteDialogueById(int id){
        return gameStorage.deleteDialogueById(id);
    }

    public void addChoiceToDialogue(String childDialogue, int parentDialogueId){
        gameStorage.addChoiceToDialogue(childDialogue, parentDialogueId);
    }
}