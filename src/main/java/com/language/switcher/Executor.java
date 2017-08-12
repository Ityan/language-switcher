package com.language.switcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Executor {

    private static final Logger LOG = LogManager.getLogger(Executor.class);

    private static final String[] CURRENT_LANGUAGE = {"osascript", "-e",
            "tell application \"System Events\" to tell process \"SystemUIServer\"\n" +
            "    tell (1st menu bar item of menu bar 1 whose value of attribute \"AXDescription\" is \"text input\")\n" +
            "        return value\n" +
            "    end tell\n" +
            "end tell"};

    private final Runtime runtime;

    public Executor() {
        this.runtime = Runtime.getRuntime();
    }

    public String currentLanguage() {
        String result = "";

        try (InputStream is = exec(CURRENT_LANGUAGE).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                result = line;
            }
            reader.close();
        } catch (Exception e) {
            LOG.error("Can't read script execution result [" + Arrays.toString(CURRENT_LANGUAGE) + "]", e);
            throw new IllegalStateException(e);
        }

        return result;
    }

    public void switchLang(String lang) {
        String[] SET_LANG = {"osascript", "-e",
                "tell application \"System Events\" to tell process \"SystemUIServer\"\n" +
                "        tell (1st menu bar item of menu bar 1 whose description is \"text input\") " +
                "        to {click, click (menu 1's menu item \"" + lang + "\")}\n" +
                "end tell"};
        exec(SET_LANG);
    }

    private Process exec(String[] script) {
        try {
            return runtime.exec(script);
        } catch (IOException e) {
            LOG.error("Can't execute script [" + Arrays.toString(script) + "]", e);
            throw new IllegalStateException(e);
        }
    }
}
