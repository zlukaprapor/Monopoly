package edu.sumdu.monopoly;

public class RailRoadCell extends OwnedCell {
    static private int baseRent;
    static public String COLOR_GROUP = "RAILROAD";
    static private int price;

    @Override
    public void addToPlayer(Player player) {
        player.addRailroad(this);
        player.incrementColorGroup(RailRoadCell.COLOR_GROUP);
    }

    @Override
    public void removeFromPlayer(Player player) {
        player.removeRailroad(this);
        player.decrementColorGroup(RailRoadCell.COLOR_GROUP);
    }

    @Override
    public void purchase(Player player) {
        player.buyProperty(this, this.getPrice());
    }

    public static void setBaseRent(int baseRent) {
        RailRoadCell.baseRent = baseRent;
    }

    public static void setPrice(int price) {
        RailRoadCell.price = price;
    }

    public int getPrice() {
        return RailRoadCell.price;
    }

    public int getRent() {
        return RailRoadCell.baseRent * (int)Math.pow(2, player.numberOfRR() - 1);
    }

    public void playAction() {
        Player currentPlayer = null;
        if(!isAvailable()) {
            currentPlayer = GameMaster.instance().getCurrentPlayer();
            if(player != currentPlayer) {
                currentPlayer.payRentTo(player, getRent());
            }
        }
    }
}