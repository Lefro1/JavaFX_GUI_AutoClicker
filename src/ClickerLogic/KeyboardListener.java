package ClickerLogic;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lombok.SneakyThrows;

import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardListener implements NativeKeyListener {

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

    /**
     *
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
     *
     * @param e Whenever a key is pressed, this is that key.
     */
    @SneakyThrows
    public void nativeKeyPressed(NativeKeyEvent e) {
        mostRecentNativeKeyEvent = e;
        mostRecentKeyPressTime = System.currentTimeMillis();

        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + "     toggleKeyCodeL: " + NativeKeyEvent.getKeyText(toggleKeyCode));


        // If the key pressed is the toggle key, check the current start/stop state. Start if stopped, stop if running.
        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals(NativeKeyEvent.getKeyText(toggleKeyCode))) {

            if(!isAutoClickerClicking) {
                clickerController.start();
            }
            else {
                clickerController.stop();
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

    public boolean getIsAutoClickerClicking() {
        return isAutoClickerClicking;
    }

    public void checkIn() {
        System.out.println("Check in!");
    }






    // Create a new global hotkey listener
//    public class GlobalKeyListener implements NativeKeyListener{
//        @SneakyThrows
//        public void nativeKeyPressed(NativeKeyEvent keyPress) {
//            // If the hotkey is pressed and the clicker isn't running, start it. If it is running, stop it.
//            if (keyPress.getKeyCode() == toggleKeyCode) {
//                if (!isAutoClickerClicking) {
//                    clickerController.start();
//                } else {
//                    clickerController.stop();
//                }
//                }
//            }
//
//            // If we're waiting for a hotkey to be pressed, make sure we can set it all up
////            if(waitingForHotkey){
////                hotkeyCode = keyPress.getKeyCode();
////                hotkeyField.setText(NativeKeyEvent.getKeyText(hotkeyCode));
////
////                // Update the buttons when a new hotkey is picked
////                startButton.setText("Start (" + NativeKeyEvent.getKeyText(hotkeyCode) + ")");
////                stopButton.setText("Stop (" + NativeKeyEvent.getKeyText(hotkeyCode) + ")");
////
////                waitingForHotkey = false;
//    }
}