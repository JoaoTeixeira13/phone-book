package PhoneBook;

import java.util.List;

public class Utils {

    public void swapListElements(List<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public String getDirectoryName(String directoryEntry) {
        return directoryEntry.split(" ", 2)[1];
    }

    public TimeDuration getTimeDurationFomStartingPoint(long startingTime) {
        return new TimeDuration(System.currentTimeMillis() - startingTime);
    }

    public void printAlgorithmDurationTimeLogs(int matches, int listSize, TimeDuration totalDuration, TimeDuration sortOrCreateDuration, TimeDuration searchDuration, boolean skippedSearch, boolean sortingAction) {

        System.out.printf("Found %s / %s entries. Time taken: %d min. %d sec. %d ms.\n", matches, listSize, totalDuration.getMinutes(), totalDuration.getSeconds(), totalDuration.getMs());
        System.out.printf("%s time: %d min. %d sec. %d ms.%s\n", sortingAction ? "Sorting" : "Creating", sortOrCreateDuration.getMinutes(), sortOrCreateDuration.getSeconds(), sortOrCreateDuration.getMs(), skippedSearch ? " - STOPPED, moved to linear search" : "");
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n", searchDuration.getMinutes(), searchDuration.getSeconds(), searchDuration.getMs());
    }
}
