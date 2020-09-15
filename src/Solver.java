/**
 * Author - Cameron Mathis
 *
 * This class handles all the necessary methods to solve the Akari puzzle.
 */
public class Solver {
    // Final Variables
    public static final int EMPTY = 100;
    public static final int BULB = 99;
    public static final int LIT = 88;

    /**
     * Attempts to solve the problem by placing bulbs around black boxed where it can only be performed one way and then
     * by randomly placing bulbs.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the solution as a two dimensional array.
     */
    static int[][] solveProblem( int[][] table, int tableX, int tableY) {
        boolean bulbsArePlaceable= true;
        while (bulbsArePlaceable) {
            bulbsArePlaceable = false;
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    if (table[x][y] <= 5) {
                        // check to see if the number of white cells corresponds to the number on the black cell
                        if (table[x][y] == countCellsAround(table, tableX, tableY, x, y, EMPTY)) {
                            // place bulbs around the black cell
                            // checks to see if the cell above exist, is empty, and if a bulb can be placed there
                            if ((y > 0) && (table[x][y - 1] == EMPTY) && canPlaceBulb(table, tableX, tableY, x, y)) {
                                table[x][y - 1] = BULB;
                                bulbsArePlaceable = true;
                            }
                            // checks to see if the cell to the left exist, is empty, and if a bulb can be placed there
                            if ((x > 0) && (table[x - 1][y] == EMPTY) && canPlaceBulb(table, tableX, tableY, x, y)) {
                                table[x - 1][y] = BULB;
                                bulbsArePlaceable = true;
                            }
                            // checks to see if the cell to the right exist, is empty, and if a bulb can be placed there
                            if ((x < tableX - 1) && (table[x + 1][y] == EMPTY) && canPlaceBulb(table, tableX, tableY, x, y)) {
                                table[x + 1][y] = BULB;
                                bulbsArePlaceable = true;
                            }
                            // checks to see if the cell below exist, is empty, and if a bulb can be placed there
                            if ((y < tableY - 1) && (table[x][y + 1] == EMPTY) && canPlaceBulb(table, tableX, tableY, x, y)) {
                                table[x][y + 1] = BULB;
                                bulbsArePlaceable = true;
                            }
                        }
                    }
                }
            }
        }

        return table;
    }

    /**
     * Checks to see the number of a specified parameter around a given cell.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @param x the x-value of the cell to check.
     * @param y the y-value of the cell to check.
     * @param checkFor what we are checking the cells around for.
     * @return the number of empty cells around a black cell.
     */
    private static int countCellsAround(int[][] table, int tableX, int tableY, int x, int y, int checkFor) {
        int number = 0;

        // checks to see if the cell above exist and is what is specified
        if ((y > 0) && (table[x][y - 1] == checkFor)) {
            number++;
        }
        // checks to see if the cell to the left exist and is what is specified
        if ((x > 0) && (table[x - 1][y] == checkFor)) {
            number++;
        }
        // checks to see if the cell to the right exist and is what is specified
        if ((x < tableX - 1) && (table[x + 1][y] == checkFor)) {
            number++;
        }
        // checks to see if the cell below exist and is what is specified
        if ((y < tableY - 1) && (table[x][y + 1] == checkFor)) {
            number++;
        }

        return number;
    }

    /**
     * Checks to see if a bulb can be placed in a cell without invalidating constraints.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @param x the x-value of the cell to check.
     * @param y the y-value of the cell to check.
     * @return the if a bulb can be placed.
     */
    private static boolean canPlaceBulb(int[][] table, int tableX, int tableY, int x, int y) {
        // check if there is a 0 black cell adjacent
        if (countCellsAround(table, tableX, tableY, x, y, 0) != 0) {
            return false;
        }

        // check to see if there is a black cell around that already has max bulbs
        if ((y > 0) && (table[x][y - 1] < 5)) {
            if (countCellsAround(table, tableX, tableY, x, y - 1, BULB) == table[x][y - 1]) {
                return false;
            }
        }
        if ((x > 0) && (table[x - 1][y] < 5)) {
            if (countCellsAround(table, tableX, tableY, x - 1, y, BULB) == table[x - 1][y]) {
                return false;
            }
        }
        if ((x < tableX) && (table[x + 1][y] < 5)) {
            if (countCellsAround(table, tableX, tableY, x + 1, y, BULB) == table[x + 1][y]) {
                return false;
            }
        }
        if ((y < tableY) && (table[x][y + 1] < 5)) {
            if (countCellsAround(table, tableX, tableY, x, y + 1, BULB) == table[x][y + 1]) {
                return false;
            }
        }

        // checks to see if there is already a bulb shinning on the cell
        // checks for another bulb shinning on it from the bottom
        for (int y2 = y + 1; y2 < tableY; y2++) {
            if (table[x][y2] != BULB) {
                if (table[x][y2] != EMPTY) {
                    break;
                }
            } else {
                return false;
            }
        }
        // checks for another bulb shinning on it from the left
        for (int x2 = x - 1; x2 >= 0; x2--) {
            if (table[x2][y] != BULB) {
                if (table[x2][y] != EMPTY) {
                    break;
                }
            } else {
                return false;
            }
        }
        // checks for another bulb shinning on it from the right
        for (int x2 = x + 1; x2 < tableX; x2++) {
            if (table[x2][y] != BULB) {
                if (table[x2][y] != EMPTY) {
                    break;
                }
            } else {
                return false;
            }
        }
        // checks for another bulb shinning on it from the top
        for (int y2 = y - 1; y2 >= 0; y2--) {
            if (table[x][y2] != BULB) {
                if (table[x][y2] != EMPTY) {
                    break;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Counts the number of black cells in a problem table.
     *
     * @param table the problem table we are counting black cells in.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the number of black cells in the problem table.
     */
    static int countNumberOfBlackCells(int[][] table, int tableX, int tableY) {
        int numberOfBlackCells = 0;
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                if (table[x][y] <= 5) {
                    numberOfBlackCells++;
                }
            }
        }
        return numberOfBlackCells;
    }

    /**
     * Checks to see if the problem solution is valid. If the solution is valid then it returns a fitness which equals
     * the number of total white cells minus the total number of bulbs.
     *
     * @param originalTable the original problem table.
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the number of lit cells, assuming the solution is valid. If the solution is not valid then return 0.
     */
    static int checkSolution(int[][] originalTable, int[][] table, int tableX, int tableY) {
        int[][] litCells = cloneArray(originalTable);
        int numberOfCellsLit = 0;

        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                // check to see if all the black cell constraints are satisfied
                if ((table[x][y] <= 4)) {
                    if (countCellsAround(table, tableX, tableY, x, y, BULB) != table[x][y]) {
                        return 0;
                    }
                }

                // check to see if two bulbs are shinning on each other
                if (table[x][y] == BULB) {
                    // checks for another bulb shinning on it from the bottom
                    for (int y2 = y + 1; y2 < tableY; y2++) {
                        if (table[x][y2] != BULB) {
                            if (table[x][y2] != EMPTY) {
                                break;
                            }
                        } else {
                            return 0;
                        }
                    }
                    // checks for another bulb shinning on it from the left
                    for (int x2 = x - 1; x2 >= 0; x2--) {
                        if (table[x2][y] != BULB) {
                            if (table[x2][y] != EMPTY) {
                                break;
                            }
                        } else {
                            return 0;
                        }
                    }
                    // checks for another bulb shinning on it from the right
                    for (int x2 = x + 1; x2 < tableX; x2++) {
                        if (table[x2][y] != BULB) {
                            if (table[x2][y] != EMPTY) {
                                break;
                            }
                        } else {
                            return 0;
                        }
                    }
                    // checks for another bulb shinning on it from the top
                    for (int y2 = y - 1; y2 >= 0; y2--) {
                        if (table[x][y2] != BULB) {
                            if (table[x][y2] != EMPTY) {
                                break;
                            }
                        } else {
                            return 0;
                        }
                    }
                }

                // checks to how many cells are lit
                if (table[x][y] == BULB) {
                    // count cells with bulb
                    litCells[x][y] = LIT;
                    // count cells underneath
                    for (int y2 = y + 1; y2 < tableY; y2++) {
                        if (table[x][y2] > 5) {
                            litCells[x][y2] = LIT;
                        } else {
                            break;
                        }
                    }
                    // count cells to the left
                    for (int x2 = x - 1; x2 >= 0; x2--) {
                        if (table[x2][y] > 5) {
                            litCells[x2][y] = LIT;
                        } else {
                            break;
                        }
                    }
                    // count cells to the right
                    for (int x2 = x + 1; x2 < tableX; x2++) {
                        if (table[x2][y] > 5) {
                            litCells[x2][y] = LIT;
                        } else {
                            break;
                        }
                    }
                    // count cells above
                    for (int y2 = y - 1; y2 >= 0; y2--) {
                        if (table[x][y2] > 5) {
                            litCells[x][y2] = LIT;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        // iterate over lit cells array to count the total number of lit cells
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++)
                if (litCells[x][y] == LIT) {
                    numberOfCellsLit++;
                }
        }

        return numberOfCellsLit;
    }

    /**
     * Clones the provided array.
     *
     * @param source the array being copied.
     * @return a new clone of the provided array.
     */
    public static int[][] cloneArray(int[][] source) {
        int length = source.length;
        int[][] target = new int[length][source[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(source[i], 0, target[i], 0, source[i].length);
        }
        return target;
    }
}