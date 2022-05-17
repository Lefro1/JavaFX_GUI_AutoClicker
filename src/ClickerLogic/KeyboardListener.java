package ClickerLogic;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardListener implements NativeKeyListener {

    private final AutoClickerController clickerController;
    private int toggleKeyCode;
    private String toggleKey;
    private NativeKeyEvent mostRecentNativeKeyEvent;
    private long mostRecentKeyPressTime;
    private boolean isAutoClickerClicking;

    /**
     * Constructs a KeyboardListener object. Uses JNativeHook in order to have a global key listener.
     * This allows the program to respond to user key inputs (assign and use a toggle key for the clicker).
     * @param clickerController The AutoClickerController object using this keyboard listener.
     */
    public KeyboardListener(AutoClickerController clickerController) {
        this.clickerController = clickerController;
        this.toggleKey = "Back Quote";
    }


    /**
     * Initiates a global keyboard listener. Allows methods "nativeKey..." to be used.
     */
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

    /**
     * After the {@code start()} method is called, this listener is on reading what keys are pressed.
     * This allows for toggle buttons to work while the application is not selected.
     * @param e Whenever a key is pressed, this is that key.
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        mostRecentNativeKeyEvent = e;
        mostRecentKeyPressTime = System.currentTimeMillis();

        // If the key pressed is the toggle key, check the current start/stop state. Start if stopped, stop if running.
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals(NativeKeyEvent.getKeyText(toggleKeyCode))) {

            if (!isAutoClickerClicking) {
                try {
                    clickerController.start();
                } catch (InterruptedException interruptedException) {
                    clickerController.stop();
                    interruptedException.printStackTrace();
                }
            } else {
                clickerController.stop();
            }
        }
    }

    /**
     * Checks for the next key pressed after being called. Sets that as the new stop key.
     *
     * @return The new stop key code
     */
    public String setToggleKeyCode() throws InterruptedException {
        long localMostRecentKeyPress = mostRecentKeyPressTime;

        // Waits for the most recent key press to update. When the most recent key press (time of key press)
        // Is no longer equal to the previous most recent key press, assign the most recent key.
        while (localMostRecentKeyPress == mostRecentKeyPressTime) {
            Thread.sleep(10);
        }

        this.toggleKeyCode = mostRecentNativeKeyEvent.getKeyCode();
        this.toggleKey = NativeKeyEvent.getKeyText(mostRecentNativeKeyEvent.getKeyCode());

        return toggleKey;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent arg0) {
    }

    public void setIsAutoClickerClicking(boolean isAutoClickerClicking) {
        this.isAutoClickerClicking = isAutoClickerClicking;
    }

    public boolean getIsAutoClickerClicking() {
        return isAutoClickerClicking;
    }
}