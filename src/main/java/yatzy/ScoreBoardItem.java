package yatzy;

public class ScoreBoardItem {
    private int score;

    private ScoreItem scoreItem;

    private boolean selectable = false;

    public ScoreBoardItem(ScoreItem scoreItem) {
        this.scoreItem = scoreItem;
        if (scoreItem.getKeySet().contains(ScoreItem.SELECTABLE)) {
            selectable = true;
        }
    }

    public ScoreBoardItem(int score, ScoreItem scoreItem) {
        if (score < 0) {
            throw new IllegalArgumentException(score + " is less than 0 and therefore illegal");
        }
        if (score == 0 && scoreItem.getKeySet().contains(ScoreItem.SELECTABLE)) {
            selectable = true;
        }
        this.score = score;
        this.scoreItem = scoreItem;
    }

    public void setScore(int score) {
        this.score = score;
        selectable = false;
    }

    public ScoreItem getScoreItem() {
        return scoreItem;
    }

    public int getScore() {
        return score;
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public String toString() {
        if (selectable == true) {
            return "";
        }
        return "" + score;
    }


}
