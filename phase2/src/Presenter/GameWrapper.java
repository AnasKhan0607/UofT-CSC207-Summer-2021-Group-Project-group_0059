package Presenter;

import Entity.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameWrapper {
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

    public TableColumn<Message, Object> wrapTextInColumn(String title, String text, int minWidth) {
        TableColumn<Message, Object> timeColumn = new TableColumn<>(title);
        timeColumn.setMinWidth(minWidth);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>(text));
        return timeColumn;
    }

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

    // Dont delete this commented method!
//    public ImageView wrapImage(String picturePath) {
//        InputStream stream;
//        try {
//            stream = new FileInputStream(picturePath);
//        } catch (FileNotFoundException e) {
//            System.out.println("File Not Found!");
//            return null;
//        }
//        Image image = new Image(stream);
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.setX(0);
//        imageView.setY(0);
//        imageView.setFitWidth(1200);
//        imageView.setFitHeight(650);
//        imageView.setPreserveRatio(true);
//        return imageView;
//    }
}
