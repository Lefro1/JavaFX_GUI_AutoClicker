package ClickerLogic;

import GUI.GUIController;

import java.io.IOException;

public class AutoClickerController {

    private GUIController controller;
    private KeyboardListener keyboardListener;

    /**
     * Used to create a connection between this class and the GUIController class.
     * Passes a reference of this object to the KeyboardListener so it can recognize inputs and use methods.
     * @throws IOException If the resource path is invalid.
     */
    public AutoClickerController(GUIController guiController) throws IOException {
        controller = guiController;
        keyboardListener = new KeyboardListener(this);


        keyboardListener.start();
    }

    /**
     * Start the auto clicker. Controls the state of isAutoClickerClicking as it can be started via button press as well.
     */
    public void start() {
        System.out.println("AutoClickerController start() method.");
        keyboardListener.setIsAutoClickerClicking(true);
    }

    /**
     * Stop the auto clicker. Controls the state of isAutoClickerClicking as it can be started via button press as well.
     */
    public void stop() {
        System.out.println("AutoClickerController stop() method.");
        keyboardListener.setIsAutoClickerClicking(false);
    }

    /**
     * Assign a new hotkey to the auto clicker.
     */
    public String setHotkey() throws InterruptedException {
        return keyboardListener.setToggleKeyCode();
    }
}
