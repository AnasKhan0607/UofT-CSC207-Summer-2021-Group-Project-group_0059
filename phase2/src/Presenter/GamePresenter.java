package Presenter;

import java.util.ArrayList;

/**
 * The presenter class for games.
 * Displays the games and their dialogues in a user-friendly format.
 */

public class GamePresenter {

    /**
     * Display a scenic view of Northern Lights
     * @param args arguments
     */

    public static void main(String[] args) {
        GamePresenter present = new GamePresenter();
        ArrayList<String> bruh = new ArrayList<>();
        bruh.add("bruh");
        bruh.add("bruh");
        bruh.add("bruh");
        bruh.add("bruh");
        bruh.add("bruh");


//        present.displayScene("bruh", bruh);
        present.displayScene("Northern Lights",
                "  ` : | | | |:  ||  :     `  :  |  |+|: | : : :|   .        `              .\n" +
                "      ` : | :|  ||  |:  :    `  |  | :| : | : |:   |  .                    :\n" +
                "         .' ':  ||  |:  |  '       ` || | : | |: : |   .  `           .   :.\n" +
                "                `'  ||  |  ' |   *    ` : | | :| |*|  :   :               :|\n" +
                "        *    *       `  |  : :  |  .      ` ' :| | :| . : :         *   :.||\n" +
                "             .`            | |  |  : .:|       ` | || | : |: |          | ||\n" +
                "      '          .         + `  |  :  .: .         '| | : :| :    .   |:| ||\n" +
                "         .                 .    ` *|  || :       `    | | :| | :      |:| |\n" +
                " .                .          .        || |.: *          | || : :     :|||\n" +
                "        .            .   . *    .   .  ` |||.  +        + '| |||  .  ||`\n" +
                "     .             *              .     +:`|!             . ||||  :.||`\n" +
                " +                      .                ..!|*          . | :`||+ |||`\n" +
                "     .                         +      : |||`        .| :| | | |.| ||`     .\n" +
                "       *     +   '               +  :|| |`     :.+. || || | |:`|| `\n" +
                "                            .      .||` .    ..|| | |: '` `| | |`  +\n" +
                "  .       +++                      ||        !|!: `       :| |\n" +
                "              +         .      .    | .      `|||.:      .||    .      .    `\n" +
                "          '                           `|.   .  `:|||   + ||'     `\n" +
                "  __    +      *                         `'       `'|.    `:\n" +
                "\"'  `---\"\"\"----....____,..^---`^``----.,.___          `.    `.  .    ____,.,-\n" +
                "    ___,--'\"\"`---\"'   ^  ^ ^        ^       \"\"\"'---,..___ __,..---\"\"'\n" +
                "--\"'                           ^                         ``--..,__ D. Rice");
//        present.displayScene(";wefj;");

//        present.displayScene("bitttttttteawjfioaei" +
//                "ewfiojawe;ifojwefoi;jwafiowejfiowef" +
//                "foeiw;fjewiofjwe;iofjefoji;weajfi;" +
//                "aweoi;fjiowe;fjiowe;fjweifjwefio" +
//                "tttch you r ugly aiowefjaioj;efjwae;oifjweiofjwe;afiojaweiofjwaioe;fjawe;oifje" +
//                ";weofijwefiowaefjwoei;fjwe;fowefo;wefj;");
    }

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
