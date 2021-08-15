package Controller;

import Entity.Game;
import Interface.TemplateData;
import Interface.UserData;
import Presenter.GameTextPresenter;
import Presenter.GamePresenter;
import UseCase.GameUseCase;
import UseCase.GameUseCase2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game creation controller class which is called by GameMainController.
 */
public class GameCreateController {
    public static void main(String[] args) {
        new GameCreateController(null,null,null).test();
        System.out.println("bruh");
    }

    public void test() {
        List bruh = new ArrayList();
        bruh.add("bruh");
        bruh.add("bruh2");
        bruh.add("bruh3");
        GamePresenter gamePresenter = new GamePresenter();
        gamePresenter.displayChoices(this, bruh);
        System.out.println("1");
        gamePresenter.displayChoices(this, bruh, "hmmmmm");
        System.out.println("2");
        gamePresenter.displayPictureScene(this, "hmmmmmm", "BRUH");
        System.out.println("3");
        gamePresenter.displayTextScene(this, "hmmmmm");
        System.out.println(4);
        gamePresenter.displayTextScene(this, "hmmmm", "awie;fjweaiofjeawio;fjweaio;f" +
                " jwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwefjwaefjaew;iowjfawe;ifj;weioefjweaof;ijwefoi;awejfio;awefjwe;oifjwef");
        System.out.println("5");

    }

    private TemplateData templateData;
    private UserData userData;
    private GameUseCase gameUseCase;
    private GameUseCase2 gameUseCase2;
    private GameTextPresenter gameTextPresenter = new GameTextPresenter();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Contructor for the class.
     *
     * @param gameUseCase A <GameUseCase> containing current game data.
     * @param templateData A templateData interface containing current templates.
     * @param userData A UserData interface containing info on current existing users.
     */

    public GameCreateController(GameUseCase gameUseCase, TemplateData templateData, UserData userData){
        this.gameUseCase = gameUseCase;
        this.userData = userData;
        this.templateData = templateData;
    }

    /**
     * Method to create new games from existing templates.
     * Interacts with the gamePresenter to communicate with the user and gameUseCase to create games.
     */

    public void createGame(){
        int choiceNumLimit = templateData.chooseTemplate();
        if (choiceNumLimit == -1){
            return;
        }
        gameTextPresenter.displayScene("Please enter the game name.");
        String gameName = String.valueOf(scanner.nextLine());
        gameTextPresenter.displayScene("Please enter the first dialogue.");
        String initialDialogue = String.valueOf(scanner.nextLine());
        gameUseCase.createGame(choiceNumLimit, gameName, userData.currentUser(), initialDialogue);
        gameTextPresenter.displayScene("Game creation complete. Enter anything to continue.");
        scanner.nextLine();
        gameUseCase2.saveGames();
    }
}
