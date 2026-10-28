package edu.curtin.gameplugins;

import edu.curtin.api.*;
import java.util.Random;

// Plugin that allows the player to teleport to a random location once
public class Teleport implements Plugin {
    private GameAPI api;
    private boolean used = false;
    private Random random = new Random();

    // Initialize the plugin
    @Override
    public void initialize(GameAPI api) {
        this.api = api;

        // Register a menu option for teleportation
        api.registerMenuOption("Teleport to random location", new MenuCallback() {
            @Override
            public void onMenuSelected() {
                teleport();
            }
        });
    }

    // Teleport the player to a random location
    private void teleport() {
        if (used) {
            System.out.println("Teleport already used!");
            return;
        }

        int rows = api.getRows();
        int cols = api.getCols();

        int newRow;
        int newCol;

        // Find a random location that is not an obstacle or goal
        do {
            newRow = random.nextInt(rows);
            newCol = random.nextInt(cols);
        } while (api.isObstacleAt(newRow, newCol) || api.isGoalAt(newRow, newCol));

        api.setPlayerLocation(newRow, newCol);
        used = true;

        System.out.println("\nTeleported to (" + newRow + ", " + newCol + ")!");
    }
}