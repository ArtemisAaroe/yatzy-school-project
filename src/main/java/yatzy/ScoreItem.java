package yatzy;

import java.util.Set;

public interface ScoreItem {

    public String SELECTABLE = "selectable", SUM = "sum", BONUS = "bonus", UPDATABLE = "updatable", TOTAL = "total" ;
    public Set<String> getKeySet();
    public String getName();
    //public Function<List<Dice>, Integer> getFunction();
    //public Function<List<ScoreBoardItem>, Integer> getUpdate();


}
