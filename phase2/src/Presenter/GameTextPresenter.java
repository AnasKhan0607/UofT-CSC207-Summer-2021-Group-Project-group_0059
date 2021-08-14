package Presenter;

import java.util.ArrayList;

/**
 * The presenter class for games.
 * Displays the games and their dialogues in a user-friendly format.
 */

public class GameTextPresenter {

    private BoxWrapper dialogueBox = new BoxWrapper("dialogue");
    private BoxWrapper pictureBox = new BoxWrapper("picture");
    private BoxWrapper choiceBox = new BoxWrapper("choice");

    /**
     * Displays the dialogue and corresponding scene to be displayed
     * @param dialogue The message to be displayed
     * @param picture The string which makes up a picture to be displayed.
     */

    public void displayScene(String dialogue, String picture){
        System.out.println(pictureBox.insertPicture(picture));
        System.out.println(dialogueBox.insertText(dialogue));
    }

    /**
     * Displays the dialogue and corresponding choices to be displayed
     * @param dialogue The string of the main message to be displayed
     * @param choices The list of strings which are the choices
     */

    public void displayScene(String dialogue, ArrayList<String> choices){
        String text = "";
        for (int i = 0; i < choices.size(); i++){
            text += choiceBox.insertText(choices.get(i)).replaceAll("\n", "");
        }
        System.out.println(pictureBox.insertText(text));
        System.out.println(dialogueBox.insertText(dialogue));
    }

    /**
     * Displays the dialogue given
     * @param dialogue The string of the main message to be displayed
     */

    public void displayScene(String dialogue){
        System.out.print(pictureBox);
        System.out.println(dialogueBox.insertText(dialogue));
    }

    /**
     * Wraps a box around choices that are displayed.
     * @param choices an arraylist of strings which are the choices to be displayed.
     */

    public void displayScene(ArrayList<String> choices){
        String text = "";
        for (int i = 0; i < choices.size(); i++){
            text += choiceBox.insertText(choices.get(i)).replaceAll("\n", "");
        }
        System.out.println(pictureBox.insertText(text));
    }
}
