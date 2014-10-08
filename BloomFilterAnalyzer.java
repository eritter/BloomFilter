import java.util.*;

/**
 * Analyzes a BloomFilter using a set number of objects to insert and to check.
 */
public class BloomFilterAnalyzer {
    private BloomFilter bloomFilter;
    private String[] items;
    private String[] testingItems;
    private int falsePositives = -1;

    /**
     * Creates the analyzer
     * @param bloomFilter the bloom filter to use
     * @param numItemsToInsert the number of items to insert in the bloom filter
     * @param numItemsToCheck the number of items to check for false positives
     */
    public BloomFilterAnalyzer(BloomFilter bloomFilter, int numItemsToInsert, int numItemsToCheck) {
        this.bloomFilter = bloomFilter;

        TreeSet<String> set1 = getStrings(numItemsToInsert, 5);
        String[] arr = set1.toArray(new String[numItemsToInsert]);
        String[] arr2 = getStringsNotIn(set1, numItemsToCheck, 5);

        this.items = arr;
        this.testingItems = arr2;
    }

    /**
     * Adds items to bloom filter and checks false positive rate
     *
     * @return the percent of false positives
     */
    public double analyze() {
        bloomFilter.add(items);
        falsePositives = bloomFilter.check(testingItems);
        return ((double) falsePositives) / testingItems.length;
    }

    /**
     * Gets a string containing verbose results
     *
     * @return the string containing the results
     */
    public String getVerboseResults() {
        return "Array length: " + bloomFilter.getArraySize() + "\n" +
                "Num items: " + items.length + "\n" +
                "Prime Bases: " + Arrays.toString(bloomFilter.gePrimeBases()) + "\n" +
                "False Positives: " + falsePositives + "/" + testingItems.length + " = " +
                ((double) falsePositives) / testingItems.length;
    }

    /**
     * Gets the number of false positives out of the total elements checked
     *
     * @return the number of false positives
     */
    public int getNumFalsePositives() {
        return falsePositives;
    }

    /**
     * Gets the percent of false positives out of the total elements checked
     *
     * @return the percent of false positives
     */
    public double getPercentToFalsePositives() {
        if (falsePositives == -1) return -1;
        return ((double) falsePositives) / items.length;
    }

    /**
     * Creates a set of strings of length stringLength using ASCII chars 0 - 255
     *
     * @param numStrings   the number of strings to create
     * @param stringLength the length of each string
     * @return the set of strings created
     */
    public TreeSet<String> getStrings(int numStrings, int stringLength) {
        TreeSet<String> set = new TreeSet<String>();
        while (set.size() < numStrings) {
            set.add(getString(stringLength));
        }
        return set;
    }

    /**
     * Creates a set of strings of length stringLength using ASCII chars
     * 0 - 255 that are not in the given set of Strings
     *
     * @param strings      the set of strings that can not be in the new set
     * @param numStrings   the number of strings to create
     * @param stringLength the length of each string
     * @return the set of strings created
     */
    public String[] getStringsNotIn(Set<String> strings, int numStrings, int stringLength) {
        TreeSet<String> set = new TreeSet<String>();
        while (set.size() < numStrings) {
            String s = getString(stringLength);
            if (!strings.contains(s))
                set.add(getString(stringLength));
        }
        return set.toArray(new String[numStrings]);
    }

    /**
     * Creates a string using ASCII chars 0 - 255
     *
     * @param length the length of the string
     * @return the string created
     */
    private String getString(int length) {
        Random r = new Random();
        String s = "";
        for (int i = 0; i < length; i++)
            s += (char) r.nextInt(255);
        return s;
    }
}
