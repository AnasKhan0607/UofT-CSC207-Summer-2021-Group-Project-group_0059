import java.util.HashMap;

public class Game{
    class GameNode{
        private String nodeID;
        private String dialogue;
        private HashMap<String, String> pointers = new HashMap<String, String>();

    }

    private String gameID;
    private String gameName;
    private String gameAuthor;
    private String gameState;
    private int choicesNum;

    public Game(String gameName, String gameAuthor, String gameState, int choicesNum) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.gameState = gameState;
        this.choicesNum = choicesNum;
    }

    private String generateID(){
        return "123";
    }


}
