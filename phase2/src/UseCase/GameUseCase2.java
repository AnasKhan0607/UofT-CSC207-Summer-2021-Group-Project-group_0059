package UseCase;

import Controller.GameMainController;
import Controller.RegularUserNavigatorController;
import Controller.TemplateEditorController;
import Entity.Game;
import Gateway.GameGate;
import Interface.LoadSave;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameUseCase2 {

    public static void main(String[] args) {
        GameUseCase gameUseCase = new GameUseCase(new GameGate());
        GameMainController gameController = new GameMainController(new TemplateEditorController(), new RegularUserNavigatorController("Daniel Liu"));
        gameController.gameMenu();

    }
    private ArrayList<Game> publicGames = new ArrayList<>();
    private ArrayList<Game> privateGames = new ArrayList<>();
    private LoadSave database;
    private Game currentGame;

    private HashMap<Integer, String> gameToHashMap(Game game){
        ArrayList<Integer> ids = game.getAllId();
        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-4, game.getGameName());
        gameData.put(-3, game.getGameAuthor());
        gameData.put(-2, String.valueOf(game.getGamePublic()));
        gameData.put(-1, String.valueOf(game.getchoiceNumLimit()));
        for (int id: ids){
            gameData.put(id, game.getDialogueById(id));
        }

        return gameData;
    }

    private Game getGameByName(String gameName){
        for(Game game: publicGames){
            if (game.getGameName().equals(gameName)){
                return game;
            }
        }
        for(Game game: privateGames){
            if (game.getGameName().equals(gameName)){
                return game;
            }
        }
        return null;
    }
    /**
     * method for saving the created game if the author isn't Guest user.
     */
    public void saveGames(){
        List<HashMap> gamesData = new ArrayList<HashMap>();
        for (Game game: this.privateGames){
            if (!game.getGameAuthor().equals("Guest")){
                gamesData.add(this.gameToHashMap(game));
            }
        }

        for (Game game: this.publicGames){
            if (!game.getGameAuthor().equals("Guest")){
                gamesData.add(this.gameToHashMap(game));
            }
        }

        database.save(gamesData);
    }
    /**
     * method for opening a game by giving the game's name.
     */
    public ArrayList<Object> openGame(String gameName){
        Game game = getGameByName(gameName);
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
     * method for changing public status of a game by giving the game's name.
     * because all games are private at the beginning, it just can change private game to public game.
     * @return if public status changing goes well it returns true, if anything goes wrong it returns false.
     */
    public boolean changeGameState(String game_name){
        for (Game game: this.privateGames){
            if(game.getGameName().equals(game_name)){
                game.setGamePublic(true);
                publicGames.add(game);
                privateGames.remove(game);
                return true;
            }
        }
        for (Game game: this.publicGames){
            if(game.getGameName().equals(game_name)){
                game.setGamePublic(false);
                privateGames.add(game);
                publicGames.remove(game);
                return true;
            }
        }
        return false;
    }
    /**
     * method for checking if the id of a dialogue or choice(subtree) is inside the ides of a game.
     * @return true if it is insde the game ides , if not returns false.
     */
    public boolean isIdInGame(int id){
        return currentGame.getAllId().contains(id);
    }
    /**
     * method for getting parent id of last opend game by user with giving a children id.
     * @return parent's id of the given id inside last open game.
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
     * @return string of the given id inside the last opened gameTree.
     */
    public String getDialogueById(int id){
        return currentGame.getDialogueById(id);
    }
    /**
     * method for setting dialogue string by giving it's id inside last opened game by user.
     * @return true if it set, else it returns false.
     */
    public boolean setDialogueById(int id, String dialogue){
        return currentGame.setDialogueById(id, dialogue);
    }
    /**
     * method for add a new dialogue  inside last opened game by user,by giving it's id and string.
     * @return true if it added, else it returns false.
     */
    public boolean addChoiceToDialogue(String childDialogue, int parentDialogueId){
        return currentGame.addChoiceToDialogue(childDialogue, parentDialogueId);
    }
    /**
     * method for deleting a dialogue  inside last opened game by user,by giving it's id.
     * @return true if it deleted , else it returns false.
     */
    public boolean deleteDialogueById(int id){
        return currentGame.deleteDialogueById(id);
    }
    /**
     * method for getting choices of a dialogue inside the last opened game of user, bu giving the dialogue id.
     * @return arraylist of choice's strings saved for the id of dialogue inside the last open game.
     */
    public ArrayList<String> getDialogueChoices(int dialogueId){
        return currentGame.getChildrenDialogues(dialogueId);
    }
    /**
     * method for getting choices of a dialogue inside the last opened game of user, bu giving the dialogue id.
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
    public ArrayList<String> getAllPrivateGames(){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: privateGames){
            game_list.add(game.getGameName());
        }
        return game_list;
    }

    public boolean deleteGame(String game_name){
        for (Game game: this.privateGames){
            if(game.getGameName().equals(game_name)){
                privateGames.remove(game);
                return true;
            }
        }
        for (Game game: this.publicGames){
            if(game.getGameName().equals(game_name)){
                publicGames.remove(game);
                return true;
            }
        }
        return true;
    }
}
