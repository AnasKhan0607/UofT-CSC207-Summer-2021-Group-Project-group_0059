package UseCase;
import Entity.Game;
import Interface.SaveLoadGame;

import java.util.ArrayList;
import java.util.HashMap;

public class GameUseCase {
    private ArrayList<Game> publicGames;
    private ArrayList<Game> privateGames;
    private SaveLoadGame database;

    public ArrayList<Game> Author_private_games(String author_name){
        ArrayList<Game> game_list = new ArrayList<>();
        for(Game game: privateGames){
            if (game.getGameAuthor().equals(author_name)){
                game_list.add(game);
            }
        }
        return game_list;
    }

    public ArrayList<Game> Author_public_games(String author_name){
        ArrayList<Game> game_list = new ArrayList<>();
        for(Game game: publicGames){
            if (game.getGameAuthor().equals(author_name)){
                game_list.add(game);
            }
        }
        return game_list;
    }

    public ArrayList<HashMap<String, Game>> All_games(){
        ArrayList<HashMap<String, Game>> all_games = new ArrayList<>();
        for (Game game: publicGames){
            HashMap<String, Game> hashMap = new HashMap<String, Game>();
            hashMap.put(game.getGameAuthor(), game);
            all_games.add(hashMap);
        }
        for (Game game: privateGames){
            HashMap<String, Game> hashMap = new HashMap<String, Game>();
            hashMap.put(game.getGameAuthor(), game);
            all_games.add(hashMap);
        }
        return all_games;
    }
    public void New_game(int number_choice, String game_name, String author_name, String initial_dialogue){
        Game game = new Game(game_name, author_name, number_choice, initial_dialogue);
        privateGames.add(game);
    }

    public void GameStatusChanger(String game_name, String authorName){
        ArrayList<Game> arrayList = Author_private_games(authorName);
        for (Game game: arrayList){
            if(game.getGameName().equals(game_name)){
                game.setGamePublic(true);
                publicGames.add(game);
                privateGames.remove(game);
            }
        }
    }

    public void GameChoiceNum(String author,String game_name, int choice_num){
        ArrayList<Game> arrayList1 = Author_private_games(author);
        for (Game game: arrayList1){
            if(game.getGameName().equals(game_name)){
                game.setChoiceNumLimit(choice_num);
            }
        }
        ArrayList<Game> arrayList2 = Author_public_games(author);
        for (Game game: arrayList1){
            if(game.getGameName().equals(game_name)){
                game.setChoiceNumLimit(choice_num);
            }
        }
        }

    }
