package Presenter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A presenter class used to wrap messages displayed to the user in boxes to make the output more visually appealing.
 */
public class BoxWrapper {
    public static void main(String[] args) {
//        TextBox
    }
    /*

 */
    public final String box;
    private final int textWidth;
    private final int textHeight;
    private final int textStartLine;

    /**
     * The contructor class where different types of boxes can be initialized depending on the user input.
     * Text will be wrapped in the type of box chosen.
     * @param boxType An integer for the choice of box that is desired.
     */

    public BoxWrapper(String boxType){
        if (boxType.equals("picture")){
            this.box =
                    ".-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-.\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "!                                                                       !\n" +
                    ":                                                                       :\n" +
                    ":                                                                       :\n" +
                    ".                                                                       .\n" +
                    ".                                                                       .\n" +
                    ":                                                                       :\n" +
                    ":                                                                       :\n" +
                    "!                                                                       !\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "`-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-'\n";
            this.textWidth = 69;
            this.textHeight = 16;
            this.textStartLine = 2;
        }
        else if (boxType.equals("dialogue")){
            this.box =
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|                                                                       |\n" +
                    "|_______________________________________________________________________|";
            this.textWidth = 69;
            this.textHeight = 3;
            this.textStartLine = 1;
        }
        else if (boxType.equals("choice")){
            this.box =
                    "|-------------------------------------------------------------------|\n" +
                    "|                                                                   |\n" +
                    "|                                                                   |\n" +
                    "|-------------------------------------------------------------------|\n";
            this.textWidth = 65;
            this.textHeight = 2;
            this.textStartLine = 2;
        }
        else{
            this.box = "";
            this.textWidth = 0;
            this.textHeight = 0;
            this.textStartLine = 0;
        }
    };

    /**
     * Returns an empty box depending on the choice when the class was initialized.
     */

    @Override
    public String toString(){
        return this.box;
    }

    /**
     * Used to insert a ASCII art picture within a box.
     * @param text the string representation of the ASCII art picture to be inserted.
     * @return The string representation of the given ASCII art picture within a box.
     */

    public String insertPicture(String text){
        String[] boxLines = this.box.split("\n");
        ArrayList<String> splitText = new ArrayList<>(Arrays.asList(text.split("\n")));

        if (splitText.size() > this.textHeight){
            for (int x = 0; x < splitText.size() - this.textHeight; x++){
                if (x%2==0){
                    splitText.remove(0);
                }
                else {
                    splitText.remove(splitText.size() - 1);
                }
            }
        }

        for (int i = 0; i < splitText.size(); i++){
            if(splitText.get(i).length() > this.textWidth) {
                splitText.set(i, splitText.get(i).substring(
                        (splitText.get(i).length() - this.textWidth + 1)/2,
                        splitText.get(i).length() - (splitText.get(i).length() - this.textWidth)/2));
            }
            else if(splitText.get(i).length() < this.textWidth){
                splitText.set(i, splitText.get(i) +
                        new String(new char[this.textWidth - splitText.get(i).length()]).
                                replace("\0", " "));
            }
        }
//        System.out.println(String.join("", splitText));

        return insertText(String.join("", splitText));
    }

    /**
     * Return a string which displays the given text within a box.
     * @param text A string of the text to display within the box.
     * @return The string representation of a box containing the given text.
     */

    public String insertText(String text){
        String[] boxLines = this.box.split("\n");
        ArrayList<String> splitText = splitTextByLength(text, this.textWidth);

        if(splitText.size() > this.textHeight){
            boxLines = this.expandHeight(splitText.size() - this.textHeight, boxLines);
        }

        for (int i = 0; i < splitText.size(); i++){
            boxLines[this.textStartLine - 1 + i] = boxLines[this.textStartLine - 1 + i].
                    replaceAll(new String(new char[this.textWidth + 2]).
                    replace("\0", " "), " " + splitText.get(i) +
                    new String(new char[this.textWidth - splitText.get(i).length()]).
                            replace("\0", " ") + " ");
        }

        return String.join("\n", boxLines);
    }

    private ArrayList<String> splitTextByLength(String text, int length){
        ArrayList<String> splitText = new ArrayList<String>((text.length() + length - 1) / length);

        for (int x = 0; x < text.length(); x += length) {
            splitText.add(text.substring(x, Math.min(text.length(), x + length)));
        }
        return splitText;
    }

    private String[] expandHeight(int expandHeight, String[] boxLines){
        ArrayList<String> bLines= new ArrayList<>(Arrays.asList(boxLines));
        for (int x = 0; x < expandHeight; x++){
            if (x%2==0){
                bLines.add(1, bLines.get(1));
            }
            else {
                bLines.add(bLines.size() - 2, bLines.get(bLines.size() - 2));
            }
        }
        return bLines.stream().toArray(String[]::new);
    }
}

