package com.language.switcher;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class Run {
    public static void main(String[] args) {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            PlatformImpl.setTaskbarApplication(false);
            Properties props = System.getProperties();
            props.put("prism.order", "sw");
            Application.launch(Window.class, args);
            return null;
        });
    }
}
