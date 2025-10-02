package de.pls.home.User;

public class User {

    private int money = 500;

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money;
    }

    /**
     * Adds money to the account negative and positive
     * @param money money being added
     */
    public void adjustMoney(final int money) {
        this.money += money;
    }

    @Override
    public String toString() {
        return "User has " + money + "€";
    }
}
