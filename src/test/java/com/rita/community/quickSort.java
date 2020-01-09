package com.rita.community;

public class quickSort {

    public void quickSortImpl(int a[],int low,int high){
        quickSort quickSort1 = new quickSort();
        int l = low;
        int h = high;
        int p = a[low];
        int tag = 0;
        while(low != high){
            if(tag == 0) {
                if (a[high] < p) {
                    a[low] = a[high];
                    low++;
                    tag = 1;
                } else {
                    high--;
                }
            }else{
                if (p < a[low]) {
                    a[high] = a[low] ;
                    high--;
                    tag = 0;
                } else {
                    low++;
                }
            }

        }
        a[low] = p;
        tag = 0;
        System.out.println(" ");
        System.out.println("一轮分类结果");
        System.out.println(p);
        while (tag < a.length){
            System.out.print(a[tag]+",");
            tag++;
        }
        if(low > l+1 ) {
            low--;
            quickSort1.quickSortImpl(a, l, low);
        }
        if(high < h-1 ) {
            high++;
            quickSort1.quickSortImpl(a, high, h);
        }
    }
}
