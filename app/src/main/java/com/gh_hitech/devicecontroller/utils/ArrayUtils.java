package com.gh_hitech.devicecontroller.utils;

/**
 * @author yijigu
 */
public class ArrayUtils {
    public static void  copyIntArray(int[] oldArray,int[] newArray){
        if(oldArray.length != newArray.length) {
            throw new RuntimeException("Array size didn't match");
        }
        for (int i = 0; i<oldArray.length;i++){
            newArray[i] = oldArray[i];
        }
    }

    public static void  copyStringArray(String[] oldArray,String[] newArray){
        if(oldArray.length != newArray.length) {
            throw new RuntimeException("Array size didn't match");
        }
        for (int i = 0; i<oldArray.length;i++){
            newArray[i] = oldArray[i];
        }
    }

    public static void  copyIntArrayByDesc(int[] oldArray,int[] newArray){
        if(oldArray.length != newArray.length) {
            throw new RuntimeException("Array size didn't match");
        }
        for (int i = 0; i<oldArray.length;i++){
            newArray[oldArray.length-1-i] = oldArray[i];
        }
    }


    public static void  copyCharArrayByDesc(char[] oldArray,char[] newArray){
        if(oldArray.length != newArray.length) {
            throw new RuntimeException("Array size didn't match");
        }
        for (int i = 0; i< oldArray.length;i++){
            newArray[oldArray.length-1-i] = oldArray[i];
        }
    }
}
