package org.day2;

import java.util.*;

import static org.day2.InputData.bagContents1;

public class Main {

    public static void main(String[] args) {
        // Get bag data
        HashMap<String, Integer> bagContents = bagContents1;

        // Get game data
        ArrayList<String> gameData = InputData.sampleGameData1;

        // Parse game data into collection of HashMap matching bag data
        ArrayList<ArrayList<HashMap<String, Integer>>> parsedGameData = parseGameData(gameData);

        // Compare Game data to bag data and return list of possible game numbers
        ArrayList<Integer> possibleGames = getPossibleGames(parsedGameData, bagContents);

        // Output sum of game numbers
        sumAndOutputPossibleGames(possibleGames);
    }

    /**
     * Parses each input line into a HashMap<String, Integer> to match bagContents structure
     * and adds each result to an ArrayList<HashMap>
     *
     * @param gameData
     * @return
     */
    private static ArrayList<ArrayList<HashMap<String, Integer>>> parseGameData(ArrayList<String> gameData) {
        ArrayList<ArrayList<HashMap<String, Integer>>> games = new ArrayList<>();
        for (String game : gameData) {
            ArrayList<HashMap<String, Integer>> game = new ArrayList<>();
            HashMap<String, Integer> gameDetails = new HashMap<>();
            // Each game parsed will be the ArrayList Index position + 1 so no need to keep this data
            // So we can get rid of everything 1 position after the ":"
            game = game.substring(game.indexOf(":") + 1);

            // Can also remove all the spaces as data can be split on "," and ";"
            game = game.replace(" ", "");

            // Can now split it into rounds and then parse the details of each round
            String[] roundDetails = game.split(";");
            for (int x = 0; x < roundDetails.length; x++) {
                // Can split on each "," to deal with each cube on it's own
                String[] cubes = roundDetails[x].split(",");
                String number = "";
                String colour = "";
                HashMap<String, Integer> round = new HashMap<>();
                for (String cubeDetails : cubes) {
                    for (int j = 0; j < cubeDetails.length(); j++) {
                        if (Character.isDigit(cubeDetails.charAt(j))) {
                            number += cubeDetails.charAt(j);
                        } else {
                            colour += cubeDetails.charAt(j);
                        }
                    }
                    round.put(colour, Integer.valueOf(number));
                    number = "";
                    colour = "";
                }
                game.add(round);
            }
            games.add(game);
        }
        return games;
    }

    private static ArrayList<Integer> getPossibleGames(ArrayList<ArrayList<HashMap<String, Integer>>> parsedGameData, HashMap<String, Integer> bagContents) {
        // Game is considered possible until we find out otherwise
        boolean gameIsPossible = true;
        ArrayList<Integer> possibleGameIDs = new ArrayList<>();
        // So need to go through each game but use the index as this is what we need to get the ID
        for (int x = 0; x < parsedGameData.size(); x++) {
            ArrayList<HashMap<String, Integer>> game = parsedGameData.get(x);
            // And in each game we need to go through each round until we find that a game is impossible
            for (HashMap<String, Integer> round : game) {
                Iterator<Map.Entry<String, Integer>> iterator = round.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = iterator.next();
                    if (entry.getValue() > bagContents.get(entry.getKey())) {
                        gameIsPossible = false;
                    }
                }
            }
            if (gameIsPossible) {
                // Game ID is the index + 1
                possibleGameIDs.add(x+1);
            }
        }
        return possibleGameIDs;
    }

    private static void sumAndOutputPossibleGames(ArrayList<Integer> possibleGames) {
    }
}
