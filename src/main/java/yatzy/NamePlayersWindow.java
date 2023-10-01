package yatzy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class NamePlayersWindow {

    private Stage stage;

    private Scene scene;

    private Parent root;

    private String player;

    @FXML
    private TextField nameField;

    @FXML
    private ListView<String> playerListView;

    @FXML
    private Button startGame;

    @FXML
    private void initialize() {
        startGame.setDisable(true);
        playerListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                player = playerListView.getSelectionModel().getSelectedItem();
            }
        });

    }

    public void addNameButton() {
        playerListView.getItems().add(nameField.getText());
        nameField.setText("");
        startGame.setDisable(false);
    }

    public void removePlayer() {
        playerListView.getItems().remove(player);
        if (playerListView.getItems().size() < 1) {
            startGame.setDisable(true);
        }
    }

    public void startButton(ActionEvent event) throws IOException {
        ScoreBoard.createNewYatzyState(playerListView.getItems().stream().toList(), 5);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("yatzy.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            e.consume();
            exitProgram(stage);
        });
    }

    public void exitProgram(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting Yatsy");
        alert.setHeaderText("You are about to exit Yatsy");
        alert.setContentText("You will be able to continue this game by pressing continue next time you lunch the game");

        if (alert.showAndWait().get() == ButtonType.OK) {
             stage.close();
        }
    }
}
