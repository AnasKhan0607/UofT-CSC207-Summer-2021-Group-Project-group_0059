
import java.util.HashMap;

public class Game {
    class GameNode{
        private String nodeID;
        private String dialogue;
        private HashMap<String, String> pointers = new HashMap<String, String>();

    }

    private static int gameID;
    //1st digit is template number, for example, 1 for template 1
    private String gameName;
    private String gameAuthor;
    private GameNode firstNode;
    private String gameState;
    private int choicesNum;

    public Game(String gameName, String gameAuthor, String gameState, int choicesNum, GameNode firstNode) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.gameState = gameState;
        this.choicesNum = choicesNum;
        this.firstNode = firstNode;
    }

    public int GameIdGetters(){return gameID;}
    public String GameNameGetters(){return gameName;}
    public String AuthorGetters(){return gameAuthor;}
    public GameNode FirstNodeGetters(){return firstNode;}
    public String GameStateGetters(){return gameState;}
    public int ChoiceNumGetters(){return choicesNum;}

    public void GameIdSetters(int givenGameId){
        gameID = givenGameId;}
    private String generateID(){
        return "123";
    }

}