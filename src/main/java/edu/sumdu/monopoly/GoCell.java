package edu.sumdu.monopoly;

public class GoCell extends Cell {
    public GoCell() {
        super.setName("Go");
        // setAvailable(false) видалено, бо метод isAvailable()
        // за замовчуванням повертає false в базовому класі Cell
    }

    public void playAction() {
    }

    void setName(String name) {
        // Порожня імплементація - ім'я GoCell не може бути змінене
    }
}