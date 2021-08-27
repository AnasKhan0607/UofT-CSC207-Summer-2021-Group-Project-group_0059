package Presenter;
import Controller.MessageController;
import Entity.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Daniel Liu
 */

public class GamePresenter{
    public static void main(String[] args) {
        new GamePresenter().setStyleSheet("");
    }

    static Stage window;
    static Object userChoice;
    private String styleSheet = Objects.requireNonNull(getClass().getResource("GamePresenter.css")).toExternalForm();
    private TableView<Message> messageTableView;
    private final GameWrapper gameWrapper = new GameWrapper();

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
            Platform.setImplicitExit(false);
        });
    }

    public void setStyleSheet(String styleSheetName){
        try{
            this.styleSheet = Objects.requireNonNull(getClass().getResource(styleSheetName + ".css")).toExternalForm();
        }
        catch (NullPointerException e){
            styleSheet = Objects.requireNonNull(getClass().getResource("GamePresenter.css")).toExternalForm();
        }
    }

    public int displayChoices(Object suspendedObject, List<String> choices, String text) {
        Platform.runLater(() -> {
            VBox textLayout = gameWrapper.wrapDialogue(text);
            VBox choicesLayout = gameWrapper.wrapChoices(suspendedObject, choices);

            BorderPane layout = new BorderPane();
            layout.setCenter(choicesLayout);
            layout.setBottom(textLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
        if (userChoice == null){
            return -1;
        }
        return (int) userChoice;
    }

    public String displayMessages(Object suspendedObject, List<Message> messages) {
        Platform.runLater(() -> {
            List<TableColumn<Message, Object>> messageColumns = new ArrayList<>();
            //Time column (TableColumn<Message, Date>)
            messageColumns.add(gameWrapper.wrapTextInColumn("Time", "time", 200));
            //from column (TableColumn<Message, String>)
            messageColumns.add(gameWrapper.wrapTextInColumn("From", "from", 100));
            //msg column (TableColumn<Message, String>)
            messageColumns.add(gameWrapper.wrapTextInColumn("Message", "msg", 700));
            //attachment column (TableColumn<Message, String>)
            messageColumns.add(gameWrapper.wrapTextInColumn("Attachment", "attachment", 200));
            //status column (TableColumn<Message, Boolean>)
            messageColumns.add(gameWrapper.wrapTextInColumn("READ", "status", 100));

            List<Button> buttons = getMessageButtons(suspendedObject);

            messageTableView = new TableView<>();
            messageTableView.setItems(getMessages(messages));
            messageTableView.getColumns().addAll(messageColumns);

            VBox vbox = new VBox();
            vbox.getChildren().addAll(messageTableView, buttons.get(0), buttons.get(1), buttons.get(2));

            Scene scene = new Scene(vbox);
            window.setScene(scene);
            window.show();
        });

        suspendThread(suspendedObject);
        if (userChoice == null){
            return "";
        }
        return (String) userChoice;
    }

    private List<Button> getMessageButtons(Object suspendedObject) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            ObservableList<Message> messagesSelected;
            messagesSelected = messageTableView.getSelectionModel().getSelectedItems();
            for (Message msg: messagesSelected){
                MessageController messageController = (MessageController) suspendedObject;
                messageController.removeMessage(msg.getid());
            }
        });
        Button quitButton = new Button("QUIT");
        quitButton.setOnAction(e -> {
            window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        Button viewButton = new Button("View Attachment");
        viewButton.setOnAction(e -> {
            ObservableList<Message> messagesSelected;
            messagesSelected = messageTableView.getSelectionModel().getSelectedItems();
            for (Message msg: messagesSelected){
                userChoice = msg.getAttachment();
            }
            window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });

        List<Button> buttons = new ArrayList<>();
        buttons.add(deleteButton);
        buttons.add(quitButton);
        buttons.add(viewButton);
        return buttons;
    }

    public ObservableList<Message> getMessages(List<Message> messages){
        ObservableList<Message> messages1 = FXCollections.observableArrayList();
        messages1.addAll(messages);
        return messages1;
    }

    public void displayList(Object suspendedObject, List<String> items, String text) {
        Platform.runLater(() -> {
            VBox inputLayout = gameWrapper.wrapListItems(items);
            VBox textLayout = gameWrapper.wrapDialogue(text);
            addExitButton(suspendedObject, textLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(inputLayout);
            layout.setBottom(textLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public List<String> displayInputs(Object suspendedObject, List<String> inputTags, String text){
        Platform.runLater(() -> {
            VBox inputLayout = gameWrapper.wrapInputs(suspendedObject, inputTags);
            VBox textLayout = gameWrapper.wrapDialogue(text);

            BorderPane layout = new BorderPane();
            layout.setCenter(inputLayout);
            layout.setBottom(textLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
        // I know user choice will be a list of strings due to nature of wrapInputs()
        return (List<String>) userChoice;
    }

    public String displayTextSceneInput(Object suspendedObject, String dialogue, String textArt) {
        Platform.runLater(() -> {
            VBox pictureLayout = gameWrapper.wrapDialogue(textArt);
            VBox dialogueLayout = gameWrapper.wrapDialogueInput(suspendedObject, dialogue);

            BorderPane layout = new BorderPane();
            layout.setCenter(pictureLayout);
            layout.setBottom(dialogueLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
        return (String) userChoice;
    }

    public void displayTextScene(Object suspendedObject, String dialogue) {
        Platform.runLater(() -> {
            VBox dialogueLayout = gameWrapper.wrapDialogue(dialogue);
            VBox pictureLayout = gameWrapper.wrapChoices(null, new ArrayList<>());
            addExitButton(suspendedObject, dialogueLayout);

            BorderPane layout = new BorderPane();
            layout.setBottom(dialogueLayout);
            layout.setCenter(pictureLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public void displayTextScene(Object suspendedObject, String dialogue, String textArt) {
        Platform.runLater(() -> {
            VBox pictureLayout = gameWrapper.wrapDialogue(textArt);
            VBox dialogueLayout = gameWrapper.wrapDialogue(dialogue);
            addExitButton(suspendedObject, dialogueLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(pictureLayout);
            layout.setBottom(dialogueLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public void terminateGUI(){
        Platform.setImplicitExit(true);
        Platform.runLater(() -> window.close());
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

    private void addExitButton(Object suspendedObject, VBox dialogueLayout) {
        Button button = new Button("Continue");
        button.setOnAction(event -> {
            window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        dialogueLayout.getChildren().add(button);
    }

    private void displayScene(Parent layout) {
        Scene scene = new Scene(layout, 1200, 800);
        scene.getStylesheets().add(styleSheet);
        window.setScene(scene);
        window.show();
    }

    //Important! do not delete the commented methods below.

//    public void displayPictureScene(Object suspendedObject, String dialogue, String name) {
//        String picturePath = findFile(name, System.getProperty("user.dir"));
//        Platform.runLater(() -> {
//            ImageView imageView = gameWrapper.wrapImage(picturePath);
//            VBox dialogueLayout = gameWrapper.wrapDialogue(dialogue);
//            addExitButton(suspendedObject, dialogueLayout);
//
//            BorderPane layout = new BorderPane();
//            layout.setCenter(imageView);
//            layout.setBottom(dialogueLayout);
//
//            displayScene(layout);
//        });
//
//        suspendThread(suspendedObject);
//    }


//    public List<String> displayInputs(Object suspendedObject, List<String> inputTags){
//        Platform.runLater(() -> {
//            VBox inputLayout = gameWrapper.wrapInputs(suspendedObject, inputTags);
//
//            BorderPane layout = new BorderPane();
//            layout.setCenter(inputLayout);
//
//            displayScene(layout);
//        });
//
//        suspendThread(suspendedObject);
//        // I know user choice will be a list of strings due to nature of wrapInputs()
//        return (List<String>) userChoice;
//    }

//    public void displayList(Object suspendedObject, List<String> items) {
//        Platform.runLater(() -> {
//            VBox inputLayout = gameWrapper.wrapListItems(items);
//            addExitButton(suspendedObject, inputLayout);
//
//            BorderPane layout = new BorderPane();
//            layout.setCenter(inputLayout);
//
//            displayScene(layout);
//        });
//
//        suspendThread(suspendedObject);
//    }

//    public Integer displayChoices(Object suspendedObject, List<String> choices){
//        Platform.runLater(() -> {
//            VBox choicesLayout = gameWrapper.wrapChoices(suspendedObject, choices);
//
//            BorderPane layout = new BorderPane();
//            layout.setCenter(choicesLayout);
//
//            displayScene(layout);
//        });
//
//        suspendThread(suspendedObject);
//        if (userChoice == null){
//            return -1;
//        }
//
//        return (int) userChoice;
//    }

    // System.getProperty("user.dir"))
//    private String findFile(String name, String filePath){
//        File dir = new File(filePath);
//        File[] directoryListing = dir.listFiles();
//        String foundPath = "";
//
//        if (directoryListing != null) {
//            for (File child : directoryListing) {
//                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains(name)){
//                    return child.getAbsolutePath();
//                }
//                else{
//                    String path = findFile(name, child.getAbsolutePath());
//                    if (!path.equals("")){
//                        return path;
//                    }
//                }
//            }
//        }
//        return foundPath;
//    }

}
