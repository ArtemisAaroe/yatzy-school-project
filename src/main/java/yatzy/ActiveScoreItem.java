package yatzy;

import java.util.function.Function;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActiveScoreItem implements ScoreItem {

    private Set<String> keySet = new HashSet<>();

    private Function<List<Dice>, Integer> function;

    private String name;

    public ActiveScoreItem(String name, Function<List<Dice>, Integer> function, String...keys) {
        this.name = name;
        this.function = function ;
        keySet.add(SELECTABLE);
        for (String key: keys) {
            keySet.add(key);
        }
    }

    public Function<List<Dice>, Integer> getFunction() {
        return function;
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
        return name;
    }

    static Function<List<Dice>, Integer> ones = diecList -> {
        return (int)diecList.stream()
                .filter(die -> die.getFace() == 1)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> twos = diceList -> {
        return (int) diceList.stream()
                .filter(die -> die.getFace() == 2)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> threes = diceList -> {
        return (int) diceList.stream()
                .filter(die -> die.getFace() == 3)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> fours = diceList -> {
        return (int) diceList.stream()
                .filter(die -> die.getFace() == 4)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> fives = diceList -> {
        return (int) diceList.stream()
                .filter(die -> die.getFace() == 5)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> sixes = diceList -> {
        return (int) diceList.stream()
                .filter(die -> die.getFace() == 6)
                .mapToInt(item -> item.getFace())
                .sum();
    };

    static Function<List<Dice>, Integer> onePair = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        int pairsCount = 2;
        int sum = 0;
        for (int i = 5; i > -1; i--) {
            if (map[i] >= 2 ) {
                return (i + 1) * 2;
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> twoPairs = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        int pairsCount = 2;
        int sum = 0;
        for (int i = 5; i > -1; i--) {
            if (map[i] >= 2) {
                sum += (i + 1) * 2;
                pairsCount--;
                if (pairsCount == 0) {
                    return sum;
                }
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> threeOfAKind = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        for (int i = 5; i > -1; i--) {
            if (map[i] >= 3) {
                return (i + 1) * 3;
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> foreOfAKind = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        for (int i = 5; i > -1; i--) {
            if (map[i] >= 4) {
                return (i + 1) * 4;
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> smallStrait = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        for (int i = 0; i < 5; i++) {
            if (map[i] < 1) {
                break;
            }
            if (i == 4) {
                return 15;
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> bigStrait = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        for (int i = 1; i <= 5; i++) {
            if (map[i] < 1) {
                break;
            }
            if (i == 5) {
                return 20;
            }
        }
        return 0;
    };

    static Function<List<Dice>, Integer> house = diceList -> {
        int[] map = new int[6];
        diceList.forEach(die -> map[die.getFace() - 1]++);
        int treeOfAKindPointer = 0;
        int pairPointer = 0;
        for (int i = 5; i > -1; i--) {
            if (treeOfAKindPointer == 0 && map[i] >= 3) {
                treeOfAKindPointer = i + 1;
                continue;
            }
            if (pairPointer == 0 && map[i] >= 2) {
                pairPointer = i + 1;
            }

        }
        if (treeOfAKindPointer != 0 && pairPointer != 0) {
            return (treeOfAKindPointer * 3) + (pairPointer * 2);
        }
        return 0;
    };

    static Function<List<Dice>, Integer> chance = diceList -> {
        return diceList.stream().mapToInt(Dice::getFace).sum();
    };

    static Function<List<Dice>, Integer> yatzy = diceList -> {
        if (diceList.stream()
                .mapToInt(Dice::getFace)
                .allMatch(face -> face == diceList.get(0).getFace())) {
            return 50;
        }
        return 0;
    };

}
