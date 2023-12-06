package org.day1;

import java.util.ArrayList;

import static org.day1.InputData.alphaNumbers;


public class Main {

    /**
     * Main method to run process grabs the requested input from InputData
     * then calls the appropriate functions to solve the puzzle
     */
    public static void main(String[] args) {
        ArrayList<String> inputArray = InputData.firstInput;
        ArrayList<Integer> valueArray = alphaNumberFinder(inputArray);
        System.out.println(calculateTotal(valueArray));
    }


    private static int calculateTotal(ArrayList<Integer> valueArray) {
        int sumValue = 0;
        for (Integer value : valueArray) {
            sumValue += value;
        }
        return sumValue;
    }

    /**
     * Takes the input of a ArrayList<String> then parses each element with a pointer
     * at both ends to locate the first and last instance of a number, adds these to
     * the working array as a single integer value (i.e. 1 and 4 become 14) then
     * returns this populated ArrayList
     *
     * @param inputData
     * @return ArrayList<Integer> list of all first and last numbers found
     */
    public static ArrayList<Integer> pincerNumberFinder(ArrayList<String> inputData) {
        // Initialise output object
        ArrayList<Integer> outputArray = new ArrayList<>();

        // Move through each element:
        for (String input : inputData) {
            // Reset the pointers and the buckets for each element
            int startPointer = 0;
            int endPointer = input.length() - 1;
            String firstValue = null;
            String lastValue = null;
            while (firstValue == null || lastValue == null) {
                if (Character.isDigit(input.charAt(startPointer))) {
                    firstValue = String.valueOf(input.charAt(startPointer));
                } else {
                    startPointer++;
                }

                if (Character.isDigit(input.charAt(endPointer))) {
                    lastValue = String.valueOf(input.charAt(endPointer));
                } else {
                    endPointer--;
                }
            }
            outputArray.add(Integer.valueOf(firstValue + lastValue));
        }
        return outputArray;
    }


    public static ArrayList<Integer> alphaNumberFinder(ArrayList<String> inputData) {
        // Initialise output object
        ArrayList<Integer> outputArray = new ArrayList<>();

        // Move through each element:
        for (String input : inputData) {
            // Reset the pointers and the buckets for each element
            int startPointer = 0;
            int endPointer = input.length() - 1;
            int extendIndex = 0;
            String firstValue = null;
            String lastValue = null;
            while (firstValue == null || lastValue == null) {
                if (Character.isDigit(input.charAt(startPointer))) {
                    firstValue = String.valueOf(input.charAt(startPointer));
                } else if (input.charAt(startPointer) == 'o' ||
                        input.charAt(startPointer) == 't' ||
                        input.charAt(startPointer) == 'f' ||
                        input.charAt(startPointer) == 's' ||
                        input.charAt(startPointer) == 'e' ||
                        input.charAt(startPointer) == 'n') {
                    firstValue = checkAndConvertAlpha(startPointer, input, ">");
                    if (firstValue == null) {
                        startPointer++;
                    }
                } else {
                    startPointer++;
                }

                if (Character.isDigit(input.charAt(endPointer))) {
                    lastValue = String.valueOf(input.charAt(endPointer));
                } else if (input.charAt(endPointer) == 'e' ||
                        input.charAt(endPointer) == 'o' ||
                        input.charAt(endPointer) == 'r' ||
                        input.charAt(endPointer) == 'x' ||
                        input.charAt(endPointer) == 'n' ||
                        input.charAt(endPointer) == 't') {
                    lastValue = checkAndConvertAlpha(endPointer, input, "<");
                    if (lastValue == null) {
                        endPointer--;
                    }
                } else {
                    endPointer--;
                }
            }
            outputArray.add(Integer.valueOf(firstValue + lastValue));
        }
        return outputArray;
    }

    private static String checkAndConvertAlpha(int startPointer, String input, String direction) {
        for (int x = 0; x < 9; x++) {
            if (direction.equals(">")) {
                // If we are moving forward then the start pointer remains the same and the endPointer changes
                if (startPointer + alphaNumbers.get(x).length() <= input.length()) {
                    if (input.substring(startPointer, startPointer + alphaNumbers.get(x).length()).equals(alphaNumbers.get(x))) {
                        return String.valueOf(x + 1);
                    }
                }
            } else {
                if (startPointer - (alphaNumbers.get(x).length()) + 1 >= 0) {
                    // If we are moving backward then the start pointer changes and the endPointer remains the same
                    if (input.substring(startPointer - (alphaNumbers.get(x).length()) + 1, startPointer + 1).equals(alphaNumbers.get(x))) {
                        return String.valueOf(x + 1);
                    }
                }
            }
        }
        return null;
    }


}