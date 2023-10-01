package yatzy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SaveData {
    private PrintWriter saveWriter;

    private String saveFile;

    private Scanner saveLoader;

    public SaveData() {
        saveFile = "saveData.txt";
    }

    public SaveData(String saveFile) {
        this.saveFile = saveFile;
    }

    private void startSaveWriter() {
        try {
            saveWriter = new PrintWriter(new FileWriter(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startSaveLoader() {
        try {
            saveLoader = new Scanner(new FileReader(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGame(String saveString) {
        startSaveWriter();
        saveWriter.println(saveString);
        saveWriter.flush();
        saveWriter.close();
    }

    public int loadNumberOfDIce() {
        startSaveLoader();
        return Integer.parseInt(saveLoader.nextLine().split(" ")[0]);
    }

    public int LoadCurrantPlayer() {
        startSaveLoader();
        saveLoader.nextLine();
        return Integer.parseInt(saveLoader.nextLine().split(" ")[0]);
    }

    public List<String> LoadPlayers() {
        startSaveLoader();
        saveLoader.nextLine();
        saveLoader.nextLine();
        return Arrays.stream(saveLoader.nextLine().split(",")).toList();
    }

    public List<List<Integer>> LoadScoreMatrix() {
        startSaveLoader();
        saveLoader.nextLine();
        saveLoader.nextLine();
        saveLoader.nextLine();
        List<List<Integer>> scoreMatrix = new ArrayList<>();
        while (saveLoader.hasNext()) {
            scoreMatrix.add(Arrays.stream(saveLoader.nextLine().split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toList());
        }
        return scoreMatrix;
    }

    public boolean isValidSaveFile() {
        startSaveLoader();
        if (!saveLoader.hasNextInt() || !saveLoader.hasNext()) {
            return false;
        }
        if (saveLoader.nextInt() < 1) {
            return false;
        }
        saveLoader.nextLine();
        if (!saveLoader.hasNextInt() || !saveLoader.hasNext()) {
            return false;
        }
        int currentPlayer = saveLoader.nextInt();
        saveLoader.nextLine();
        if (!saveLoader.hasNext()) {
            return false;
        }
        int numberOfPlayers = (int) Arrays.stream(saveLoader.nextLine().split(",")).count();
        if (-1 > currentPlayer || currentPlayer >= numberOfPlayers) {
            return false;
        }
        if (!saveLoader.hasNext()) {
            return false;
        }

        int scoreItemsCount = (int) Arrays.stream(saveLoader.nextLine().split(",")).count();
        numberOfPlayers--;
        while (numberOfPlayers > 0) {
            if (!saveLoader.hasNext()) {
                return false;
            }
            if (scoreItemsCount != Arrays.stream(saveLoader.nextLine().split(",")).count()) {
                return false;
            }
            numberOfPlayers--;
        }
        if (saveLoader.hasNext()) {
            return false;
        }
        return true;
    }
}
