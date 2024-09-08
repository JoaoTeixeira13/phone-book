package PhoneBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Setup
        String pathToDirectory = "src/main/files/directory.txt";
        File directoryFile = new File(pathToDirectory);
        String pathToFind = "src/main/files/find.txt";
        File findFile = new File(pathToFind);
        List<String> directory = new ArrayList<>();
        List<String> find = new ArrayList<>();

        System.out.println();
        // Scan files into lists
        try (Scanner scanner = new Scanner(directoryFile)) {
            while (scanner.hasNextLine()) {
                directory.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + pathToDirectory);
        }

        try (Scanner findScanner = new Scanner(findFile)) {
            while (findScanner.hasNextLine()) {
                find.add(findScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + pathToFind);
        }

        SearchAlgorithm searchAlgorithm = new SearchAlgorithm();

        long linearSearchDurationInMilliseconds = searchAlgorithm.linear(directory, find);
        searchAlgorithm.bubbleSortAndJump(directory, find, linearSearchDurationInMilliseconds);
        searchAlgorithm.quickSortAndBinarySearch(directory, find);
        searchAlgorithm.hashTable(directory, find);
    }
}
