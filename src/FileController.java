import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Author - Cameron Mathis
 *
 * This class handles all the reading and writing to files.
 */
public class FileController {
    /**
     * Reads in the problem file and then creates an array with 100 in the white slots and the appropriate number on
     * the black spots.
     *
     * @param problemFilePath the relative file path+name of the log file.
     * @return the problem table as a two dimensional array.
     */
    static int[][] readProblemFile(String problemFilePath) {
        int[][] result = new int[0][0];
        int tableX = 0;
        int tableY = 0;
        try {
            File problemFile = new File(problemFilePath);
            Scanner myReader = new Scanner(problemFile);
            if (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tableX = Integer.parseInt(data);
                data = myReader.nextLine();
                tableY = Integer.parseInt(data);
            }
            result = new int[tableX][tableY];
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    result[x][y] = Solver.EMPTY;
                }
            }
            while (myReader.hasNextLine()) {
                try {
                    // subtract 1 since the array index starts at 0 and not 1
                    int x = myReader.nextInt() - 1;
                    // subtract it from five since array indexing starts at 0 and the top instead of 1 and the bottom
                    int y = tableY - myReader.nextInt();
                    int z = myReader.nextInt();
                    result[x][y] = z;
                } catch (NoSuchElementException e) {
                    System.out.println("Incorrect problem file format.");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Problem file not found.");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Create the solution file.
     *
     * @param solutionFilePath the relative file path+name of the solution file.
     * @param problemFilePath the relative file path+name of the problem file.
     * @param solution the solution to the problem.
     * @param tableX the x length of the problem table.
     * @param tableY the y length of the problem table.
     */
    static void createSolutionFile(String solutionFilePath, String problemFilePath, int[][] solution, int tableX, int tableY) {
        try {
            if (Files.exists(Paths.get(solutionFilePath))) {
                Files.delete(Paths.get(solutionFilePath));
            }
            FileWriter writer = new FileWriter(solutionFilePath, true);
            writer.write("Solution file for: " + problemFilePath +  "\r\n\n");
            writer.write("Bulb coordinates: ");
            for (int x = 0; x < tableX; x++) {
                for (int y = 0; y < tableY; y++) {
                    // writes each coordinate to the solution file
                    if (solution[x][y] == Solver.BULB) {
                        // add 1 since the array index starts at 0 and not 1
                        int xOut = x + 1;
                        // subtract it from five since array indexing starts at 0 and the top, instead of 1 and the bottom
                        int yOut = tableY - y;
                        writer.write("\r\n" + xOut + " " + yOut);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
