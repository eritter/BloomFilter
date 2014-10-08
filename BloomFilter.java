import java.util.ArrayList;
import java.util.Random;

/**
 * A Bloom Filter that holds Strings. It can add items and check if they are in the filter.
 * It does not support removing. There can be false positives, but no false negatives for
 * checking to see if an item is in the filter.
 */
public class BloomFilter {
    private boolean arr[];
    private Integer[] primeBases;

    /**
     * Creates the BloomFilter
     * @param tableSize the size of the backing array for the bloom filter
     * @param numHashFunctions the number of hash functions to use
     * @throws Exception
     */
    public BloomFilter(int tableSize, int numHashFunctions) throws Exception {
        arr = new boolean[tableSize];
        primeBases = getPrimeBases(numHashFunctions);
    }

    /**
     * Adds an item to the bloom filter
     *
     * @param item the item to add
     */
    public void add(String item) {
        for (int base : primeBases) {
            long hash = getHash(item, base);
            int place = (int) (hash % arr.length);
            arr[place] = true;
        }
    }

    /**
     * Adds an array of items to the bloom filter
     *
     * @param items the items to add
     */
    public void add(String[] items) {
        for (String t : items) {
            add(t);
        }
    }

    /**
     * Gets the size of the array backing the bloom filter
     *
     * @return the size of the array backing the bloom filter
     */
    public int getArraySize() {
        return arr.length;
    }

    /**
     * Gets the prime bases used for this bloom filter
     *
     * @return the prime bases
     */
    public Integer[] gePrimeBases() {
        return primeBases;
    }

    /**
     * Checks to see how many of the items are in the bloom filter (can give false positives)
     *
     * @param items the items to check for
     * @return the number of items that are in the bloom filter (can give false positives)
     */
    public int check(String[] items) {
        int counter = 0;
        for (String item : items) {
            if (check(item))
                counter++;
        }
        return counter;
    }

    /**
     * Checks if an item is in the bloom filter (can give false positives)
     *
     * @param item the item to check for
     * @return if it is in the bloom filter (can give false positives)
     */
    public boolean check(String item) {
        for (int base : primeBases) {
            long hash = getHash(item, base);
            int place = (int) (hash % arr.length);
            if (!arr[place])
                return false;
        }
        return true;
    }

    /**
     * Creates a hash based off of a string and a base
     *
     * @param item the item to hash
     * @param base the base (should be prime)
     * @return the hash
     */
    private long getHash(String item, int base) {
        long hash = 0;
        for (int i = 0; i < item.length(); i++) {
            hash = hash * base + item.charAt(i);
        }
        return hash;
    }

    /**
     * Gets an array of prime bases between 7 and 349
     *
     * @param numBases the number of prime bases to choose
     * @return the prime bases
     * @throws Exception if more prime bases are requested than exist between 7 and 349
     */
    private Integer[] getPrimeBases(int numBases) throws Exception {
        Random r = new Random();
        Integer[] primesList = {7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 47, 53,
                59, 61, 67, 71, 83, 89, 97, 101, 103, 107, 109, 113,
                127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193,
                197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
                277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349};

        if (numBases > primesList.length)
            throw new Exception("Too many hash functions required.");

        ArrayList<Integer> primesRemaining = new ArrayList<Integer>();
        for (Integer i : primesList) {
            primesRemaining.add(i);
        }
        ArrayList<Integer> arrList = new ArrayList<Integer>();

        for (int i = 0; i < numBases; i++) {
            int index = r.nextInt(primesRemaining.size());
            arrList.add(primesRemaining.remove(index));
        }

        return ((arrList.toArray(new Integer[arrList.size()])));
    }
}
