package yatzy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

public class ScoreItemTest {

    ScoreItem scoreItem;
    Function<List<Dice>, Integer> activeTest = diecList -> 0;
    Function<List<ScoreBoardItem>, Integer> passiveTest = scoreList -> 0;

    @Test
    @DisplayName("initializing score item")
    public void scoreItemInitialization() {
        scoreItem = new ActiveScoreItem("activeTest", activeTest, ScoreItem.SELECTABLE);
        Assertions.assertEquals("activeTest", scoreItem.getName());

        scoreItem = new PassivScoreItem("passiveTest", passiveTest, ScoreItem.UPDATABLE);
        Assertions.assertEquals("passiveTest", scoreItem.getName());
    }

    @Test
    @DisplayName("key set test")
    public void testKeySet() {
        scoreItem = new ActiveScoreItem("activeTest", activeTest, ScoreItem.SELECTABLE, ScoreItem.TOTAL, ScoreItem.BONUS);
        Assertions.assertTrue(scoreItem.getKeySet().contains(ScoreItem.SELECTABLE));
        Assertions.assertTrue(scoreItem.getKeySet().contains(ScoreItem.TOTAL));
        Assertions.assertTrue(scoreItem.getKeySet().contains(ScoreItem.BONUS));
        Assertions.assertFalse(scoreItem.getKeySet().contains(ScoreItem.UPDATABLE));

        scoreItem = new PassivScoreItem("passiveTest", passiveTest, ScoreItem.UPDATABLE);
        Assertions.assertTrue(scoreItem.getKeySet().contains(ScoreItem.UPDATABLE));
        Assertions.assertFalse(scoreItem.getKeySet().contains(ScoreItem.SELECTABLE));
    }
}
