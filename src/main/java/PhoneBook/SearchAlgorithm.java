package PhoneBook;

import java.util.HashMap;
import java.util.List;


public class SearchAlgorithm {

    Utils utils = new Utils();

    public long linear(List<String> directory, List<String> find) {
        long startingLinearSearchTime = System.currentTimeMillis();
        int linearMatches = 0;

        System.out.println("Start searching (linear search)...");

        for (String findEntry : find) {
            for (String directoryEntry : directory) {
                if (utils.getDirectoryName(directoryEntry).equals(findEntry)) {
                    linearMatches++;
                    break;
                }
            }
        }

        long linearSearchDurationInMilliseconds = System.currentTimeMillis() - startingLinearSearchTime;
        TimeDuration linearSortDuration = new TimeDuration(linearSearchDurationInMilliseconds);

        System.out.printf("Found %s / %s entries. Time taken: %d min. %d sec. %d ms.\n", linearMatches, find.size(), linearSortDuration.getMinutes(), linearSortDuration.getSeconds(), linearSortDuration.getMs());
        return linearSearchDurationInMilliseconds;
    }

    public void bubbleSortAndJump(List<String> directory, List<String> find, long linearSearchDurationInMilliseconds) {

        System.out.println("Start searching (bubble sort + jump search)...");

        long startingBubbleSearchTime = System.currentTimeMillis();

        int bubbleMatches = 0;
        long bubbleSortDurationInMilliseconds = bubbleSort(directory, linearSearchDurationInMilliseconds);
        TimeDuration bubbleSortDuration = new TimeDuration(bubbleSortDurationInMilliseconds);

        //switch to linear search when bubble sort takes too long
        if (bubbleSortDurationInMilliseconds > linearSearchDurationInMilliseconds * 10) {
            long startingSecondLinearSearchStart = System.currentTimeMillis();

            for (String findEntry : find) {
                for (String directoryEntry : directory) {
                    if (utils.getDirectoryName(directoryEntry).equals(findEntry)) {
                        bubbleMatches++;
                        break;
                    }
                }
            }

            TimeDuration secondLinearSearchDuration = utils.getTimeDurationFomStartingPoint(startingSecondLinearSearchStart);
            TimeDuration totalSecondLinearSearchDuration = utils.getTimeDurationFomStartingPoint(startingBubbleSearchTime);
            utils.printAlgorithmDurationTimeLogs(bubbleMatches, find.size(), totalSecondLinearSearchDuration, bubbleSortDuration, secondLinearSearchDuration, true, true);

        } else {
            //do jump search
            long startingJumpSearchStart = System.currentTimeMillis();

            for (String findEntry : find) {
                for (int i = 0; i < find.size(); i++) {
                    if (jumpSearch(directory, findEntry) != null) {
                        bubbleMatches++;
                    }

                }
            }

            TimeDuration jumpSearchDuration = utils.getTimeDurationFomStartingPoint(startingJumpSearchStart);
            TimeDuration totalJumpSearchDuration = utils.getTimeDurationFomStartingPoint(startingBubbleSearchTime);
            utils.printAlgorithmDurationTimeLogs(bubbleMatches, find.size(), totalJumpSearchDuration, bubbleSortDuration, jumpSearchDuration, false, true);
        }
    }

    public void quickSortAndBinarySearch(List<String> directory, List<String> find) {
        System.out.println("\nStart searching (quick sort + binary search)...");
        long quickSortAndBinarySearchStartTime = System.currentTimeMillis();
        int binarySearchMatches = 0;

        quickSort(directory, 0, directory.size() - 1);

        TimeDuration quickSortDuration = utils.getTimeDurationFomStartingPoint(quickSortAndBinarySearchStartTime);
        long binarySearchStartTime = System.currentTimeMillis();

        for (String findEntry : find) {
            String match = runBinarySearchRecursively(directory, findEntry, 0, directory.size() - 1);
            if (match != null) {
                binarySearchMatches++;
            }
        }

        TimeDuration binarySearchDuration = utils.getTimeDurationFomStartingPoint(binarySearchStartTime);
        TimeDuration quickSortAndBinarySearchTimeDuration = utils.getTimeDurationFomStartingPoint(quickSortAndBinarySearchStartTime);
        utils.printAlgorithmDurationTimeLogs(binarySearchMatches, find.size(), quickSortAndBinarySearchTimeDuration, quickSortDuration, binarySearchDuration, false, true);

    }

