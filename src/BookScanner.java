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
        ArrayList<String> tokens = tokenize(fileName);
        SentenceAnalysis sentenceAnalysis = sentenceAnalyzer(tokens);
        println("--------------------------------------------------------------------------------------");
        println("Report on " + name + " by " + author);
        reportList.add("Report on " + name + " by " + author);
        println("Done by Group6 for In Class Lab Project #2");
        reportList.add("Done by Group6 for In Class Lab Project #2");
        println("File name: " + fileName);
        reportList.add("File name: " + fileName);
        println("File URL: " + GUTENBERG_URL_STRING + fileName);
        reportList.add("File URL: " + GUTENBERG_URL_STRING + fileName);
        println("Number of Words: " + tokens.size());
        reportList.add("Number of Words: " + tokens.size());
        println("Number of Sentences: " + sentenceAnalysis.getNumSentences());
        reportList.add("Number of Sentences: " + sentenceAnalysis.getNumSentences());
        println("Longest Sentence: " + sentenceAnalysis.getMax());
        reportList.add("Longest Sentence: " + sentenceAnalysis.getMax());
        println("Shortest Sentence: " + sentenceAnalysis.getMin());
        reportList.add("Shortest Sentence: " + sentenceAnalysis.getMin());
        println("Average Number of Words Per Sentence: " + sentenceAnalysis.getAverage());
        reportList.add("Average Number of Words Per Sentence: " + sentenceAnalysis.getAverage());
        String percentage = getPercentageOfWork(fileName, name, reportList, author);
        println(percentage);
        reportList.add(percentage);
        println("--------------------------------------------------------------------------------------");
        writeReport(reportList, printWriter, fileName);
        printWriter.close();
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

    /**
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<String> tokenize(String fileName) throws FileNotFoundException {
        Scanner s = new Scanner(new File(DIRECTORY + fileName));
        // try link and read the text from your scanner
        ArrayList<String> tokens = new ArrayList<>();
        while (s.hasNextLine()) {
            // get the sum word number and ignore white space
            String[] line = s.nextLine().trim().split(" ");
            for (int i = 0; i < line.length; i++) {
                // split words that are broken up by two "--" and add them to word sum
                String[] subLine = line[i].split("--");
                for (int j = 0; j < subLine.length; j++) {
                    if (!subLine[j].isEmpty()) {
                        tokens.add(subLine[j].trim());
                    }
                }
            }
        }
        return tokens;
    }

    /**
     *
     * @param tokens
     * @return
     */
    private static SentenceAnalysis sentenceAnalyzer(ArrayList<String> tokens) {
        SentenceAnalysis sentenceAnalysis = new SentenceAnalysis();
        int sentenceCount = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int sentenceSize = 0;
        String delimiters = ".!?";
        for (String token : tokens) {
            sentenceSize++;
            //escapes early in case a token contains two delimiters
            for (int i = 0; i < token.length() && sentenceSize != 0; i++) {
                if (delimiters.indexOf(token.charAt(i)) != -1) {
                    // If the delimiters string contains the character
                    sentenceCount++;
                    if (sentenceSize > max) {
                        max = sentenceSize;
                    }
                    if (sentenceSize < min) {
                        min = sentenceSize;
                    }
                    sentenceSize = 0;
                }
            }
        }
        sentenceAnalysis.setMax(max);
        sentenceAnalysis.setMin(min);
        sentenceAnalysis.setNumSentences(sentenceCount);
        sentenceAnalysis.setAverage((double)tokens.size() / sentenceCount);
        sentenceAnalysis.setNumWords(tokens.size());
        return sentenceAnalysis;
    }

    /**
     * returns the percentage of the document that is part of the work
     * @param fileName file name
     * @param title title
     * @param reportList list of reports
     * @param authorName author name
     * @return percentage of work
     * @throws FileNotFoundException
     */
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

    /**
     * downloads the requested file from gutenberg fiction book library
     * @param fileName file name
     * @throws IOException
     */
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

    /**
     * writes to "fileName"_Report.txt
     * @param reportList list of reports
     * @param printWriter a print writer
     * @param fileName name of file
     */
    private static void writeReport(ArrayList<String> reportList, PrintWriter printWriter, String fileName) {
        for (int i = 0; i < reportList.size(); i++) {
            printWriter.println(reportList.get(i));
        }
        println("Finished generating " + fileName.split("\\.")[0] + "_Report.txt!");
    }

    /**
     * shorthand println method.
     * @param s string
     */
    private static void println(String s) {
        System.out.println(s);
    }

    /**
     * shorthand print method
     * @param s string
     */
    private static void print(String s) {
        System.out.print(s);
    }
}
