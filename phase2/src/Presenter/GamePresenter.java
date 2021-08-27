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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The GUI class used for presenting information as gui.
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

    /**
     * the constructor for the gui; it initiates the global window.
     */
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

    /**
     * Sets the css stylesheet for the Game Presenter.
     * @param styleSheetName name of the css file
     */
    public void setStyleSheet(String styleSheetName){
        try{
            this.styleSheet = Objects.requireNonNull(getClass().getResource(styleSheetName + ".css")).toExternalForm();
        }
        catch (NullPointerException e){
            styleSheet = Objects.requireNonNull(getClass().getResource("GamePresenter.css")).toExternalForm();
        }
    }

    /**
     * display the choices given by the list choices
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param choices the descriptions of each choice
     * @param text string displayed below the choices
     * @return integer representing the choice the user made
     */
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

    /**
     * display messages and 3 buttons representing open message attachment, delete a message
     * and quit the screen
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param messages a list of messages
     * @return string representing the game name the user opened in a message
     */
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

    /**
     * Converts a list of messages to an observableList of messages
     * @param messages a list of messages
     * @return returns an ObservableList of messages
     */
    public ObservableList<Message> getMessages(List<Message> messages){
        ObservableList<Message> messages1 = FXCollections.observableArrayList();
        messages1.addAll(messages);
        return messages1;
    }

    /**
     * Displays a list of strings as items with some text below the items
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param items A list of strings, each string represents an item
     * @param text A string representing some text
     */
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

    /**
     * Displays a list of input fields (With their corresponding description
     * in inputTags) and returns the user's input after they finish
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param inputTags A list of strings, each string represents a description of an input
     * @param text A string representing some text
     * @return The list of inputs the user inputted
     */
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

    /**
     * Displays some chunk of text and another chunk of text below it and an input field
     * below both text chunk. Gathers the user's input and returns it.
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param dialogue Some other text displayed below textArt
     * @param textArt Some text
     * @return String representing the user's input
     */
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

    /**
     * Displays some text in a window
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param dialogue the text that is displayed
     */
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

    /**
     * Displays some chunk of text with another chunk of text below it.
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param dialogue the text that is displayed below textArt
     * @param textArt a chunk of text
     */
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

    /**
     * the GUI window is terminated globally, for all instances of GamePresenter.
     */
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

    /**
     * Displays a window with a picture and some text below it
     * @param suspendedObject the object thats about to be called object.wait() on
     * @param dialogue the text that is displayed below the picture
     * @param name the name of the picture file
     */
    public void displayPictureScene(Object suspendedObject, String dialogue, String name) {
        String picturePath = findFile(name, System.getProperty("user.dir"));
        Platform.runLater(() -> {
            ImageView imageView = gameWrapper.wrapImage(picturePath);
            VBox dialogueLayout = gameWrapper.wrapDialogue(dialogue);
            addExitButton(suspendedObject, dialogueLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(imageView);
            layout.setBottom(dialogueLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    // System.getProperty("user.dir"))
    private String findFile(String name, String filePath){
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        String foundPath = "";

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains(name)){
                    return child.getAbsolutePath();
                }
                else{
                    String path = findFile(name, child.getAbsolutePath());
                    if (!path.equals("")){
                        return path;
                    }
                }
            }
        }
        return foundPath;
    }

//Important! do not delete the commented methods below.
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

}
