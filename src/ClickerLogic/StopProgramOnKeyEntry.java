package ClickerLogic;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StopProgramOnKeyEntry implements NativeKeyListener{

    private final String stopKeyCode;

    public StopProgramOnKeyEntry(String keyCode) {
        this.stopKeyCode = keyCode;
    }

    public StopProgramOnKeyEntry() {
        this.stopKeyCode = "Back Quote";
    }


    public void start() {
        // Get the logger for "com.github.kwhat.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals(stopKeyCode)) {
            System.exit(0);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent arg0) {
    }
}