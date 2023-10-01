package yatzy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ScoreBoardTest {
    ScoreBoard scoreBoard;

    @Test
    @DisplayName("test next functionality")
    public void nextTest() {
        scoreBoard = ScoreBoard.createNewYatzyState(List.of("player0", "player1", "player2", "player3"), 5);
        Assertions.assertEquals("player0", scoreBoard.next());
        Assertions.assertEquals("player1", scoreBoard.next());
        Assertions.assertEquals("player2", scoreBoard.next());
        Assertions.assertEquals("player3", scoreBoard.next());
        Assertions.assertEquals("player0", scoreBoard.next());
    }
}
