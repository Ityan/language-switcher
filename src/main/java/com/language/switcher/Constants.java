package com.language.switcher;

public class Constants {
    public static Key FIRST_VALUE;
    public static Key SECOND_VALUE;
    public static final String FILE_NAME = "keys/keys.properties";
    public static final String DIR_NAME = "keys";

    public static final boolean MAC_OS = System.getProperty("os.name").toLowerCase().contains("mac");
    public static final boolean WIN_OS = System.getProperty("os.name").toLowerCase().contains("win");

    public static final String FIRST_KEY = "firstKey";
    public static final String SECOND_KEY = "secondKey";
}
