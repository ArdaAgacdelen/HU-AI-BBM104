import java.io.File;
import java.util.ArrayList;

/**
 * This class represents main class of a voyage operations' manager system.
 */
public class BookingSystem {

    /**
     * Main method of BookingSystem class, contains necessary method calls and iterations to terminate program.
     *
     * @param args Command line arguments as string array
     */
    public static void main(String[] args) {

        if (args.length != 2) { //Given different from 2 arguments.
            System.out.println("ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
        } else {

            File input = new File(args[0]);
            // Check if the input file exists and is readable.
            if (!input.exists() || !input.canRead()) {
                System.out.printf("ERROR: This program cannot read from the \"%s\", either this program does not have read permission to read that file or file does not exist. Program is going to terminate!", args[0]);
                System.exit(1);
            }


            File output = new File(args[1]);
            // Check if the output file is writable.
            if (output.exists() && !output.canWrite()) {
                System.out.printf("ERROR: This program cannot write to the \"%s\", please check the permissions to write that directory. Program is going to terminate!", args[1]);
                System.exit(1);
            }

            // If the output file does not exist, check the directory permissions
            if (!output.exists()) {
                File parent = output.getParentFile();
                if (parent != null && !parent.canWrite()) {
                    System.out.printf("ERROR: This program cannot write to the \"%s\", please check the permissions to write that directory. Program is going to terminate!", args[1]);
                    System.exit(1);
                }
            }

            //Check file extension(.txt)
            if (!args[1].endsWith(".txt")) {
                System.out.printf("ERROR: This program cannot write to the \"%s\", please check the permissions to write that directory. Program is going to terminate!", args[1]);
                System.exit(1);
            }


            //Creates an empty output file or deletes content if already exists.
            FileIO.writeToFile(args[1], "", false, false);

            //Reads input file line by line to a string array.
            String[] lines = FileIO.readFile(args[0], true, true);

            //List to keep voyages as transportation vehicles.
            ArrayList<PublicTransportationVehicle> vehicleList = new ArrayList<>();

            for (String line : lines) { //lines: input lines.
                try {
                    CommandOperations.operateCommand(line, vehicleList, args[1]); //line: voyage operation command.

                } catch (Exception e) {
                    //Writes to the output file proper error message.
                    FileIO.writeToFile(args[1], (e instanceof ZReportError) ? e.getMessage() : "ERROR: " + e.getMessage(),
                            true, true);
                }
            }


            //Operations taken to bring the output file to the appropriate format.
            String[] outputFile = FileIO.readFile(args[1], true, true);
            try {
                if (!outputFile[outputFile.length - 1].equals("----------------")) {
                    //z-report was not written to the end of file.
                    if (!vehicleList.isEmpty()) {
                        Messages.ZReport.printMessage(vehicleList, args[1]);

                    } else { //There are not any voyage left
                        FileIO.writeToFile(args[1], "Z Report:\n----------------\nNo Voyages Available!\n----------------", true, false);
                    }
                }
            } catch (IndexOutOfBoundsException e) { //Empty input file
                FileIO.writeToFile(args[1], "Z Report:\n----------------\nNo Voyages Available!\n----------------", true, false);
            }
            outputFile = FileIO.readFile(args[1], true, true); //to delete newline at the end

            FileIO.writeToFile(args[1], "", false, false); //Deletes content not to over-write
            for (int i = 0; i < outputFile.length; i++) {
                FileIO.writeToFile(args[1], outputFile[i], true, i < outputFile.length - 1);
            }
        }
    }
}
