package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Random;

public class BenchmarkInsertionSort {



    public static void main(String[] args) {
        Benchmark_Timer benchmark_timer = new Benchmark_Timer<Integer[]>("Benchmark Insertion Sort",
                (Integer[] array) -> {
                    new InsertionSort<Integer>().sort(array, true);
                });
        int [] arrayLengths={200,400,800,1600,3200};
        int m=100;

        System.out.println();
        System.out.println("Benchmarks for Random Array:");
        for(int i=0;i<arrayLengths.length;i++){
            Integer[] random=new Integer[arrayLengths[i]];
            Random rand = new Random();
            for(int j = 0; j <random.length ; j++){
                random[j] = rand.nextInt(j+1);
            }
            double avgTime=benchmark_timer.run(random,100);
            System.out.println("Avergae Time taken to sort the Random Array of length n="+arrayLengths[i]+" is T="+avgTime);
        }

        System.out.println();
        System.out.println("Benchmarks for Reverse Ordered Array:");
        for(int i=0;i<arrayLengths.length;i++){
            Integer[] reverseOrderd=new Integer[arrayLengths[i]];
            int k= 0;
            for(int j =reverseOrderd.length-1 ; j >=0; j--){
                reverseOrderd[k] = j;
                k++;
            }
            double avgTime=benchmark_timer.run(reverseOrderd,100);
            System.out.println("Avergae Time taken to sort the Reverse Ordered Array of length n="+arrayLengths[i]+" is T="+avgTime);
        }

        System.out.println();
        System.out.println("Benchmarks for Partially Ordered Array:");
        for(int i=0;i<arrayLengths.length;i++){
            Random rand = new Random();
            Integer[] partially=new Integer[arrayLengths[i]];

            for(int j = 0; j <= partially.length / 2; j++){
                partially[j] = j;
            }

            for(int j =  partially.length / 2 + 1 ; j < partially.length ; j++){
                partially[j] = rand.nextInt(partially.length - j);
            }
            double avgTime=benchmark_timer.run(partially,100);
            System.out.println("Avergae Time taken to sort the Partially Ordered Array of length n="+arrayLengths[i]+" is T="+avgTime);
        }

        System.out.println();
        System.out.println("Benchmarks for Sorted Array:");
        for(int i=0;i<arrayLengths.length;i++){
            Integer[] sorted=new Integer[arrayLengths[i]];

            for(int j = 0; j < sorted.length ; j++){
                sorted[j] = j;
            }
            double avgTime=benchmark_timer.run(sorted,100);
            System.out.println("Avergae Time taken to sort the Sorted Array of length n="+arrayLengths[i]+" is T="+avgTime);
        }

    }
}
