package com.JohnBieniek.Java26Demo.utility;

/** Reusable algorithm helpers for the interview demonstrations. */
public final class InterviewAlgorithmUtils {
    private InterviewAlgorithmUtils() {}

    /**
     * Marks one horizontally or vertically connected island as visited.
     *
     * @param row current row
     * @param column current column
     * @param grid mutable grid containing land as '1' and water as '0'
     */
    public static void markIslandVisited(int row, int column, char[][] grid) {
        if (row < 0
                || column < 0
                || row >= grid.length
                || column >= grid[row].length
                || grid[row][column] != '1') {
            return;
        }

        grid[row][column] = '0';
        markIslandVisited(row + 1, column, grid);
        markIslandVisited(row - 1, column, grid);
        markIslandVisited(row, column + 1, grid);
        markIslandVisited(row, column - 1, grid);
    }
}
