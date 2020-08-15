package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {

        Main main = new Main();
        main.start();

    }

    private void start() {
        System.out.println("Input the length of secret code:");
        int codeLength = getLength();

        System.out.println("Input the length of the dictionary:");
        int dictionaryLength = getDictionaryLength(codeLength);

        String secretCode = generateSecretCode(codeLength, dictionaryLength);

        displayAstericsMessageWithRange(secretCode, dictionaryLength);

        startGuessing(secretCode);

    }

    private int getLength() {
        int length = -1;
        String userInput = "";
        try {
            userInput = scanner.nextLine().trim();
            length = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.printf("invalid input error: \"%s\" isn't a valid number.", userInput);
            System.exit(0);
        }

        if (length > 36 || length < 1) {
            System.out.printf("error invalid input: can't generate a secret number with a length of %d", length);
            System.exit(0);
        }
        return length;
    }

    private int getDictionaryLength(int length) {
        int dictionaryLength = -1;
        String userInput = "";
        try {
            userInput = scanner.nextLine().trim();
            dictionaryLength = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.printf("invalid input error: \"%s\" isn't a valid number.", userInput);
            System.exit(0);
        }

        if (dictionaryLength > 36) {
            System.out.println("invalid input error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        if (dictionaryLength < length) {
            System.out.printf("error: it's not possible to generate a code with a length of " +
                    "%d with %d unique symbols.", length, dictionaryLength);
            System.exit(0);
        }

        return dictionaryLength;
    }

    private String generateSecretCode(int length, int dictionaryLength) {
        StringBuilder secretCode = new StringBuilder();
        String dictionary = "1234567890abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();

        while (secretCode.length() < length) {

            int randomNumber = r.nextInt(dictionaryLength);
            String ch = String.valueOf(dictionary.charAt(randomNumber));

            if (secretCode.indexOf(ch) < 0) {
                secretCode.append(ch);
            }
        }
        return secretCode.toString();
    }

    private void displayAstericsMessageWithRange(String secretCode, int dictionaryLength) {
        String secretCodeDisplay = secretCode.replaceAll("\\w", "*");
        StringBuilder range = new StringBuilder();
        range.append("(0-");
        if (dictionaryLength <= 10) {
            range.append(dictionaryLength - 1).append(").");
        } else {
            range.append("9, a-");
            range.append((char) ('a' + dictionaryLength - 11)).append(").");
        }

        System.out.println("The secret is prepared: " + secretCodeDisplay + "." + range);
    }

    private void startGuessing(String secretCode) {
        System.out.println("Okay, let's start a game! It's your turn 1:");
        int turn = 1;
        int bull = 0;
        int cow = 0;

        while (true) {
            String guess = scanner.next().trim();
            for (int i = 0; i < guess.length(); i++) {
                String singleChar = guess.substring(i, i + 1);
                int index = secretCode.indexOf(singleChar);
                if (index < 0) {
                    continue;
                }

                if (index == i) {
                    bull++;
                } else {
                    cow++;
                }
            }

            printGrades(bull, cow, turn);

            if (guess.equals(secretCode)) {
                break;
            }
            bull = 0;
            cow = 0;
            turn++;
            System.out.println("Turn " + turn + ":");
        }
        System.out.println("Congratulations! You guessed the secret code.");

    }

    private void printGrades(int bull, int cow, int turn) {
        StringBuilder grades = new StringBuilder();
        System.out.print("Grades: ");

        if (bull == 0 && cow == 0) {
            System.out.println("no match");
        }

        if (bull == 1) {
            grades.append("1 bull ");
        } else if (bull > 1) {
            grades.append(bull).append(" bulls ");
        }

        if (bull != 0 && cow > 0) {
            grades.append("and ");
        }

        if (cow == 1) {
            grades.append("1 cow");
        } else if (cow > 1) {
            grades.append(cow).append(" cows");
        }

        System.out.println(grades);
    }

}
