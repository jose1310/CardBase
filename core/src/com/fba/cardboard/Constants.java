package com.fba.cardboard;

public class Constants {
    private static final Constants instance = new Constants();
    public static int zValue=0;

    private Constants(){}

    public static Constants getInstance(){
        return instance;
    }

    public int getZ() {
        zValue++;
        return zValue;
    }
}