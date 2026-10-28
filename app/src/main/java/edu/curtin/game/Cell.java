package edu.curtin.game;

import java.util.List;

// Represents a cell in the game grid
class Cell {
    private boolean visible;
    private boolean goal;
    private String item;
    private List<String> requiredItems;
    private String itemMessage;

    public Cell() {
        visible = false;
        goal = false;
        item = null;
        itemMessage = null;
        requiredItems = null;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public boolean hasItem() {
        return item != null;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item, String message) {
        this.item = item;
        this.itemMessage = message;
    }

    public String getItemMessage() {
        return itemMessage;
    }

    public void removeItem() {
        this.item = null;
    }

    public boolean hasObstacle() {
        return requiredItems != null;
    }

    public List<String> getRequiredItems() {
        return requiredItems;
    }

    public void setObstacle(List<String> requiredItems) {
        this.requiredItems = requiredItems;
    }
}
