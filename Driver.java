/**
 * Created by emily on 9/25/14.
 */
public class Driver {
    public static void main(String[] args) throws Exception {

        int m = 100000;
        int strLength = 10;
        int n;
        double N = Math.pow(256, strLength);


        for (n = 10000; n <= 700000; n += 10000) {

            int c = (int) (((double) n) / m);
            int k = Math.max(1, (int) (c * Math.log(2)));

            //double p = 1-Math.pow(1-Math.exp((-1*k*n)/(m+0.0)),k);
            //System.out.println(p);
            BloomFilter b = new BloomFilter(n, k);

            BloomFilterAnalyzer a = new BloomFilterAnalyzer(b, m, m);
            System.out.println(a.analyze());
        }

    }


}
