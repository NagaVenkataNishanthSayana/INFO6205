package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());

        int[] arrLengths = {524288,1048576,2097152,4194304};
        ForkJoinPool forkPool;

        int[] threadNum = {2, 4, 8, 16,32};
        Random random = new Random();
        int[] array;
        ArrayList<Long> timeList = new ArrayList<>();

        for(int l=0;l<arrLengths.length;l++) {
            int length=arrLengths[l];
            array = new int[length];

            int[] cutoffLen = {length / 1024 + 1, length / 512 + 1, length / 256 + 1, length / 128 + 1,
                    length / 64 + 1, length / 32 + 1, length / 16 + 1, length / 8 + 1, length / 4 + 1,
                    length / 2 + 1, length + 1};

            System.out.println("Length of array is " + arrLengths[l]);
            System.out.println();


                for (int n = 0; n < threadNum.length; n++) {
                    for (int c = 0; c < cutoffLen.length; c++) {
                        ParSort.cutoff = cutoffLen[c];

                        forkPool = new ForkJoinPool(threadNum[n]);
                        long time;
                        long startTime = System.currentTimeMillis();
                        for (int t = 0; t < 10; t++) {
                            for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                            ParSort.sort(array, 0, array.length, forkPool);
                        }
                        long endTime = System.currentTimeMillis();
                        time = (endTime - startTime);
                        timeList.add(time);


                        System.out.println("cutoff：" + (ParSort.cutoff) + " Thread Count: " + threadNum[n] + "\t\t10times Time:" + time + "ms");}
            }
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            for (long i : timeList) {
                String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
