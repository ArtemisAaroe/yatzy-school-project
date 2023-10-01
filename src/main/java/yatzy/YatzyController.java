package yatzy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class YatzyController {

    private List<Dice> diecList;

    private List<Button> diecButtonList;

    private List<List<Label>> scoreLabels;

    private ScoreBoard scoreBoard;

    private ListItem currantListItem;

    private int rollCounter = 3;

    @FXML
    private ScrollPane scoreBoardScroll;

    @FXML
    private Label instructionLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Button die0;

    @FXML
    private Button die1;

    @FXML
    private Button die2;

    @FXML
    private Button die3;

    @FXML
    private Button die4;

    @FXML
    private Rectangle rectangle0;

    @FXML
    private Rectangle rectangle1;

    @FXML
    private Rectangle rectangle2;

    @FXML
    private Rectangle rectangle3;

    @FXML
    private Rectangle rectangle4;

    @FXML
    private Button rollDice;

    @FXML
    private ListView<ListItem> scoreItemListView;

    @FXML
    private Button scoreItemListViewButton;

    @FXML
    private void initialize() {
        scoreItemListViewButton.setDisable(true);

        scoreBoard = ScoreBoard.getInstance();

        diecButtonList = new ArrayList<>();
        diecButtonList.add(die0);
        diecButtonList.add(die1);
        diecButtonList.add(die2);
        diecButtonList.add(die3);
        diecButtonList.add(die4);
        diecButtonList.forEach(button -> button.setDisable(true));

        diecList = Stream.of(
                new Dice(rectangle0, die0),
                new Dice(rectangle1, die1),
                new Dice(rectangle2, die2),
                new Dice(rectangle3, die3),
                new Dice(rectangle4, die4)).toList();

        nameLabel.setText(scoreBoard.next());
        scoreItemListView.getItems().addAll(scoreBoard.getListItems());
        scoreItemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListItem>() {
            @Override
            public void changed(ObservableValue<? extends ListItem> observableValue, ListItem listItem, ListItem t1) {
                currantListItem = scoreItemListView.getSelectionModel().getSelectedItem();
            }
        });
        makeScoreBoard();
        instructionFieldUpdate();
    }

    private void makeScoreBoard() {
        scoreLabels = new ArrayList<>();
        GridPane scoreGrid = new GridPane();
        Insets margin = new Insets(5);
        for (int x = 0; x < scoreBoard.getPlayerCount() + 1; x++) {
            List<Label> labelList = new ArrayList<>();
            for(int y = 0; y < scoreBoard.getScoreSheetListSize() + 1; y++) {
                Label label = new Label(" ");
                labelList.add(label);
                label.setAlignment(Pos.CENTER_LEFT);
                GridPane.setMargin(label, margin);
                scoreGrid.add(label, x, y);
            }
            scoreLabels.add(labelList);
        }
        scoreBoardScroll.setContent(scoreGrid);
        scoreBoard.setScoreLabelsText(scoreLabels);
        scoreBoard.updateScoreLabels(scoreLabels);
    }

    @FXML
    public void rollButton(ActionEvent e) {
        diecList.stream()
                .filter(Dice::getMarkedForRole)
                .forEach(Dice::roleDie);
        diecList.forEach(Dice::markedForRoleFalse);
        rollCounter--;
        if (rollCounter == 0) {
            diecButtonList.forEach(button -> button.setDisable(true));
            rollDice.setDisable(true);
        } else {
            diecButtonList.forEach(button -> button.setDisable(false));
        }
        scoreItemListViewButton.setDisable(false);
        updateAfterDiceRoll();
    }

    private void updateAfterDiceRoll() {
        if (diecList.stream().allMatch(dice -> dice.getFace() > 0)) {
            // valid
            scoreItemListView.getItems().forEach(item -> item.calculate(diecList));
        }
        scoreItemListView.refresh();
        instructionFieldUpdate();

    }

    @FXML
    public void die0Press() {
        diecList.get(0).markedForRoleSwitch();
    }

    @FXML
    public void die1Press() {
        diecList.get(1).markedForRoleSwitch();
    }

    @FXML
    public void die2Press() {
        diecList.get(2).markedForRoleSwitch();
    }

    @FXML
    public void die3Press() {
        diecList.get(3).markedForRoleSwitch();
    }

    @FXML
    public void die4Press() {
        diecList.get(4).markedForRoleSwitch();
    }

    @FXML
    public void scoreItemListViewButtonPress() {
        if (currantListItem == null) {
            return;
        }
        scoreBoard.select(currantListItem);
        scoreBoard.updateScoreLabels(scoreLabels);
        scoreBoard.gameStateToString();
        String player = scoreBoard.next();
        if (player != null) {
            nameLabel.setText(player);
            scoreItemListView.getItems().removeAll(scoreItemListView.getItems());
            scoreItemListView.getItems().addAll(scoreBoard.getListItems());
            rollCounter = 3;
            rollDice.setDisable(false);
            diecList.forEach(Dice::markedForRoleTrue);
            diecList.forEach(Dice::zeroDie);
            diecButtonList.forEach(button -> button.setDisable(true));
            updateAfterDiceRoll();
            scoreItemListViewButton.setDisable(true);
        } else {
            instructionLabel.setText("The winner is: " + scoreBoard.getWinner());
            rollDice.setDisable(true);
            diecButtonList.forEach(button -> button.setDisable(true));
            scoreItemListViewButton.setDisable(true);
            scoreItemListView.setDisable(true);
        }

    }

    private void instructionFieldUpdate() {
        if (rollCounter > 0) {
            instructionLabel.setText("You have " + rollCounter + " rolls left");
        } else {
            instructionLabel.setText("Pick where you want to place this attempt on the score board");
        }
    }
}
