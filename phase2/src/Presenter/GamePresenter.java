package Presenter;
import Controller.MessageController;
import Entity.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Daniel Liu
 */

public class GamePresenter{

    Stage window;
    Object userChoice;
    String styleSheet = Objects.requireNonNull(getClass().getResource("GamePresenter.css")).toExternalForm();
    TableView<Message> messageTableView;

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

    public Integer displayChoices(Object suspendedObject, List<String> choices){
        Platform.runLater(() -> {
            VBox choicesLayout = wrapChoices(suspendedObject, choices);

            BorderPane layout = new BorderPane();
            layout.setCenter(choicesLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
        if (userChoice == null){
            return -1;
        }

        return (int) userChoice;
    }

    public int displayChoices(Object suspendedObject, List<String> choices, String text) {
        Platform.runLater(() -> {
            VBox textLayout = wrapDialogue(text);
            VBox choicesLayout = wrapChoices(suspendedObject, choices);

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
            messageColumns.add(wrapTextInColumn("Time", "time", 200));
            //from column (TableColumn<Message, String>)
            messageColumns.add(wrapTextInColumn("From", "from", 100));
            //msg column (TableColumn<Message, String>)
            messageColumns.add(wrapTextInColumn("Message", "msg", 700));
            //attachment column (TableColumn<Message, String>)
            messageColumns.add(wrapTextInColumn("Attachment", "attachment", 200));
            //status column (TableColumn<Message, Boolean>)
            messageColumns.add(wrapTextInColumn("READ", "status", 100));

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

    public void displayList(Object suspendedObject, List<String> items) {
        Platform.runLater(() -> {
            VBox inputLayout = wrapListItems(items);
            addExitButton(suspendedObject, inputLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(inputLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public void displayList(Object suspendedObject, List<String> items, String text) {
        Platform.runLater(() -> {
            VBox inputLayout = wrapListItems(items);
            VBox textLayout = wrapDialogue(text);
            addExitButton(suspendedObject, textLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(inputLayout);
            layout.setBottom(textLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public List<String> displayInputs(Object suspendedObject, List<String> inputTags){
        Platform.runLater(() -> {
            VBox inputLayout = wrapInputs(suspendedObject, inputTags);

            BorderPane layout = new BorderPane();
            layout.setCenter(inputLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
        // I know user choice will be a list of strings due to nature of wrapInputs()
        return (List<String>) userChoice;
    }

    public List<String> displayInputs(Object suspendedObject, List<String> inputTags, String text){
        Platform.runLater(() -> {
            VBox inputLayout = wrapInputs(suspendedObject, inputTags);
            VBox textLayout = wrapDialogue(text);

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
            VBox pictureLayout = wrapDialogue(textArt);
            VBox dialogueLayout = wrapDialogueInput(suspendedObject, dialogue);

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
            VBox dialogueLayout = wrapDialogue(dialogue);
            VBox pictureLayout = wrapChoices(null, new ArrayList<>());
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
            VBox pictureLayout = wrapDialogue(textArt);
            VBox dialogueLayout = wrapDialogue(dialogue);
            addExitButton(suspendedObject, dialogueLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(pictureLayout);
            layout.setBottom(dialogueLayout);

            displayScene(layout);
        });

        suspendThread(suspendedObject);
    }

    public void displayPictureScene(Object suspendedObject, String dialogue, String name) {
        String picturePath = findFile(name, System.getProperty("user.dir"));
        Platform.runLater(() -> {
            ImageView imageView = wrapImage(picturePath);
            VBox dialogueLayout = wrapDialogue(dialogue);
            addExitButton(suspendedObject, dialogueLayout);

            BorderPane layout = new BorderPane();
            layout.setCenter(imageView);
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

    private VBox wrapChoices(Object suspendedObject, List<String> choices) {
        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for(int i = 0; i < choices.size(); i++){
            final int counter = i;
            Button button = new Button(choices.get(i));
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

    private TableColumn<Message, Object> wrapTextInColumn(String title, String text, int minWidth) {
        TableColumn<Message, Object> timeColumn = new TableColumn<>(title);
        timeColumn.setMinWidth(minWidth);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>(text));
        return timeColumn;
    }

    private VBox wrapListItems(List<String> items) {
        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        layout.setAlignment(Pos.CENTER);
        for (Object item : items) {
            Label label = new Label(item.toString());
            label.setMinWidth(150);
            label.setMaxWidth(200);
            label.setMinHeight(40);
            label.setMaxHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            layout.getChildren().add(label);
        }
        return layout;
    }

    private VBox wrapInputs(Object suspendedObject, List<String> choices) {
        List<TextField> inputList = new ArrayList<>();
        List<String> inputString = new ArrayList<>();

        VBox layout = new VBox();
        layout.setMinHeight(650);
        layout.setMaxHeight(800);
        layout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
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
            userChoice = inputString;
            window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        layout.getChildren().add(button);
        return layout;
    }

    private VBox wrapDialogueInput(Object suspendedObject, String dialogue) {
        VBox dialogueLayout = wrapDialogue(dialogue);
        TextField input = new TextField();
        input.setMinWidth(200);
        dialogueLayout.getChildren().add(input);

        Button button = new Button("Finish Entering");
        button.setOnAction(event -> {
            userChoice = input.getText();
            window.hide();
            synchronized (suspendedObject) {
                suspendedObject.notify();
            }
        });
        dialogueLayout.getChildren().add(button);
        return dialogueLayout;
    }

    private VBox wrapDialogue(String dialogue) {
        VBox dialogueLayout = new VBox();
        dialogueLayout.setMinHeight(150);
        dialogueLayout.setMaxHeight(650);
        dialogueLayout.setAlignment(Pos.CENTER);
        dialogueLayout.setBorder(new Border(new BorderStroke(Paint.valueOf("#000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        dialogueLayout.setPadding(new Insets(20));
        Label dialogueLabel = new Label(dialogue);
        dialogueLabel.setWrapText(true);
        dialogueLayout.getChildren().add(dialogueLabel);
        return dialogueLayout;
    }

    private ImageView wrapImage(String picturePath) {
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
}
