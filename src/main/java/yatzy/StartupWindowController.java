package yatzy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;


public class StartupWindowController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button continueButton;

    String saveFile = "saveData.txt";

    public void initialize() {
        SaveData saveData = new SaveData(saveFile);
        if (!saveData.isValidSaveFile()) {
            continueButton.setDisable(true);
        }
    }

    public void continueGame(ActionEvent e) throws IOException {
        ScoreBoard.loadYatzyStateFromFile(saveFile);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("yatzy.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exitProgram(stage);
        });
    }

    public void newGame(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("namePlayersWindow.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
