package Controller;

import Interface.TemplateData;
import Interface.UserData;
import Presenter.GameTextPresenter;
import Presenter.GamePresenter;
import UseCase.GamesUseCase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game creation controller class which is called by GameMainController.
 */
public class GameCreateController {
    public static void main(String[] args) {
        new GameCreateController(null,null,null, new GamePresenter()).test();
    }

    public void test() {
        List bruh = new ArrayList();
        bruh.add("bruh");
        bruh.add("bruh2");
        bruh.add("bruh3");
        GamePresenter gamePresenter = new GamePresenter();
        gamePresenter.displayList(this, bruh);
        gamePresenter.displayList(this, bruh, "bro");
//        int one = gamePresenter.displayChoices(this, bruh);
//        System.out.println(one);
//        int two = gamePresenter.displayChoices(this, bruh, "hmmmmm");
//        System.out.println(two);
//        List three = gamePresenter.displayInputs(this, bruh);
//        System.out.println(three);
//        List four = gamePresenter.displayInputs(this, bruh, "BRUH!");
//        System.out.println(four);
//        gamePresenter.displayPictureScene(this, "hmmmmmm", "BRUH");
//        System.out.println("5");
//        gamePresenter.displayTextScene(this, "hmmmmm");
//        System.out.println(6);
//        gamePresenter.displayTextScene(this, "hmmmm", "awie;fjweaiofjeawio;fjweaio;f" +
//                " jwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwef");
//        System.out.println("7");
        gamePresenter.terminateGUI();

    }

    private TemplateData templateData;
    private UserData userData;
    private GamesUseCase gamesUseCase;
    private GamePresenter gamePresenter;

    /**
     * Contructor for the class.
     *
     * @param gamesUseCase A <GamesUseCase> containing current game data.
     * @param templateData A templateData interface containing current templates.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameCreateController(GamesUseCase gamesUseCase, TemplateData templateData, UserData userData, GamePresenter gamePresenter){
        this.gamesUseCase = gamesUseCase;
        this.userData = userData;
        this.templateData = templateData;
        this.gamePresenter = gamePresenter;
    }

    /**
     * Method to create new games from existing templates.
     * Interacts with the gamePresenter to communicate with the user and gamesUseCase to create games.
     */

    public void createGame(){
        int choiceNumLimit = templateData.chooseTemplate();
        if (choiceNumLimit == -1){
            System.out.println("bruh");
            return;
        }
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Game Name");
        tags.add("First Dialogue");

        List<String> inputs = gamePresenter.displayInputs(this, tags);
        boolean createSuccess = gamesUseCase.createGame(choiceNumLimit, inputs.get(0), userData.currentUser(), inputs.get(1));
        if (createSuccess){
            gamePresenter.displayTextScene(this, "Game creation completed.");
            gamesUseCase.saveGames();
        }
        else{
            gamePresenter.displayTextScene(this, "Game creation failed. A game with that name already exist.");
        }
    }
}
