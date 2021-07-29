package Controller;
import Entity.Game;
import Interface.UserData;
import Presenter.GamePresenter;
import UseCase.GameUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class GameEditController {

    private UserData userData;
    private GameUseCase gameUseCase;
    private GamePresenter gamePresenter = new GamePresenter();
    private Scanner scanner = new Scanner(System.in);


    public GameEditController(GameUseCase gameUseCase, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
    }

    public void editGame(){
        gamePresenter.displayScene("Enter the game name you want to edit.");
        String gameName = String.valueOf(scanner.next());
        if(!verifyEditGameRight(gameName)){
            return;
        }

        gameUseCase.openGame(gameName);

        int userChoice = 0;
        while (true){
            gamePresenter.displayScene(
                    "Choose and enter the corresponding integer.",
                    new ArrayList<>(Arrays.asList(new String[]{
                            "1: Make Game Public", "2: Edit Game Dialogues", "3: Exit"})));

            try{
                userChoice = Integer.valueOf(scanner.next());
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }

            if(userChoice == 1){
                gameUseCase.changeGameState(gameName);
                break;
            }
            else if(userChoice == 2){
                gamePresenter.displayScene("Enter r to return to the parent dialogue, " +
                                "v + id to view the game structure starting from the dialogue with that id (e.g. v1), " +
                                "s + id to show the dialogue associated with that id, " +
                                "c + id to change a game dialogue, " +
                                "a + id to add a dialogue, d + id to delete a dialogue " +
                                "and e to exit",
                        gameUseCase.getGameAsString(gameName, 69));
                editGameDialogues(gameName);
            }
            else if(userChoice == 3){
                break;
            }
        }
    }

    private boolean verifyEditGameRight(String gameName){
        boolean privateGameByUser = gameUseCase.getPrivateGames(userData.currentUser()).contains(gameName);
        boolean publicGameByUser = gameUseCase.getPublicGamesByAuthor(userData.currentUser()).contains(gameName);
        if(!privateGameByUser && publicGameByUser){
            int userChoice = 0;
            while(true){
                gamePresenter.displayScene("You created this game, but it must be private to edit! " +
                        "Make it private? Enter 1 to make it private, enter 2 to cancel this edit request.");
                try{
                    userChoice = Integer.valueOf(scanner.next());
                }
                catch(NumberFormatException e){
                    System.out.println(e);
                }

                if(userChoice == 1){
                    gameUseCase.changeGameState(gameName);
                    return true;
                }
                else if(userChoice == 2){
                    return false;
                }
            }
        }
        else if(!privateGameByUser && !publicGameByUser){
            gamePresenter.displayScene("You did not create this game! Enter anything to exit.");
            scanner.next();
            return false;
        }
        return true;
    }

    private void editGameDialogues(String gameName){
        gamePresenter.displayScene("Enter r to return to the parent dialogue, " +
                        "j + id to view the game structure starting from the dialogue with that id (e.g. j1), " +
                        "v + id to view the dialogue associated with that id, " +
                        "c + id to change a game dialogue, " +
                        "a + id to add a dialogue, and d + id to delete a dialogue.",
                gameUseCase.getGameAsString(gameName, 69));

    }

    private void addDialogueMenu(String gameName){

    }

    private void deleteDialogueMenu(String gameName){

    }
//    public boolean gameChangeDialogue(Game game, String dialogue,int id ) {
//        return game.setDialogueById(id, dialogue);
//    }
//
//    public boolean gameChangeChoice(Game game, String dialogue,int id ) {
//        return game.setDialogueById(id, dialogue);
//    }
//
//
//    public void SetGamePrivate(Game game){
//        game.setGamePublic(false);
//    }
//    public void SetGamePublic(Game game){
//        game.setGamePublic(true);
//    }
//
//
//    public boolean addDialogue(Game game, String dialogue, int parentId){
//        String P = game.getDialogueById(parentId);
//        if(GameUseCase.choiceOfDialogue2(P, game).size() < game.getchoiceNumLimit()){
//            game.addChoiceToDialogue(dialogue, parentId);
//            return true;
//        }else{
//            System.out.println("oops, you have limit,you cant add more ");
//            return false;
//        }
//    }
//
//    public boolean addChoice(Game game, String dialogue, int parentId){
//        String P = game.getDialogueById(parentId);
//        if(GameUseCase.choiceOfDialogue2(P, game).size() < game.getchoiceNumLimit()){
//            game.addChoiceToDialogue(dialogue, parentId);
//            return true;
//        }else{
//            System.out.println("oops, you have limit,no more choice!");
//            return false;
//        }
//    }
}
