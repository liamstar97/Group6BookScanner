import java.util.*;
import java.io.*;

public class BookScanner {
    public static void main(String args[])
            throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        String name = getWorkName(input);
        String author = getAuthorName(input);
        String fileLocation = getFileLocation(input);
        Scanner file = new Scanner(new File(fileLocation));
        println("Report on " + name + " by " + author);
        println("Done by Group6 for In Class Lab Project #2");
        println("File name: " + getLocalFileName(fileLocation));
        println("File URL: " + fileLocation);
    }

    private static String getWorkName(Scanner input) {
        print("Please enter the works name: ");
        return input.nextLine();
    }

    private static String getAuthorName(Scanner input){
        print("Please enter the author name: ");
        return input.nextLine();
    }

    private static String getFileLocation(Scanner input) {
        print("Please enter the file location (full path): ");
        return input.nextLine();
    }

    private static String getLocalFileName(String fileLocation) {
        String[] fileLocationArray = fileLocation.split("\\\\");
        return fileLocationArray[fileLocationArray.length - 1];
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private static void print(String s) {
        System.out.print(s);
    }
}
