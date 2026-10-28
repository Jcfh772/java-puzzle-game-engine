package edu.curtin.api;

public interface GameAPI {
    void registerMoveCallback(MoveCallback callback);

    void registerItemCallback(ItemCallback callback);

    void registerMenuOption(String label, MenuCallback callback);

    void registerObstacleCallback(ObstacleCallback callback);

    int getPlayerRow();

    int getPlayerCol();

    void setPlayerLocation(int row, int col);

    java.util.Set<String> getInventory();

    String getLastAcquiredItem();

    void addItemToInventory(String itemName);

    void removeItemFromInventory(String itemName);

    int getRows();

    int getCols();

    String getItemAt(int row, int col);

    java.util.List<String> getObstacleRequirements();

    java.util.List<String> getObstacleRequirementsAt(int row, int col);

    boolean isObstacleAt(int row, int col);

    boolean isGoalAt(int row, int col);

    boolean isVisibleAt(int row, int col);

    void setVisibleAt(int row, int col, boolean visible);

    void addObstacleAt(int row, int col, java.util.List<String> requirements);

    void removeObstacleAt(int row, int col);

    void addItemAt(int row, int col, String itemName);

    void removeItemAt(int row, int col);

    void addGoalAt(int row, int col);

    void removeGoalAt(int row, int col);
}
