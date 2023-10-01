package yatzy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class DiceTest {
@FXML
private Rectangle rectangle;
@FXML
private Button button;
private Dice dice;

    @BeforeEach
    public void setup() {
        rectangle = new Rectangle();
        button = new Button();
        dice = new Dice(rectangle, button);
    }

    @Test
    @DisplayName("Test diceroll 1-6")
    public void testRollDice() {
        Set<Integer> acceptableRoll = Set.of(1,2,3,4,5,6);
        for (int i = 0; i < 1000; i++) {
            dice.roleDie();
            Assertions.assertTrue(acceptableRoll.contains(dice.getFace()));
        }
    }

    @Test
    @DisplayName("Test Mark for roll")
    public void testMarkForRoll() {
        Assertions.assertTrue(dice.getMarkedForRole());
        dice.markedForRoleSwitch();
        Assertions.assertFalse(dice.getMarkedForRole());
        dice.markedForRoleSwitch();
        Assertions.assertTrue(dice.getMarkedForRole());
        dice.markedForRoleTrue();
        Assertions.assertTrue(dice.getMarkedForRole());
        dice.markedForRoleFalse();
        Assertions.assertFalse(dice.getMarkedForRole());
    }

    @Test
    @DisplayName("test rectangle opacity")
    public void TestRectangle() {
        Assertions.assertTrue(rectangle.getOpacity() == 1);
        dice.markedForRoleSwitch();
        Assertions.assertFalse(rectangle.getOpacity() == 0);
        dice.markedForRoleSwitch();
        Assertions.assertTrue(rectangle.getOpacity() == 1);
        dice.markedForRoleTrue();
        Assertions.assertTrue(rectangle.getOpacity() == 1);
        dice.markedForRoleFalse();
        Assertions.assertFalse(rectangle.getOpacity() == 0);
    }

    @Test
    @DisplayName("Test button lable")
    public void testButtonLable() {
        for (int i = 0; i < 1000; i++) {
            dice.roleDie();
            Assertions.assertTrue(button.getText().equals("" + dice.getFace()));
        }
    }
}
