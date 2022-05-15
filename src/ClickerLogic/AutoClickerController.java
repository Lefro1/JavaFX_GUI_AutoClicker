package ClickerLogic;

import GUI.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AutoClickerController {

    private GUIController controller;

    /**
     * Used to create a connection between this class and the GUIController class.
     * @throws IOException If the resource path is invalid.
     */
    public AutoClickerController(GUIController guiController) throws IOException {
        controller = guiController;
    }

    /**
     * Start the auto clicker.
     */
    public void start() {
        System.out.println("AutoClickerController start() method.");
        controller.printHelloMethod();
    }

    /**
     * Stop the auto clicker.
     */
    public void stop() {

    }

    /**
     * Assign a new hotkey to the auto clicker.
     */
    public void setHotkey() {

    }
}
