package Presenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.AccessibleObject.setAccessible;


public class GamePresenter{

//    class AlertBox

    Stage window;
    Object userChoice;

    public GamePresenter(){
        // To do Platform.runlater, the toolkits must be initialized, see
        // https://staticfinal.blog/2015/04/04/javafx-toolkit-not-initialized-solved/
        new JFXPanel();

        Platform.runLater(() -> {
            this.window = new Stage();
            window.setTitle("VN Creator");
            window.setOnCloseRequest(e -> {
                System.out.println("Are you sure you want to exit? " +
                        "Nothing will be saved!");
                e.consume();
            });
        });
    }

    public Integer displayChoices(Object suspendedObject, List choices){
        Platform.runLater(() -> {
            VBox choicesLayout = wrapChoices(suspendedObject, choices);

            BorderPane layout = new BorderPane();
            layout.setCenter(choicesLayout);

            window.setScene(new Scene(layout, 1200, 800));
            window.show();
        });

        suspendThread(suspendedObject);
        return (int) userChoice;
    }

    public int displayChoices(Object suspendedObject, List choices, String dialogue) {
        Platform.runLater(() -> {
            HBox dialogueLayout = wrapDialogue(dialogue);

            VBox pictureLayout = wrapChoices(this, choices);

            BorderPane layout = new BorderPane();
            layout.setBottom(dialogueLayout);
            layout.setCenter(pictureLayout);

            window.setScene(new Scene(layout, 1200, 800));
            window.show();
        });

        suspendThread(suspendedObject);
        return (int) userChoice;
    }

    public int displayTextScene(Object suspendedObject, String dialogue) {
        Platform.runLater(() -> {
            HBox dialogueLayout = wrapDialogue(dialogue);

            VBox pictureLayout = wrapChoices(null, new ArrayList());

            BorderPane layout = new BorderPane();
            layout.setBottom(dialogueLayout);
            layout.setCenter(pictureLayout);

            window.setScene(new Scene(layout, 1200, 800));
            window.show();
        });

        suspendThread(suspendedObject);
        return (int) userChoice;
    }

    public int displayTextScene(Object suspendedObject, String dialogue, String textArt) {
        Platform.runLater(() -> {
            HBox pictureLayout = wrapDialogue(textArt);
            HBox dialogueLayout = wrapDialogue(dialogue);

            BorderPane layout = new BorderPane();
            layout.setCenter(pictureLayout);
            layout.setBottom(dialogueLayout);

            window.setScene(new Scene(layout, 1200, 800));
            window.show();
        });

        suspendThread(suspendedObject);
        return (int) userChoice;
    }

    public int displayPictureScene(Object suspendedObject, String dialogue, String pictureName) {
        String picturePath = findPictureFile(pictureName, System.getProperty("user.dir"));
        Platform.runLater(() -> {
            ImageView imageView = wrapImage(picturePath);
            HBox dialogueLayout = wrapDialogue(dialogue);

            BorderPane layout = new BorderPane();
            layout.setCenter(imageView);
            layout.setBottom(dialogueLayout);

            window.setScene(new Scene(layout, 1200, 800));
            window.show();
        });

        suspendThread(suspendedObject);
        return (int) userChoice;
    }

    private ImageView wrapImage(String picturePath) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(picturePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    private HBox wrapDialogue(String dialogue) {
        HBox dialogueLayout = new HBox();
        dialogueLayout.setMinHeight(150);
        dialogueLayout.setMaxHeight(650);
        dialogueLayout.setAlignment(Pos.CENTER);
        dialogueLayout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        dialogueLayout.setPadding(new Insets(20));
        Label dialogueLable = new Label(dialogue);
        dialogueLable.setWrapText(true);
        dialogueLayout.getChildren().add(dialogueLable);
        return dialogueLayout;
    }


    private void suspendThread(Object suspendedObject) {
        synchronized (suspendedObject){
            try{
                suspendedObject.wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception; object.wait failed.");
            }
        }
    }

    private VBox wrapChoices(Object suspendedObject, List choices) {
        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for(int i = 0; i < choices.size(); i++){
            final int counter = i;
            Button button = new Button(choices.get(i).toString());
            button.setOnAction(event -> {
                userChoice = counter;
                window.hide();
                synchronized (suspendedObject) {
                    suspendedObject.notify();
                }
            });
            layout.getChildren().add(button);
        }
        return layout;
    }

    // System.getProperty("user.dir"))
    private String findPictureFile(String pictureName, String filePath){
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        String foundPath = "";

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains(pictureName)){
                    return child.getAbsolutePath();
                }
                else{
                    String path = findPictureFile(pictureName, child.getAbsolutePath());
                    if (!path.equals("")){
                        return path;
                    }
                }
            }
        }
        return foundPath;
    }
}
