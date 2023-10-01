package yatzy;

import java.util.List;

public class ListItem {
    String name;

    int score = 0;

    private ScoreBoardItem scoreBoardItem;

    public ListItem(String name, ScoreBoardItem scoreBoardItem) {
        this.name = name;
        this.scoreBoardItem = scoreBoardItem;
    }

    public void calculate(List<Dice> diceList) {
        if (scoreBoardItem.getScoreItem() instanceof ActiveScoreItem) {
        score = (((ActiveScoreItem) scoreBoardItem.getScoreItem()).getFunction().apply(diceList));
        } else {
            throw new IllegalStateException("can not calculate if not ActiveScoreItem");
        }
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

    public ScoreBoardItem getScoreBoardItem() {
        return scoreBoardItem;
    }

    public int getScore() {
        return score;
    }
}
