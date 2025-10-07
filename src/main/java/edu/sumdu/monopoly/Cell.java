package edu.sumdu.monopoly;

public abstract class Cell {
    private String name;
    protected Player player;

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPrice() {
        return 0;
    }

    /**
     * За замовчуванням комірки недоступні для придбання
     * Перевизначено в OwnedCell для комірок з власниками
     */
    public boolean isAvailable() {
        return false;
    }

    public abstract void playAction();

    void setName(String name) {
        this.name = name;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String toString() {
        return name;
    }

}