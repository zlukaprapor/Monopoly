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
}