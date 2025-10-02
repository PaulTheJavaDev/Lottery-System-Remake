package de.pls.home;

import java.util.*;

public class Lottery {

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    private final int lotteryTicketCost;

    private int accountMoney;
    private final double defaultMultiplier;
    private double multiplier;

    private final int winningNumberAmount;
    private final int highestPossibleNumber;
    private final int lowestPossibleNumber;

    {
        lotteryTicketCost = 20;
        accountMoney = 1000;
        defaultMultiplier = 1;
        multiplier = defaultMultiplier;

        winningNumberAmount = 6;
        highestPossibleNumber = 50;
        lowestPossibleNumber = 1;
    }

    private HashSet<Integer> winningNumbers;

    /**
     * Generates the Winning-Numbers of the currently ongoing game
     */
    private void generateWinningNumbers() {

        var temporaryWinningNumbers = new HashSet<Integer>();
        int countOfTheWinningNumbers = 0;

        while (countOfTheWinningNumbers != winningNumberAmount) {

            final int number = random.nextInt(lowestPossibleNumber, highestPossibleNumber) + 1;

            if (!temporaryWinningNumbers.add(number)) {
                continue;
            }
            countOfTheWinningNumbers += 1;
        }

        winningNumbers = temporaryWinningNumbers;
    }

    /**
     * Gets and Compares the User Numbers with the Winning ones
     */
    private void getAndCompareUserNumbers() {

        // Start Generation
        generateWinningNumbers();

        System.out.println("Hello dear User, would you like to enroll in the Lottery?");
        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("no")) {
            System.exit(0);
        }

        // Purchase of a ticket
        accountMoney -= lotteryTicketCost;

        var userNums = new HashSet<Integer>();

        // Gets the User Inputs
        while (userNums.size() != 6) {

            System.out.println("Please enter a unique Number:");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (!userNums.add(input)) {
                System.out.println("Enter a unique Number!\n");
                continue;
            }

            if (input > 50 || input < 1) {
                System.out.println("Please enter a valid Number!\n");
                continue;
            }

            userNums.add(input);
        }

        // Calculates the Winning Amount
        int matchingCount = (int) userNums.stream()
                .filter(number -> winningNumbers.stream().anyMatch(winningNumber -> Objects.equals(winningNumber, number)))
                .count();

        if (matchingCount == 6) {
            System.out.println("Congratulations you won the Jackpot!");
            multiplier = 1.5;
        } else {
            System.out.println("You matched " + matchingCount + " Numbers!");
            multiplier = -1.5;
        }

        // Reset multiplier and add Money to account
        double wonMoney = matchingCount * (lotteryTicketCost * multiplier);
        accountMoney += (int) wonMoney;
        multiplier = defaultMultiplier;

        System.out.println("\n" + getAccountMoney());
    }

    public void startLottery() {
        getAndCompareUserNumbers();
    }

    public String getAccountMoney() {
        return "You currently have " + accountMoney + "€";
    }
}