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
    public static final int ILLEGAL = 66;

    /**
     * Solves the puzzle.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the solution as a two dimensional array.
     */
    static int[][] solveProblem( int[][] table, int tableX, int tableY) {
        // mark illegal cells
        markIllegalCells(table, tableX, tableY);
        // place bulbs where it is forced by a black cell
        placeForcedBulbs(table, tableX, tableY);
        // light all unlit illegal cells
        lightIllegalCells(table, tableX, tableY);
        // place bulbs in empty unlit empty cells not marked illegal
        placeAllPossibleBulbs(table, tableX, tableY);
        // check is puzzle is solved
        if (!checkIfSolved(table, tableX, tableY)) {
            System.out.println("Puzzle was not solved.");
            System.exit(0);
        } // return table of solved puzzle
        return table;
    }

    /**
     * Marks everywhere that a bulb cannot be places due to black cells with zero constraint.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    private static void markIllegalCells(int[][] table, int tableX, int tableY) {
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                // check to see if cell is a 0 black
                if (table[x][y] == 0) {
                    // mark all cells around 0 black as illegal
                    // checks to see if the cell above exist and is empty
                    if (y > 0 && table[x][y - 1] == EMPTY) {
                        table[x][y - 1] = ILLEGAL;
                    } // checks to see if the cell to the left exist and is empty
                    if (x > 0 && table[x - 1][y] == EMPTY) {
                        table[x - 1][y] = ILLEGAL;
                    } // checks to see if the cell to the right exist and is empty
                    if (x < tableX - 1 && table[x + 1][y] == EMPTY) {
                        table[x + 1][y] = ILLEGAL;
                    } // checks to see if the cell below exist and is empty
                    if (y < tableY - 1 && table[x][y + 1] == EMPTY) {
                        table[x][y + 1] = ILLEGAL;
                    }
                }
            }
        }
    }

    /**
     * Places a bulb everywhere it is forced by a black cell.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    private static void placeForcedBulbs(int[][] table, int tableX, int tableY) {
        boolean bulbsArePlaceable= true;
        while (bulbsArePlaceable) {
            bulbsArePlaceable = false;
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    if (table[x][y] > 0 && table[x][y] < 5) {
                        // mark empty cells around satisfied black cells as illegal
                        if (table[x][y] == countCellsAround(table, tableX, tableY, x, y, BULB)) {
                            // checks to see if the cell above exist and is empty
                            if ((y > 0) && (table[x][y - 1] == EMPTY)) {
                                table[x][y - 1] = ILLEGAL;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell to the left exist and is empty
                            if ((x > 0) && (table[x - 1][y] == EMPTY)) {
                                table[x - 1][y] = ILLEGAL;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell to the right exist and is empty
                            if ((x < tableX - 1) && (table[x + 1][y] == EMPTY)) {
                                table[x + 1][y] = ILLEGAL;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell below exist and is empty
                            if ((y < tableY - 1) && (table[x][y + 1] == EMPTY)) {
                                table[x][y + 1] = ILLEGAL;
                                bulbsArePlaceable = true;
                            }
                        } // place bulbs around black cell if there is only one way to do so
                        else if (table[x][y] == countCellsAround(table, tableX, tableY, x, y, EMPTY)) {
                            // checks to see if the cell above exist and is empty
                            if ((y > 0) && (table[x][y - 1] == EMPTY)) {
                                table[x][y - 1] = BULB;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell to the left exist and is empty
                            if ((x > 0) && (table[x - 1][y] == EMPTY)) {
                                table[x - 1][y] = BULB;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell to the right exist and is empty
                            if ((x < tableX - 1) && (table[x + 1][y] == EMPTY)) {
                                table[x + 1][y] = BULB;
                                bulbsArePlaceable = true;
                            } // checks to see if the cell below exist and is empty
                            if ((y < tableY - 1) && (table[x][y + 1] == EMPTY)) {
                                table[x][y + 1] = BULB;
                                bulbsArePlaceable = true;
                            }
                        }
                    }
                }
            }
            // mark all lit cells
            markLitCells(table, tableX, tableY);
        }
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
     * Make sure all cells that cannot have a bulb placed in them are lit.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    private static void lightIllegalCells(int[][] table, int tableX, int tableY) {
        int numberOfPossibleSidesToCheck = 1;
        int unlitIllegalCells = countUnlitIllegalCells(table, tableX, tableY);
        while (unlitIllegalCells > 0) {
            boolean didCheckSides = false;
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    f: if (table[x][y] == ILLEGAL) {
                        // count number of sides the cell could be lit from
                        int numberOfPossibleSides = 4;
                        // checks to see if the cell above exist and is what is specified
                        if ((y > 0) && (table[x][y - 1] <= 5)) {
                            numberOfPossibleSides--;
                        }
                        // checks to see if the cell to the left exist and is what is specified
                        if ((x > 0) && (table[x - 1][y] <= 5)) {
                            numberOfPossibleSides--;
                        }
                        // checks to see if the cell to the right exist and is what is specified
                        if ((x < tableX - 1) && (table[x + 1][y] <= 5)) {
                            numberOfPossibleSides--;
                        }
                        // checks to see if the cell below exist and is what is specified
                        if ((y < tableY - 1) && (table[x][y + 1] <= 5)) {
                            numberOfPossibleSides--;
                        }

                        if (numberOfPossibleSides == numberOfPossibleSidesToCheck) {
                            didCheckSides = true;
                            // check to see if cell can be lit from below
                            for (int y2 = y + 1; y2 < tableY; y2++) {
                                if (table[x][y2] == 88 || table[x][y2] == 66) {
                                    continue;
                                } else if (table[x][y2] == 100) {
                                    table[x][y2] = BULB;
                                    markLitCells(table, tableX, tableY);
                                    unlitIllegalCells = countUnlitIllegalCells(table, tableX, tableY);
                                    break f;
                                } else {
                                    break;
                                }
                            } // check to see if cell can be lit from the left
                            for (int x2 = x - 1; x2 >= 0; x2--) {
                                if (table[x2][y] == 88 || table[x2][y] == 66) {
                                    continue;
                                } else if (table[x2][y] == 100) {
                                    table[x2][y] = BULB;
                                    markLitCells(table, tableX, tableY);
                                    unlitIllegalCells = countUnlitIllegalCells(table, tableX, tableY);
                                    break f;
                                } else {
                                    break;
                                }
                            } // check to see if cell can be lit from the right
                            for (int x2 = x + 1; x2 < tableX; x2++) {
                                if (table[x2][y] == 88 || table[x2][y] == 66) {
                                    continue;
                                } else if (table[x2][y] == 100) {
                                    table[x2][y] = BULB;
                                    markLitCells(table, tableX, tableY);
                                    unlitIllegalCells = countUnlitIllegalCells(table, tableX, tableY);
                                    break f;
                                } else {
                                    break;
                                }
                            } // check to see if cell can be lit from above
                            for (int y2 = y - 1; y2 >= 0; y2--) {
                                if (table[x][y2] == 88 || table[x][y2] == 66) {
                                    continue;
                                } else if (table[x][y2] == 100) {
                                    table[x][y2] = BULB;
                                    markLitCells(table, tableX, tableY);
                                    unlitIllegalCells = countUnlitIllegalCells(table, tableX, tableY);
                                    break f;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (!didCheckSides) {
                numberOfPossibleSidesToCheck++;
            }
        }
    }

    /**
     * Counts the number of unlit illegal cells.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the number of unlit illegal cells.
     */
    private static int countUnlitIllegalCells(int[][] table, int tableX, int tableY) {
        int unlitIllegalCells = 0;
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                if (table[x][y] == 66) {
                    unlitIllegalCells++;
                }
            }
        }
        return unlitIllegalCells;
    }

    /**
     * Places a bulb in every unlit empty cell that is not marked illegal.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    private static void placeAllPossibleBulbs(int[][] table, int tableX, int tableY) {
        int emptyCells = countEmptyCells(table, tableX, tableY);
        while (emptyCells > 0) {
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    if (table[x][y] == EMPTY) {
                        // place bulb in empty cell
                        table[x][y] = BULB;
                        markLitCells(table, tableX, tableY);
                        emptyCells = countEmptyCells(table, tableX, tableY);
                    }
                }
            }
        }
    }

    /**
     * Counts the number of empty cells.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return the number of unlit cells.
     */
    private static int countEmptyCells(int[][] table, int tableX, int tableY) {
        int emptyCells = 0;
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                if (table[x][y] == EMPTY) {
                    emptyCells++;
                }
            }
        }
        return emptyCells;
    }

    /**
     * Marks all lit cells.
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    private static void markLitCells(int[][] table, int tableX, int tableY) {
        // marks all lit cells
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                if (table[x][y] == BULB) {
                    // mark cells underneath
                    for (int y2 = y + 1; y2 < tableY; y2++) {
                        if (table[x][y2] == 100 || table[x][y2] == 88 || table[x][y2] == 66) {
                            table[x][y2] = LIT;
                        } else {
                            break;
                        }
                    } // mark cells to the left
                    for (int x2 = x - 1; x2 >= 0; x2--) {
                        if (table[x2][y] == 100 || table[x2][y] == 88 || table[x2][y] == 66) {
                            table[x2][y] = LIT;
                        } else {
                            break;
                        }
                    } // mark cells to the right
                    for (int x2 = x + 1; x2 < tableX; x2++) {
                        if (table[x2][y] == 100 || table[x2][y] == 88 || table[x2][y] == 66) {
                            table[x2][y] = LIT;
                        } else {
                            break;
                        }
                    } // mark cells above
                    for (int y2 = y - 1; y2 >= 0; y2--) {
                        if (table[x][y2] == 100 || table[x][y2] == 88 || table[x][y2] == 66) {
                            table[x][y2] = LIT;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks to see if the puzzle is solved
     *
     * @param table the problem table.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     * @return true is the puzzle is solved and false otherwise.
     */
    static boolean checkIfSolved(int[][] table, int tableX, int tableY) {
        for (int x = 0; x < tableX; x++) {
            for (int y = 0; y < tableY; y++) {
                if (table[x][y] == EMPTY || table[x][y] == ILLEGAL) {
                    return false;
                }
            }
        }
        return true;
    }
}
