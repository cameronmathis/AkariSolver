/**
 * Author - Cameron Mathis
 *
 * This is the main class for the Akari Solver.
 */
public class Main {

    /**
     * @param args String[]
     */
    public static void main(String[] args) {
        // read in command line arguments
        if (args.length != 2) {
            System.out.println("Incorrect Command Line Arguments.\r\n");
            System.out.println("View README.md for proper syntax.\r\n");
            System.exit(0);
        }
        String problemFilePath = args[0];
        String solutionFilePath = args[1];
        // read in the puzzle
        int[][] problemTable = FileController.readProblemFile(problemFilePath);
        // calculate table X & Y length
        int tableX = problemTable.length;
        int tableY = problemTable[0].length;
        // solve problem
        int[][] solution = Solver.solveProblem(problemTable, tableX, tableY);
        // generate solution file
        FileController.createSolutionFile(solutionFilePath, problemFilePath, solution, tableX, tableY);
    }
}
