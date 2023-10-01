package yatzy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class PassivScoreItem implements ScoreItem{

    private Set<String> keySet = new HashSet<>();

    private Function<List<ScoreBoardItem>, Integer> function;

    private String name;

    public PassivScoreItem(String name, Function<List<ScoreBoardItem>, Integer> function, String...keys) {
        this.name = name;
        this.function = function;
        keySet.add(UPDATABLE);
        for (String key: keys) {
            keySet.add(key);
        }
    }

    @Override
    public Set<String> getKeySet() {
        return new HashSet<>(keySet);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public Function<List<ScoreBoardItem>, Integer> getUpdate() {
        return function;
    }

    static Function<List<ScoreBoardItem>, Integer> sum = scoreList -> {
        return scoreList.stream()
                .filter(item -> item.getScoreItem().getKeySet().contains(ScoreItem.SUM))
                .mapToInt(ScoreBoardItem::getScore)
                .sum();
    };

    static Function<List<ScoreBoardItem>, Integer> bonus = scoreList -> {
        int sum = scoreList.stream()
                .filter(item -> item.getScoreItem().getKeySet().contains(ScoreItem.BONUS))
                .mapToInt(ScoreBoardItem::getScore)
                .sum();
        if (sum < 63 ) {
            return 0;
        } else {
            return 50;
        }
    };

    static Function<List<ScoreBoardItem>, Integer> total = scoreList -> {
        return scoreList.stream()
                .filter(Item -> Item.getScoreItem().getKeySet().contains(ScoreItem.TOTAL))
                .mapToInt(ScoreBoardItem::getScore)
                .sum();
    };
}
