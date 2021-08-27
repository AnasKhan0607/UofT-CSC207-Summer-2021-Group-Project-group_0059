package Presenter;

import Entity.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The wrapper class used to wrap information with Javafx objects.
 * @author Daniel Liu
 */
public class GameWrapper {
    /**
     * Wraps a list of string as choices with VBox
     * @param suspendedObject Object that could be called object.wait() on
     * @param choices A list of string, each string is a choice
     * @return VBox containing choices for each choice in choices
     */
    public VBox wrapChoices(Object suspendedObject, List<String> choices) {
        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
//        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for(int i = 0; i < choices.size(); i++){
            final int counter = i;
            Button button = new Button(choices.get(i));
            button.setOnAction(event -> {
                GamePresenter.userChoice = counter;
                GamePresenter.window.hide();
                synchronized (suspendedObject) {
                    suspendedObject.notify();
                }
            });
            layout.getChildren().add(button);
        }
        return layout;
    }

    /**
     * Wraps a title, some text as TableColumn with minimum width minWidth
     * @param title the title of the returned column
     * @param text the text in the returned column
     * @param minWidth the minimum width of the returned tableColumn
     * @return tableColumn a title and some text
     */
    public TableColumn<Message, Object> wrapTextInColumn(String title, String text, int minWidth) {
        TableColumn<Message, Object> timeColumn = new TableColumn<>(title);
        timeColumn.setMinWidth(minWidth);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>(text));
        return timeColumn;
    }

    /**
     * Wraps a list of string as items with VBox
     * @param items A list of string, each string is an item
     * @return VBox containing items for each item in items
     */
    public VBox wrapListItems(List<String> items) {
        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
//        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for (Object item : items) {
            Label label = new Label(item.toString());
            label.setMinWidth(200);
            label.setMinHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            layout.getChildren().add(label);
        }
        return layout;
    }

    /**
     * Wraps a list of string as inputs with VBox
     * @param suspendedObject Object that could be called object.wait() on
     * @param choices A list of string, each string represents a input description
     * @return VBox containing input field for each choice in choices
     */
    public VBox wrapInputs(Object suspendedObject, List<String> choices) {
        List<TextField> inputList = new ArrayList<>();
        List<String> inputString = new ArrayList<>();

        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
//        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for (Object choice : choices) {
            Label inputLabel = new Label(choice.toString());
            TextField input = new TextField();
            input.setMaxWidth(400);
            inputList.add(input);
            layout.getChildren().addAll(inputLabel, input);
        }
        Button button = new Button("Finish Entering");
        button.setOnAction(event -> {
            for (TextField textField : inputList) {
                inputString.add(textField.getText());
            }
            GamePresenter.userChoice = inputString;
            GamePresenter.window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        layout.getChildren().add(button);
        return layout;
    }

    /**
     * returns a VBox wrapping dialogue with an input field
     * @param suspendedObject Object that could be called object.wait() on
     * @param dialogue Text to be wrapped in a vbox
     * @return VBox wrapping the string dialogue and an input field
     */
    public VBox wrapDialogueInput(Object suspendedObject, String dialogue) {
        VBox dialogueLayout = wrapDialogue(dialogue);
        TextField input = new TextField();
        input.setMinWidth(100);
        dialogueLayout.getChildren().add(input);

        Button button = new Button("Finish Entering");
        button.setOnAction(event -> {
            GamePresenter.userChoice = input.getText();
            GamePresenter.window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        dialogueLayout.getChildren().add(button);
        return dialogueLayout;
    }

    /**
     * returns a VBox wrapping dialogue
     * @param dialogue Text to be wrapped in a vbox
     * @return VBox wrapping the string dialogue
     */
    public VBox wrapDialogue(String dialogue) {
        VBox dialogueLayout = new VBox();
        dialogueLayout.setMinHeight(150);
        dialogueLayout.setMaxHeight(650);
        dialogueLayout.setAlignment(Pos.CENTER);
//        dialogueLayout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        dialogueLayout.setPadding(new Insets(20));
        Label dialogueLabel = new Label(dialogue);
        dialogueLabel.setWrapText(true);
        dialogueLayout.getChildren().add(dialogueLabel);
        return dialogueLayout;
    }

    /**
     * wraps the picture indicated by picturePath in ImageView and returns it
     * @param picturePath a picture file's path
     * @return ImageView wrapping picturePath
     */
    public ImageView wrapImage(String picturePath) {
        InputStream stream;
        try {
            stream = new FileInputStream(picturePath);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            return null;
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitWidth(1200);
        imageView.setFitHeight(650);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
