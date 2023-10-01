package yatzy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

public class ScoreBoardItemTest {
    Function<List<Dice>, Integer> activeTest = diecList -> 0;
    Function<List<ScoreBoardItem>, Integer> passivTest = scoreList -> 0;

    @Test
    @DisplayName("test is selectable")
    public void testIsSelectable() {
        ScoreBoardItem scoreBoardItemActive = new ScoreBoardItem(new ActiveScoreItem("activeTest", activeTest, ScoreItem.SELECTABLE));
        Assertions.assertTrue(scoreBoardItemActive.isSelectable());
        scoreBoardItemActive.setScore(1);
        Assertions.assertFalse(scoreBoardItemActive.isSelectable());

        ScoreBoardItem scoreBoardItemPassive = new ScoreBoardItem(new PassivScoreItem("passiveTest", passivTest, ScoreItem.UPDATABLE));
        Assertions.assertFalse(scoreBoardItemPassive.isSelectable());
    }


}
