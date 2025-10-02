package de.pls.home;

import de.pls.home.User.User;

import java.util.*;

public class Lottery {

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final User user;

    private final int lotteryTicketCost;

    private final double defaultMultiplier;
    private double multiplier;

    private final int winningNumberRequiredAmount;
    private final int highestPossibleNumber;
    private final int lowestPossibleNumber;

    private final double winMultiplier;
    private final double lossMultiplier;

    // Money related
    {
        user = new User();

        lotteryTicketCost = 20;
    }

    // Winning Numbers
    {
        winningNumberRequiredAmount = 6;

        highestPossibleNumber = 50;
        lowestPossibleNumber = 1;
    }

    // Multipliers
    {
        defaultMultiplier = 1; // expand exponentially on more matching numbers
        winMultiplier = 5;
        lossMultiplier = -1.5;

        multiplier = defaultMultiplier;
    }

    private HashSet<Integer> winningNumbers;

    /**
     * Starts the Game | called externally
     */
    public void startLottery() {

        showMenu();

    }

    /**
     * Generates the Winning-Numbers of the currently ongoing game
     */
    private void generateWinningNumbers() {

        var temporaryWinningNumbers = new HashSet<Integer>();
        int countOfTheWinningNumbers = 0;

        while (countOfTheWinningNumbers != winningNumberRequiredAmount) {

            final int number = random.nextInt(lowestPossibleNumber, highestPossibleNumber) + 1;

            if (!temporaryWinningNumbers.add(number)) {
                continue;
            }
            countOfTheWinningNumbers += 1;
        }

        winningNumbers = temporaryWinningNumbers;
        System.out.println(winningNumbers);
    }

    /**
     * Gets and Compares the User Numbers with the Winning ones
     */
    private void processUserNumbers() {

        // Start Generation
        generateWinningNumbers();

        // Purchase of a ticket
        user.adjustMoney(-lotteryTicketCost);

        HashSet<Integer> userNums = new HashSet<>();

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

        calculateWinningAmount(userNums);
    }

    /**
     * Calculates the Winning Amount
     * @param userNums Needed for accessing the User's selected Numbers
     */
    private void calculateWinningAmount(final HashSet<Integer> userNums) {
        int matchingCount = (int) userNums.stream()
                .filter(number -> winningNumbers.stream().anyMatch(winningNumber -> Objects.equals(winningNumber, number)))
                .count();

        if (matchingCount == winningNumberRequiredAmount) {
            System.out.println("Congratulations you won the Jackpot!");
            multiplier = winMultiplier;
        } else {
            System.out.println("You matched " + matchingCount + " Numbers!");
            multiplier = lossMultiplier;
        }

        // Reset multiplier and add Money to account
        user.adjustMoney( (int) (matchingCount * (lotteryTicketCost * multiplier)));
        multiplier = defaultMultiplier;

        System.out.println("\n" + getAccountMoney());

        showMenu();
    }

    /**
     * Shows you all the different Options you can select at the Menu
     */
    private void showMenu() {
        System.out.println("+-----------------------------------------------+");
        System.out.println("|                 Lottery Menu                  |");
        System.out.println("|               Select an Option                |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("|  1. Start the game                            |");
        System.out.println("|  2. My Account                                |");
        System.out.println("|  3. Statistics                                |");
        System.out.println("|  4. Exit the Program                          |");
        System.out.println("+-----------------------------------------------+");

        int response = scanner.nextInt();

        respondToInputAfterMenu(response);
    }

    private void respondToInputAfterMenu(final int outputOption) {

        // TODO: Instead of passing a number, which could mean anything, pass an Enum

        switch (outputOption) {
            case 1: processUserNumbers();
                    break;
            case 2:
                System.out.println(user);
                showMenu();
                break;
            case 3:
                System.out.println("Nothing to show here right now #3");
            case 4:
                System.exit(0);
            default:
                System.out.println("Something went wrong, I ain't tellin tho..");
        }
    }

    /**
     * Method to give back to the user on how much money they have
     * @return Formatted available Money
     */
    private String getAccountMoney() {
        return "You currently have " + user.getMoney() + "€";
    }
}