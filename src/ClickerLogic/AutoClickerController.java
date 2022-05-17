package ClickerLogic;

import GUI.GUIController;
import java.awt.*;
import java.io.IOException;

public class AutoClickerController {

    private GUIController guiController;
    private KeyboardListener keyboardListener;
    private Robot robot;
    private AutoClicker autoClicker;


    /**
     * Used to create a connection between this class and the GUIController class.
     * Passes a reference of this object to the KeyboardListener so it can recognize inputs and use methods.
     * @throws IOException If the resource path is invalid.
     */
    public AutoClickerController(GUIController guiController) throws IOException, AWTException {
        this.guiController = guiController;
        keyboardListener = new KeyboardListener(this);
        autoClicker = new AutoClicker(this);
        robot = new Robot();

        keyboardListener.start();
    }

    /**
     * Start the auto clicker. Forces the GUIController class to update its values to their current entered amounts.
     */
    public void start() throws InterruptedException {
        // By default, the Spinner value only "sets" itself upon clicking the arrow.
        // By calling increment 0, we do not change the value of the Spinner, BUT, it "sets" itself to the entered value.
        guiController.minuteClickDelaySpinner.increment(0);
        guiController.secondsClickDelaySpinner.increment(0);
        guiController.millisecondsClickDelaySpinner.increment(0);
        guiController.clicksPerSecondSpinner.increment(0);
        guiController.totalClicksSpinner.increment(0);

        // Reset the click counter on every start. Also done numerically in AutoClicker but this guarantees before click 1 it works properly.
        guiController.totalClicksTextArea.setText("0");

        autoClicker.startAutoClicker();
    }

    /**
     * Stop the auto clicker.
     */
    public void stop() {
        autoClicker.stopAutoClicker();
    }

    /**
     * Assign a new hotkey to toggle the auto clicker on/off.
     */
    public String setHotkey() throws InterruptedException {
        return keyboardListener.setToggleKeyCode();
    }



    public int getMinuteClickDelaySpinnerValue() {
        return guiController.getMinuteClickDelaySpinnerValue();
    }

    public int getSecondsClickDelaySpinnerValue() {
        return guiController.getSecondsClickDelaySpinnerValue();
    }

    public int getMillisecondsClickDelaySpinnerValue() {
        return guiController.getMillisecondsClickDelaySpinnerValue();
    }

    public int getClicksPerSecondSpinnerValue() {
        return guiController.getClicksPerSecondSpinnerValue();
    }

    public int getTotalClicksSpinnerValue() {
        return guiController.getTotalClicksSpinnerValue();
    }

    public String getMouseButtonString() {
        return guiController.getMouseButtonString();
    }

    public String getClickMultipleString() {
        return guiController.getClickMultipleString();
    }

    public boolean isClicksPerSecond() {
        return guiController.isClicksPerSecond();
    }

    public boolean isRepeatUntilStopped() {
        return guiController.isRepeatUntilStopped();
    }

    public GUIController getGuiController() {
        return this.guiController;
    }

    public KeyboardListener getKeyboardListener() {
        return this.keyboardListener;
    }
}
