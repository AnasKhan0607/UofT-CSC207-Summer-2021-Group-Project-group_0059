public class Game {

    private static int gameID;
    //1st digit is template number, for example, 1 for template 1
    private String gameName;
    private String gameAuthor;
    private GameStorage firstTree;
    private String gameState;
    private int choicesNum;

    public Game(String gameName, String gameAuthor, String gameState, int choicesNum, GameStorage firstTree) {
        this.gameName = gameName;
        this.gameAuthor = gameAuthor;
        this.gameState = gameState;
        this.choicesNum = choicesNum;
        this.firstTree = firstTree;
    }

    public int getGameID(){return gameID;}
    public String getGameName(){return gameName;}
    public String getGameAuthor(){return gameAuthor;}
    public GameStorage getFirstTree(){return firstTree;}
    public String getGameState(){return gameState;}
    public int getChoicesNum(){return choicesNum;}

    public void GameIdSetters(int givenGameId){
        gameID = givenGameId;}
    private String generateID(){
        return "123";
    }

}