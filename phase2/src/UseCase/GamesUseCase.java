package UseCase;
import Entity.Game;
import Interface.GameTemplateLoadSave;

import java.util.*;
/**
 * The UseCase class for games. it has some method for using the given game stored data in the game entity and
 * let work with it with the put rules.
 *
 * @author Daniel Liu Kimia Raminrad
 */
public class GamesUseCase {

    private ArrayList<Game> publicGames = new ArrayList<>();
    private ArrayList<Game> privateGames = new ArrayList<>();
    private final GameTemplateLoadSave database;
    /**
     * Contructor for the class. Gets all the games saved using GameGate and load them into this.publicGames and this.privateGames
     *
     * @param  database A <database> the stored load save which store list of hashmap
     *                  which can be transformed into needed data for a Game entity.
     */
    public GamesUseCase(GameTemplateLoadSave database){
        this.database = database;
        System.out.println("Loading game data from database...");
        List<HashMap<Integer, String>> gamesData = database.load();
        System.out.println("Loading complete.");
        for (HashMap<Integer, String> gameData: gamesData){
            Game game = hashMapToGame(gameData);
            assert game != null;
            if (game.getGamePublic()){
                this.publicGames.add(game);
            }
            else {
                this.privateGames.add(game);
            }
        }
    }
    /**
     * method for creating a new game , which gets number of choice, name of the game,
     * name of author and initial dialogue. Game name must be unique.
     */
    public Boolean createGame(int number_choice, String game_name, String author_name, String initial_dialogue, String styleSheet){
        if (getGameByName(game_name) == null && !game_name.equals("")){
            Game game = new Game(game_name, author_name, false, number_choice, initial_dialogue, styleSheet);
            privateGames.add(game);
            return true;
        }
        return false;
    }

    /**
     * Method that returns a string representing the game tree of a game with gameName.
     *
     * @param gameName the game name
     * @return a printed tree representation of the game
     */
    public String getGameAsString(String gameName){
        return this.getGameByName(gameName).toString();
    }

    /**
     * Method that returns the string representing the game tree of a gameName; this string needs to be at most
     * widthLimit wide and starts from the node with id startPoint.
     *
     * @param gameName the game name
     * @param widthLimit limits the returned string's width by a certain character count. For example, can be max 69 characters wide.
     * @param startPoint makes the returned printed tree start from the node with the id represented by the startPoint.
     * @return a printed tree representation of the game
     */
    public String getGameAsString(String gameName, int widthLimit, int startPoint){
        String gameAsString = this.getGameByName(gameName).toString(startPoint);
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(gameAsString.split("\n")));

        for (int i = 0; i < splitString.size(); i++){
            if (splitString.get(i).length() > widthLimit){
                splitString.set(i, splitString.get(i).substring(0, widthLimit - 1));
            }
        }

        return String.join("\n", splitString);
    }

    // package protected
    Game getGameByName(String gameName){
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
     * method for getting all made games which are public.
     * @return arraylist of names for public games.
     */
    public ArrayList<String> getPublicGames(){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: publicGames){
            game_list.add(game.getGameName());

        }
        return game_list;
    }
    /**
     * method for getting all made games which are private.
     * @return arraylist of names for private games.
     * @param author_name String for author name.
     */
    public ArrayList<String> getPrivateGames(String author_name){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: privateGames){
            if (game.getGameAuthor().equals(author_name)){
                game_list.add(game.getGameName());
            }
        }
        return game_list;
    }

    /**
     * Return an arraylist of all private game names
     * @return an arraylist of the names of all private game
     */
    public ArrayList<String> getPrivateGames(){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: privateGames){
            game_list.add(game.getGameName());
        }
        return game_list;
    }

    /**
     * method for getting all made games by author which are public bu giving author name.
     * @return arraylist of names for private games made by the given author name.
     * @param author_name String for author name.
     */
    public ArrayList<String> getPublicGamesByAuthor(String author_name){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: publicGames){
            if (game.getGameAuthor().equals(author_name)){
                game_list.add(game.getGameName());
            }
        }
        return game_list;
    }

    private HashMap<Integer, String> gameToHashMap(Game game){
        ArrayList<Integer> ids = game.getAllId();
        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-4, game.getGameName());
        gameData.put(-3, game.getGameAuthor());
        gameData.put(-2, String.valueOf(game.getGamePublic()));
        gameData.put(-1, String.valueOf(game.getchoiceNumLimit()));
        gameData.put(-5, game.getStyleSheetName());
        for (int id: ids){
            gameData.put(id, game.getDescriptionById(id) + "#;" + game.getDialogueById(id));
        }

        return gameData;
    }

    private Game hashMapToGame(HashMap<Integer, String> hashMap){
        if(!hashMap.containsKey(0) || !hashMap.containsKey(-1) || !hashMap.containsKey(-2) ||
                !hashMap.containsKey(-3) || !hashMap.containsKey(-4)|| !hashMap.containsKey(-5)){
            System.out.println("hashmap to game failed.");
            return null;
        }
        Game game = new Game(hashMap.get(-4), hashMap.get(-3), Boolean.parseBoolean(hashMap.get(-2)),
                Integer.parseInt(hashMap.get(-1)), hashMap.get(0).split("#;")[1], hashMap.get(-5));

        ArrayList<Integer> childrenDialogueIds = new ArrayList<>();
        for ( int key : hashMap.keySet() ) {
            if (key > 0){
                childrenDialogueIds.add(key);
            }
        }
        ArrayList<Integer> parentDialogueIds = game.getParentDialogueIds(childrenDialogueIds);
        if(!checkHashmapFormat(parentDialogueIds, childrenDialogueIds)){
            System.out.println("hashmap to game failed.");
            return null;
        }

        addDialoguesToGames(parentDialogueIds, childrenDialogueIds, game, hashMap,0);

        return game;
    }

    private boolean checkHashmapFormat(ArrayList<Integer> parentDialogueIds,
                                       ArrayList<Integer> childrenDialogueIds){
        for (int parentId: parentDialogueIds){
            if (!childrenDialogueIds.contains(parentId) && parentId != 0){
                return false;
            }
        }
        return true;
    }

    private void addDialoguesToGames(ArrayList<Integer> parentDialogueIds, ArrayList<Integer> childrenDialogueIds,
                                     Game game, HashMap<Integer, String> hashMap, int parentDialogueId){
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 0; i < parentDialogueIds.size(); i++){
            if (parentDialogueIds.get(i) == parentDialogueId){
                queue.add(childrenDialogueIds.get(i));
            }
        }
        Collections.sort(queue);

        for (int childId: queue){
            String[] texts = hashMap.get(childId).split("#;");
            game.addChoiceToDialogue(texts[1], texts[0], parentDialogueId);
            addDialoguesToGames(parentDialogueIds, childrenDialogueIds, game, hashMap, childId);
        }
    }
    /**
     * method for saving the created game if the author isn't Guest user.
     */
    public void saveGames(){
        List<HashMap<Integer, String>> gamesData = new ArrayList<>();
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

        System.out.println("Saving changes...");
        database.save(gamesData);
        System.out.println("Changes saved.");
    }
    /**
     * method for changing public status of a game by giving the game's name.
     * because all games are private at the beginning, it just can change private game to public game.
     * @return if public status changing goes well it returns true, if anything goes wrong it returns false.
     * @param game_name String for game name.
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
     * Delete the game with game_name
     * @param game_name the name of a Game object
     * @return whether the game with game_name has been successfully deleted
     */
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
        return false;
    }
}