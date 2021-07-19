import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    class GameNode {
        public String nodeID;
        public String dialogue;
        public HashMap<String, GameNode> children = new HashMap<String, GameNode>();
        public GameNode parent;
    }

    public static void main(String[] args) {

    }

    private String gameID;
    private String gameName;
    private String gameAuthor;
    private boolean gamePublic = false;
    private int choicesNum;
    private GameNode initialNode;
    private ArrayList<GameNode> publicGames = new ArrayList<GameNode>();
    private ArrayList<GameNode> privateGames = new ArrayList<GameNode>();


    public Game(String gameName, String gameAuthor, int choicesNum) {
        this.gameID = generateID();
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.choicesNum = choicesNum;
    }

    private String generateID() {
        return "123";
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameAuthor() {
        return gameAuthor;
    }

    public void setGameAuthor(String gameAuthor) {
        this.gameAuthor = gameAuthor;
    }

    public boolean isGamePublic() {
        return gamePublic;
    }

    public void setGamePublic(boolean gamePublic) {
        this.gamePublic = gamePublic;
    }

    public int getChoicesNum() {
        return choicesNum;
    }

    public void setChoicesNum(int choicesNum) {
        this.choicesNum = choicesNum;
    }

    public String getGameDialogue(String nodeID){
        return "";
    }
}
