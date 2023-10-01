package yatzy;

import javafx.scene.control.Label;

import java.util.*;

public class ScoreBoard {
    private static ScoreBoard instance;

    private int currantPlayer = -1;

    private List<String> players;

    private int numberOfDice;

    private List<List<ScoreBoardItem>> scoreBoard;

    private List<ScoreItem> scoreSheetList;

    private SaveData saveData= new SaveData();

    public static ScoreBoard getInstance() {
        if (instance == null) {
            throw new IllegalStateException("you haven't started a yatzy game yet!");
        }
        return instance;
    }

    public static ScoreBoard createNewYatzyState(List<String> players, int numberOfDice) {
        instance = new ScoreBoard(players, numberOfDice);
        return instance;
    }

    public static ScoreBoard loadYatzyStateFromFile(String fileName) {
        instance = new ScoreBoard(fileName);
        return instance;
    }

    private ScoreBoard(String filName) {
        saveData = new SaveData(filName);
        this.numberOfDice = saveData.loadNumberOfDIce();
        this.currantPlayer = saveData.LoadCurrantPlayer();
        this.players = saveData.LoadPlayers();
        List<List<Integer>> scoreMatrix = saveData.LoadScoreMatrix();
        setScoreSheetList();
        this.scoreBoard = new ArrayList<>();
        for (List<Integer> scoreList: scoreMatrix) {
            List<ScoreBoardItem> scoreColumn = new ArrayList<>();
            int i = 0;
            for (Integer score: scoreList) {
                scoreColumn.add(new ScoreBoardItem(score, scoreSheetList.get(i)));
                i++;
        }
            this.scoreBoard.add(scoreColumn);
        }
    }

//    private ScoreBoard(String fileName) {
//        try {
//            setScoreSheetList();
//            FileReader reader = new FileReader(fileName);
//            Scanner scanner = new Scanner(reader);
//            this.numberOfDice = Integer.parseInt(scanner.nextLine().split(" ")[0]);
//            System.out.println(numberOfDice);
//            this.currantPlayer = Integer.parseInt(scanner.nextLine().split(" ")[0]);
//            System.out.println(currantPlayer);
//            this.players = Arrays.stream(scanner.nextLine().split(",")).toList();
//            System.out.println(players);
//            scoreBoard = new ArrayList<>();
//            while (scanner.hasNext()) {
//                List<Integer> scoreList = Arrays.stream(scanner.nextLine().split(","))
//                        .mapToInt(Integer::parseInt)
//                        .boxed()
//                        .toList();
//                List<ScoreBoardItem> scoreColumn = new ArrayList<>();
//                int i = 0;
//                for (ScoreItem scoreItem: scoreSheetList) {
//                    scoreColumn.add(new ScoreBoardItem(scoreList.get(i), scoreItem));
//                    System.out.print(scoreList.get(i) + ",");
//                    i++;
//                }
//                System.out.println();
//                scoreBoard.add(scoreColumn);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private ScoreBoard(List<String> players, int numberOfDice) {
        this.players = new ArrayList<>(players);
        if (numberOfDice < 1) {
            throw new IllegalArgumentException(numberOfDice + " is illegal because the game needs at least 1 die");
        }
        this.numberOfDice = numberOfDice;

        // define the score sheet and scoring functions
        setScoreSheetList();

