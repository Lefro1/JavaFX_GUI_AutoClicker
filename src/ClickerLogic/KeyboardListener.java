package ClickerLogic;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardListener implements NativeKeyListener{

    private final AutoClickerController clickerController;
    private int toggleKeyCode;
    private String toggleKey;
    private NativeKeyEvent mostRecentNativeKeyEvent;
    private long mostRecentKeyPressTime;
    private boolean isAutoClickerClicking;

    public KeyboardListener(AutoClickerController clickerController, String stopKey) {
        this.clickerController = clickerController;
        this.toggleKey = stopKey;
    }

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
     *
     * @param e Whenever a key is pressed, this is that key.
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        mostRecentNativeKeyEvent = e;
        mostRecentKeyPressTime = System.currentTimeMillis();


        // If the key pressed is the toggle key, check the current start/stop state. Start if stopped, stop if running.
        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals(NativeKeyEvent.getKeyText(toggleKeyCode))) {

            if(isAutoClickerClicking) {
                clickerController.stop();
            }
            else {
                try {
                    clickerController.start();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }

        }
    }

    /**
     * Checks for the next key pressed after being called. Sets that as the new stop key.
     * @return The new stop key code
     */
    public String setToggleKeyCode() throws InterruptedException {
        long localMostRecentKeyPress = mostRecentKeyPressTime;

        // Waits for the most recent key press to update. When the most recent key press (time of key press)
        // Is no longer equal to the previous most recent key press, assign the most recent key.
        while(localMostRecentKeyPress == mostRecentKeyPressTime) {
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
}