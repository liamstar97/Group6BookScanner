// CS210(c) F2017
// Group 6
// This program provides information on a work.
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.io.*;

public class BookScanner {
    static String DIRECTORY = "webFiles/";
    static String GUTENBERG_URL_STRING = "http://www.textfiles.com/etext/FICTION/";

    public static void main(String args[])
            throws IOException {
        ArrayList<String> reportList = new ArrayList();
        Scanner input = new Scanner(System.in);
        String name = getWorkName(input);
        String author = getAuthorName(input);
        String fileName = getFileName(input);
        PrintWriter printWriter = new PrintWriter(fileName.split("\\.")[0] + "_Report.txt");
        downloadWebFile(fileName);
        println("Report on " + name + " by " + author);
        reportList.add("Report on " + name + " by " + author);
        println("Done by Group6 for In Class Lab Project #2");
        reportList.add("Done by Group6 for In Class Lab Project #2");
        println("File name: " + fileName);
        reportList.add("File name: " + fileName);
        println("File URL: " + GUTENBERG_URL_STRING + fileName);
        reportList.add("File URL: " + GUTENBERG_URL_STRING + fileName);
        println("Number of Words: " + wordCounter(fileName));
        String percentage = getPercentageOfWork(fileName, name, reportList, author);
        println(percentage);
        reportList.add(percentage);
        writeReport(reportList, printWriter);
        printWriter.close();
        println("DONE!");
    }

    private static String getWorkName(Scanner input) {
        print("Please enter the works name: ");
        return input.nextLine();
    }

    private static String getAuthorName(Scanner input){
        print("Please enter the author name: ");
        return input.nextLine();
    }

    private static String getFileName(Scanner input) {
        print("Please enter the name of the file: ");
        return input.nextLine();
    }

    public static int wordCounter(String fileName) throws FileNotFoundException {
        int sum = 0;
        Scanner input = new Scanner(new File(DIRECTORY + fileName));
        while (input.hasNextLine()) {
            String[] line = input.nextLine().trim().split(" ");
            for (int i = 0; i < line.length; i++) {
                if (!line[i].equalsIgnoreCase("")) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static String getPercentageOfWork(String fileName, String title, ArrayList<String> reportList,
                                              String authorName)
        throws FileNotFoundException {
        Scanner file = new Scanner(new File(DIRECTORY + fileName));
        double headerLines = 0;
        double lineCount = 1;
        boolean header = true;
        while (file.hasNextLine()) {
            if (!file.nextLine().trim().equalsIgnoreCase(title) && header) {
                headerLines++;
            } else {
                header = false;
            }
            lineCount++;
        }
        if (header) {
            println("That's not the right title!");
            Scanner input = new Scanner(System.in);
            print("Please re-enter the title: ");
            title = input.nextLine();
            reportList.set(0, "Report on " + title + " by " + authorName);
            return getPercentageOfWork(fileName, title, reportList, authorName);
        } else {
            return "Percentage of the file that is not part of the work: "
                    + ((double) Math.round((headerLines / lineCount) * 1000) / 10) + "%";
        }
    }

    private static void downloadWebFile(String fileName) throws IOException {
        URL fileURL = new URL(GUTENBERG_URL_STRING + fileName);
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            File file = new File(DIRECTORY + fileName);
            if (file.exists()) {
                println("This file already exists!");
                return;
            }
        }
        println("Downloading file from: " + fileURL);
        Files.copy(fileURL.openStream(), Paths.get(DIRECTORY + fileName), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void writeReport(ArrayList<String> reportList, PrintWriter printWriter) {
        for (int i = 0; i < reportList.size(); i++) {
            filePrintln(reportList.get(i), printWriter);
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private static void filePrintln(String s, PrintWriter printWriter) {
        printWriter.println(s);
        println(s);
    }

    private static void println(int n) {
        System.out.println(n);
    }

    private static void println(double n) {
        System.out.println(n);
    }

    private static void print(String s) {
        System.out.print(s);
    }

    private static void print(int n) {
        System.out.print(n);
    }

    private static void print(double n) {
        System.out.println(n);
    }
}