    public void hashTable(List<String> directory, List<String> find) {
        System.out.println("\nStart searching (hash table)...");
        long hashTableCreateAndSearchStartTime = System.currentTimeMillis();
        int hashTableSearchMatches = 0;
        HashMap<String, String> phoneBook = new HashMap<>();

        for (String directoryEntry : directory) {

            String[] parts = directoryEntry.split(" ", 2);
            String number = parts[0];
            String name = parts[1];

            phoneBook.put(name, number);
        }

        TimeDuration creatingPhoneBookDuration = utils.getTimeDurationFomStartingPoint(hashTableCreateAndSearchStartTime);

        long hashTableSearchStartTime = System.currentTimeMillis();

        for (String findEntry : find) {
            String match = phoneBook.get(findEntry);
            if (match != null) {
                hashTableSearchMatches++;
            }
        }

        TimeDuration hashTableSearchDuration = utils.getTimeDurationFomStartingPoint(hashTableSearchStartTime);
        TimeDuration hashTableCreateAndSearchTimeDuration = utils.getTimeDurationFomStartingPoint(hashTableCreateAndSearchStartTime);
        utils.printAlgorithmDurationTimeLogs(hashTableSearchMatches, find.size(), hashTableCreateAndSearchTimeDuration, creatingPhoneBookDuration, hashTableSearchDuration, false, false);

    }

    private long bubbleSort(List<String> list, long linearSearchDurationInMilliseconds) {
        long startingBubbleSearchTime = System.currentTimeMillis();
        long bubbleSearchDurationInMilliseconds = System.currentTimeMillis() - startingBubbleSearchTime;

        int n = list.size();
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < n; i++) {
                String firstValue = utils.getDirectoryName(list.get(i - 1));
                String secondValue = utils.getDirectoryName(list.get(i));
                if (firstValue.compareTo(secondValue) > 0) {
                    utils.swapListElements(list, i - 1, i);
                    swapped = true;
                }
                bubbleSearchDurationInMilliseconds = System.currentTimeMillis() - startingBubbleSearchTime;

                //return current count and switch to linear search when current duration exceeds linearSearchDurationInMilliseconds * 10
                if (bubbleSearchDurationInMilliseconds > linearSearchDurationInMilliseconds * 10) {
                    return bubbleSearchDurationInMilliseconds;
                }
            }
        }
        return bubbleSearchDurationInMilliseconds;

    }


    private String jumpSearch(List<String> list, String target) {
        int n = list.size();

        int step = (int) Math.floor(Math.sqrt(n));

        int currentIndex = 0;// index of current element, starts from 0
        int helper = 0;

        while (currentIndex < n) {
            String currentValue = utils.getDirectoryName(list.get(currentIndex));
            if (currentValue.equals(target)) {
                return target;
            } else if (currentValue.compareTo(target) < 0) {// maybe this block contains target element
                // backward linear search
                helper = currentIndex;// the index of block end
                int blockPrevIndex = helper - step + 1;// the index of block start

                while (helper >= blockPrevIndex && helper >= 0) {
                    if (utils.getDirectoryName(list.get(helper)).equals(target)) {
                        return target;
                    }
                    helper--;
                }
                return null;// dont search target in this block, it's make no sense to search other blocks
            }

            // dont meet above cases, move to next block
            currentIndex += step;
        }

        // target element in last block, the size of last block < step
        helper = n - 1;// index of last element
        int lastBlockPrevIndex = currentIndex - step + 1;
        while (helper >= lastBlockPrevIndex) {
            if (utils.getDirectoryName(list.get(helper)).equals(target)) {
                return target;
            }
            helper--;
        }
        // dont search target in last block
        return null;
    }

    private void quickSort(List<String> list, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end);

            quickSort(list, begin, partitionIndex - 1);
            quickSort(list, partitionIndex + 1, end);
        }
    }

    private int partition(List<String> list, int begin, int end) {
        String pivot = utils.getDirectoryName(list.get(end));
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if ((utils.getDirectoryName(list.get(j)).compareTo(pivot) < 0)) {
                i++;
                utils.swapListElements(list, i, j);
            }
        }
        utils.swapListElements(list, i + 1, end);
        return i + 1;
    }

    private String runBinarySearchRecursively(
            List<String> sortedList, String target, int low, int high) {
        int middle = low + ((high - low) / 2);

        if (high < low) {
            return null;
        }

        if (target.equals(utils.getDirectoryName(sortedList.get(middle)))) {
            return target;
        } else if (target.compareTo(utils.getDirectoryName(sortedList.get(middle))) < 0) {
            return runBinarySearchRecursively(
                    sortedList, target, low, middle - 1);
        } else {
            return runBinarySearchRecursively(
                    sortedList, target, middle + 1, high);
        }
    }

}
