package edu.sumdu.monopoly;

public class UtilityCell extends OwnedCell {

    public static final String COLOR_GROUP = "UTILITY";
    private static int PRICE;

    @Override
    public void addToPlayer(Player player) {
        player.addUtility(this);
        player.incrementColorGroup(UtilityCell.COLOR_GROUP);
    }

    @Override
    public void removeFromPlayer(Player player) {
        player.removeUtility(this);
        player.decrementColorGroup(UtilityCell.COLOR_GROUP);
    }

    @Override
    public void purchase(Player player) {
        player.buyProperty(this, this.getPrice());
    }

    public static void setPrice(int price) {
        UtilityCell.PRICE = price;
    }

    public int getPrice() {
        return UtilityCell.PRICE;
    }

    public int getRent(int diceRoll) {
        if(player.numberOfUtil() == 1) {
            return diceRoll * 4;
        } else if (player.numberOfUtil() >= 2) {
            return diceRoll * 10;
        }
        return 0;
    }

    public void playAction() {
        Player currentPlayer = null;
        if(!isAvailable()) {
            currentPlayer = GameMaster.instance().getCurrentPlayer();
            if(player != currentPlayer) {
                GameMaster.instance().utilRollDice();
                int diceRoll = GameMaster.instance().getUtilDiceRoll();
                currentPlayer.payRentTo(player, getRent(diceRoll));
            }
        }
    }
}