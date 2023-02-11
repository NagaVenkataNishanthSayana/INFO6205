package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UFClient {

    public static int count(int n){
        int totalCount=0;
        for(int i=0;i<1000;i++){
            UF_HWQUPC uf_hwqupc=new UF_HWQUPC(n,true);
            int pairCount=0;

            while(uf_hwqupc.components()!=1){
                int[] pair=randomPair(n);
                pairCount++;
                if(!uf_hwqupc.connected(pair[0],pair[1])){
                    uf_hwqupc.union(pair[0],pair[1]);
                }
            }
            totalCount+=pairCount;
        }
        return totalCount/1000;
    }

    private static int[] randomPair(int n){
        Random r = new Random();
        int p = r.nextInt(n);
        int q = r.nextInt(n);
        while(p == q){
            q = r.nextInt(n);
        }
        return new int[]{p,q};
    }

    public static void main(String[] args){
        int[] siteValues={100,200,400,800,1600,3200,6400,12800};

        for(int n:siteValues){
            int pairCount=count(n);

            System.out.println("No of Sites(n): "+n+" | No of connections generated(m): "+pairCount);
        }
    }
}