        // make a score board and add a column for each player
        scoreBoard = new ArrayList<>();
        for (String s: players) {
            List<ScoreBoardItem> scoreColumn = new ArrayList<>();
            for (ScoreItem scoreItem: scoreSheetList) {
                scoreColumn.add(new ScoreBoardItem(scoreItem));
            }
            scoreBoard.add(scoreColumn);
        }
    }

    public String next() {
        currantPlayer++;
        if (currantPlayer == players.size()) {
            currantPlayer = 0;
        }
        if (scoreBoard.get(currantPlayer).stream()
                .filter(ScoreBoardItem::isSelectable)
                .count() > 0) {
            return players.get(currantPlayer);
        } else {
            return null;
        }
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public List<ListItem> getListItems() {
        List<ListItem> listItemList = new ArrayList<>();
        scoreBoard.get(currantPlayer).stream()
                .filter(item -> item.isSelectable())
                .forEach(item -> listItemList.add
                        (new ListItem(item.getScoreItem().getName(), item)));
        return listItemList;
    }

    public void select(ListItem listItem) {
        listItem.getScoreBoardItem().setScore(listItem.getScore());
        if (scoreBoard.get(currantPlayer).stream()
                .filter(item -> item.getScoreItem().getKeySet().contains(ScoreItem.UPDATABLE))
                .allMatch(item -> item.getScoreItem() instanceof PassivScoreItem)) {
        scoreBoard.get(currantPlayer).stream()
                .filter(item -> item.getScoreItem().getKeySet().contains(ScoreItem.UPDATABLE))
                .forEach(item -> item.setScore(((PassivScoreItem)item.getScoreItem()).getUpdate().apply(scoreBoard.get(currantPlayer))));
        } else {
            throw new IllegalStateException("Must be Active score item");
        }
    }

    public int getScoreSheetListSize() {
        return scoreSheetList.size();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void setScoreLabelsText(List<List<Label>> scoreLabels) {
        for (int i = 0; i < players.size(); i++) {
            scoreLabels.get(i + 1).get(0).setText(players.get(i));
        }
        for (int i = 0; i < scoreSheetList.size(); i++) {
            scoreLabels.get(0).get(i + 1).setText(scoreSheetList.get(i).getName());
        }
    }

    public void updateScoreLabels(List<List<Label>> scoreLabels) {
        for (int x = 0; x < scoreBoard.size(); x++) {
            for (int y = 0; y < scoreBoard.get(0).size(); y++) {
                scoreLabels.get(x + 1).get(y + 1).setText(scoreBoard.get(x).get(y).toString());
            }
        }
    }

    private void setScoreSheetList() {
        List<ScoreItem> scoreSheetList = new ArrayList<>();
        scoreSheetList.add(new ActiveScoreItem("Ones", ActiveScoreItem.ones, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Twos", ActiveScoreItem.twos, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Threes", ActiveScoreItem.threes, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Fours", ActiveScoreItem.fours, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Fives", ActiveScoreItem.fives, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Sixes", ActiveScoreItem.sixes, ScoreItem.SELECTABLE, ScoreItem.SUM, ScoreItem.TOTAL));
        scoreSheetList.add(new PassivScoreItem("Sum", PassivScoreItem.sum, ScoreItem.UPDATABLE, ScoreItem.BONUS));
        scoreSheetList.add(new PassivScoreItem("Bonus", PassivScoreItem.bonus, ScoreItem.UPDATABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("One pair", ActiveScoreItem.onePair, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Two pairs", ActiveScoreItem.twoPairs, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Three of a kind", ActiveScoreItem.threeOfAKind, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Fore of a kind", ActiveScoreItem.foreOfAKind, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Small straight", ActiveScoreItem.smallStrait, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Big strait", ActiveScoreItem.bigStrait, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("House", ActiveScoreItem.house, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("Chance", ActiveScoreItem.chance, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new ActiveScoreItem("yatzy", ActiveScoreItem.yatzy, ScoreItem.SELECTABLE, ScoreItem.TOTAL));
        scoreSheetList.add(new PassivScoreItem("Total", PassivScoreItem.total, ScoreItem.UPDATABLE));
        this.scoreSheetList = scoreSheetList;
    }



    public void gameStateToString() {
        StringBuilder saveString = new StringBuilder();
        saveString.append(numberOfDice + "\n");
        saveString.append(currantPlayer + "\n");
        for (String player : players) {
            saveString.append(player + ",");
        }
        saveString.append("\n");
        for (List<ScoreBoardItem> scoreBoardItemList : scoreBoard) {
            for (ScoreBoardItem scoreBoardItem : scoreBoardItemList) {
                saveString.append(scoreBoardItem.getScore() + ",");
            }
            saveString.append("\n");
        }
        saveData.saveGame(saveString.toString());
    }
//    public void saveGame() {
//        try {
//            PrintWriter saveWriter = new PrintWriter(new FileWriter("saveData.txt"));
//            saveWriter.println(numberOfDice);
//            saveWriter.println(currantPlayer);
//            for (String player: players) {
//                saveWriter.print(player + ",");
//            }
//            saveWriter.println();
//            for (List<ScoreBoardItem> scoreBoardItemList: scoreBoard) {
//                for (ScoreBoardItem scoreBoardItem: scoreBoardItemList) {
//                    saveWriter.print(scoreBoardItem.getScore() + ",");
//                    scoreBoardItem.getScoreItem();
//                }
//                saveWriter.println();
//            }
//            saveWriter.flush();
//            saveWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String getWinner() {
        List<Integer> totalList = new ArrayList<>();
        int winner = 0;
        int counter = 0;
        for (List<ScoreBoardItem> scoreBoardItemList: scoreBoard) {
            scoreBoardItemList.get(scoreBoardItemList.size() - 1);
            if (scoreBoardItemList.get(winner).getScore() < scoreBoardItemList.get(counter).getScore()) {
                winner = counter;
            }
            counter++;
        }
        return players.get(winner);
    }
}
