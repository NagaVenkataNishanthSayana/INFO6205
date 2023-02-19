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

        int[] threadNum = {2, 4, 8, 16,32};

        ForkJoinPool forkPool;

        Random random = new Random();
        int[] array;
        ArrayList<Long> timeList = new ArrayList<>();
        for (int i = 0; i < arrLengths.length; i++) {
            int length = arrLengths[i];
            array = new int[length];
            System.out.println("Array size is " + length);

            //cut-off lengths according to levels of recursion tree
            int[] cutoffLen = {length / 1024 + 1, length / 512 + 1, length / 256 + 1, length / 128 + 1,
                    length / 64 + 1, length / 32 + 1, length / 16 + 1, length / 8 + 1, length / 4 + 1,
                    length / 2 + 1, length + 1};

            for (int c = 0; c < cutoffLen.length; c++) {
                ParSort.cutoff = cutoffLen[c];
                for (int n = 0; n < threadNum.length; n++) {
                    forkPool = new ForkJoinPool(threadNum[n]);
                    long duration;
                    long startTimems = System.currentTimeMillis();
                    for (int t = 0; t < 10; t++) {
                        for (int j = 0; j < length; j++) array[j] = random.nextInt(10000000);
                        ParSort.sort(array, 0, array.length, forkPool);
                    }
                    long endTimems = System.currentTimeMillis();
                    duration = (endTimems - startTimems);

                    System.out.println(" cut-off is：" + (ParSort.cutoff) +
                            " number of threads：" + (threadNum[n]) + "\t\taverage time taken:" + (duration / 10) + "ms");
                    timeList.add(duration);
                }
            }
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
