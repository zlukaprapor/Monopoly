package edu.sumdu.monopoly;

/**
 * Проміжний клас для комірок, які можуть мати власника
 * Суперклас для PropertyCell, RailRoadCell та UtilityCell
 */
public abstract class OwnedCell extends Cell {
    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public abstract void playAction();

    /**
     * Додає цю власність до колекцій гравця
     * Кожен підклас реалізує свою специфічну логіку
     */
    public abstract void addToPlayer(Player player);

    /**
     * Видаляє цю власність з колекцій гравця
     * Кожен підклас реалізує свою специфічну логіку
     */
    public abstract void removeFromPlayer(Player player);

    /**
     * Виконує покупку цієї власності гравцем
     * Кожен підклас реалізує свою специфічну логіку
     */
    public abstract void purchase(Player player);
}
