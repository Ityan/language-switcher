package com.language.switcher;

public class Constants {
    public static Key FIRST_VALUE;
    public static Key SECOND_VALUE;
    public static String DEFAULT_LANG;
    public static final String FIRST_KEY = "firstKey";
    public static final String SECOND_KEY = "secondKey";
    public static final String LANGUAGE_KEY = "language";

    public static final String FILE_NAME = "property/keys.properties";
    public static final String DIR_NAME = "property";

    public static final boolean MAC_OS = System.getProperty("os.name").toLowerCase().contains("mac");
    public static final boolean WIN_OS = System.getProperty("os.name").toLowerCase().contains("win");
}
