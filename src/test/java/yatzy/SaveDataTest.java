package yatzy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SaveDataTest {

    @Test
    @DisplayName("test save functionality")
    public void testSaveFunctionality() {
        ScoreBoard scoreBoard = ScoreBoard.createNewYatzyState(List.of("player0", "player1", "player2", "player3"), 5);
        SaveData saveData = new SaveData();
        scoreBoard.gameStateToString();
        Assertions.assertTrue(saveData.isValidSaveFile());
        scoreBoard = ScoreBoard.loadYatzyStateFromFile("saveData.txt");
    }

    @Test
    @DisplayName("Test valid save File")
    public void validSaveFileTest() {
        SaveData validSaveData0 = new SaveData("validSaveData0");
        Assertions.assertTrue(validSaveData0.isValidSaveFile());
        SaveData validSaveData1 = new SaveData("validSaveData1");
        Assertions.assertTrue(validSaveData1.isValidSaveFile());
        SaveData validSaveData2 = new SaveData("validSaveData2");
        Assertions.assertTrue(validSaveData2.isValidSaveFile());
        SaveData notValidSaveData0 = new SaveData("notValidSaveData0");
        Assertions.assertFalse(notValidSaveData0.isValidSaveFile());
        SaveData notValidSaveData1 = new SaveData("notValidSaveData1");
        Assertions.assertFalse(notValidSaveData1.isValidSaveFile());
        SaveData notValidSaveData2 = new SaveData("notValidSaveData2");
        Assertions.assertFalse(notValidSaveData2.isValidSaveFile());
    }


}
