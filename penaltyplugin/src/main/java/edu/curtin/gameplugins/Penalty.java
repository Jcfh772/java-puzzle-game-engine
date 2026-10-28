package edu.curtin.gameplugins;

import edu.curtin.api.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

// Plugin that adds a penalty obstacle if the player takes too long to move
public class Penalty implements Plugin {
    private GameAPI api;
    private long lastMoveTime;
    private Random random = new Random();

    @Override
    public void initialize(GameAPI api) {
        this.api = api;
        this.lastMoveTime = System.currentTimeMillis();

        // Register a move callback to track player movements
        api.registerMoveCallback(new MoveCallback() {
            @Override
            public void onMove(String direction) {
                checkPenalty();
            }
        });
    }

    // Check if penalty condition is met
    private void checkPenalty() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastMoveTime;

        if (timeDiff > 5000) {
            addPenaltyObstacle();
        }

        lastMoveTime = currentTime;
    }

    // Add a penalty obstacle adjacent to the player
    private void addPenaltyObstacle() {
        int playerRow = api.getPlayerRow();
        int playerCol = api.getPlayerCol();

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        List<int[]> validPositions = new ArrayList<>();

        for (int[] dir : directions) {
            int newRow = playerRow + dir[0];
            int newCol = playerCol + dir[1];

            if (newRow >= 0 && newRow < api.getRows() &&
                    newCol >= 0 && newCol < api.getCols() &&
                    api.getItemAt(newRow, newCol) == null &&
                    !api.isObstacleAt(newRow, newCol) &&
                    !api.isGoalAt(newRow, newCol)) {
                validPositions.add(new int[] { newRow, newCol });
            }
        }

        // Place obstacle at a random valid position
        if (!validPositions.isEmpty()) {
            // Select a random position from the valid positions
            int[] pos = validPositions.get(random.nextInt(validPositions.size()));
            // Add obstacle with default requirements
            api.addObstacleAt(pos[0], pos[1], api.getObstacleRequirements());
            System.out.println("\nPenalty! New obstacle created at (" + pos[0] + ", " + pos[1] + ")");
        }
    }
}
