package UseCase;
import Controller.GameMainController;
import Controller.RegularUserNavigatorController;
import Controller.TemplateEditorController;
import Entity.Game;
import Gateway.GameGate;
import Gateway.GameGateway;
import Interface.LoadSave;
import Interface.SaveLoadGame;

import java.util.*;

public class GameUseCase {
    public static void main(String[] args) {
        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-4, "bruh");
        gameData.put(-3, "Daniel Liu");
        gameData.put(-2, "true");
        gameData.put(-1, "4");
        gameData.put(0, "0");
        gameData.put(1, "1");
        gameData.put(2, "2");
        gameData.put(3, "3");
        gameData.put(4, "4");
        gameData.put(5, "5");
        gameData.put(21, "21");
        gameData.put(9, "9");

        List<HashMap> gamesData = new ArrayList<>();
        gamesData.add(gameData);
        GameGate bruh = new GameGate();
        bruh.save(gamesData);

        GameUseCase gameUseCase = new GameUseCase(new GameGate());
//        Game game = gameUseCase.privateGames.get(0);
        GameMainController gameController = new GameMainController(new TemplateEditorController(), new RegularUserNavigatorController("Daniel Liu"));
        gameController.gameMenu();
//        System.out.println(game);4
//        System.out.println(game.getDialogueById(1));
//        System.out.println(game.getDialogueById(4));
//        System.out.println(game.getDialogueById(21));
//        System.out.println(game.getDialogueById(9));
//        System.out.println(game.getDialogueById(5));

    }

    private ArrayList<Game> publicGames = new ArrayList<>();
    private ArrayList<Game> privateGames = new ArrayList<>();
    private LoadSave database;
    private Game currentGame;

    public GameUseCase(LoadSave database){
        this.database = database;
        List<HashMap> gamesData = database.load();
        for (HashMap gameData: gamesData){
            Game game = hashMapToGame(gameData);
            if (game.getGamePublic()){
                this.publicGames.add(game);
            }
            else {
                this.privateGames.add(game);
            }
        }
    }

    public void createGame(int number_choice, String game_name, String author_name, String initial_dialogue){
        Game game = new Game(game_name, author_name, false, number_choice, initial_dialogue);
        privateGames.add(game);
    }

    public String getGameAsString(String gameName){
        return this.getGameByName(gameName).toString();
    }

    public String getGameAsString(String gameName, int widthLimit){
        String gameAsString = this.getGameAsString(gameName);
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(gameAsString.split("\n")));

        for (int i = 0; i < splitString.size(); i++){
            if (splitString.get(i).length() > widthLimit){
                splitString.set(i, splitString.get(i).substring(0, widthLimit - 1));
            }
        }

        return String.join("\n", splitString);
    }

    public String getGameAsString(String gameName, int widthLimit, int id){
        String gameAsString = this.getGameByName(gameName).toString(id);
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(gameAsString.split("\n")));

        for (int i = 0; i < splitString.size(); i++){
            if (splitString.get(i).length() > widthLimit){
                splitString.set(i, splitString.get(i).substring(0, widthLimit - 1));
            }
        }

        return String.join("\n", splitString);
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

    public ArrayList<String> getPublicGames(){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: publicGames){
            game_list.add(game.getGameName());

        }
        return game_list;
    }

    public ArrayList<String> getPrivateGames(String author_name){
        ArrayList<String> game_list = new ArrayList<>();
        for(Game game: privateGames){
            if (game.getGameAuthor().equals(author_name)){
                game_list.add(game.getGameName());
            }
        }
        return game_list;
    }

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
        for (int id: ids){
            gameData.put(id, game.getDialogueById(id));
        }

        return gameData;
    }

    private Game hashMapToGame(HashMap<Integer, String> hashMap){
        if(!hashMap.containsKey(0) || !hashMap.containsKey(-1) || !hashMap.containsKey(-2) ||
                !hashMap.containsKey(-3) || !hashMap.containsKey(-4)){
            System.out.println("hashmap to game failed.");
            return null;
        }
        Game game = new Game(hashMap.get(-4), hashMap.get(-3), Boolean.parseBoolean(hashMap.get(-2)),
                Integer.parseInt(hashMap.get(-1)), hashMap.get(0));

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

        addDialoguesToGame(parentDialogueIds, childrenDialogueIds, game, hashMap,0);

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

    private void addDialoguesToGame(ArrayList<Integer> parentDialogueIds, ArrayList<Integer> childrenDialogueIds,
                                    Game game, HashMap<Integer, String> hashMap, int parentDialogueId){
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 0; i < parentDialogueIds.size(); i++){
            if (parentDialogueIds.get(i) == parentDialogueId){
                queue.add(childrenDialogueIds.get(i));
            }
        }
        Collections.sort(queue);

        for (int childId: queue){
            game.addChoiceToDialogue(hashMap.get(childId), parentDialogueId);
            addDialoguesToGame(parentDialogueIds, childrenDialogueIds, game, hashMap, childId);
        }
    }

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

    // change later to get dialogue choices by id
    public ArrayList<String> getDialogueChoices(String dialogue){
        return currentGame.getChildrenDialogues(currentGame.getIdByDialogue(dialogue));
    }

    public ArrayList<Integer> getDialogueChoiceIds(String dialogue){
        ArrayList<String> childrenDialogues = this.getDialogueChoices(dialogue);
        ArrayList<Integer> childrenIds = new ArrayList<>();
        for (String children: childrenDialogues){
            childrenIds.add(currentGame.getIdByDialogue(children));
        }
        return childrenIds;
    }
}
