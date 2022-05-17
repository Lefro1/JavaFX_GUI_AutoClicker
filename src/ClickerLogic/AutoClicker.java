package ClickerLogic;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {

    private final AutoClickerController clickerController;
    private final KeyboardListener keyboardListener;
    private int delayBetweenCycles;
    private int totalClicks;


    public AutoClicker(AutoClickerController clickerController) {
        this.clickerController = clickerController;
        this.keyboardListener = clickerController.getKeyboardListener();
    }

    /**
     * Given either a specified delay between clicks, or an int of clicks per second, defines how fast to click.
     *
     */
    public void startAutoClicker() {
        totalClicks = 0;

        boolean isClicksPerSecond = clickerController.isClicksPerSecond();
        boolean isRepeatUntilStopped = clickerController.isRepeatUntilStopped();


        delayBetweenCycles = calculateTimeBetweenClicks(
                clickerController.getMinuteClickDelaySpinnerValue(),
                clickerController.getSecondsClickDelaySpinnerValue(),
                clickerController.getMillisecondsClickDelaySpinnerValue(),
                isClicksPerSecond);

        clickerVersionDecider(isRepeatUntilStopped);
    }

    /**
     * Stops the auto clicker by re-assigning the keyboardListener field, causing the while() loops
     * inside the start...Clicker() methods to be while(false) and exit.
     */
    public void stopAutoClicker() {
        keyboardListener.setIsAutoClickerClicking(false);
    }

    /**
     *
     * @param minutes the time to wait between clicks in minutes.
     * @param seconds the time to wait between clicks in seconds.
     * @param milliseconds the time to wait between clicks in milliseconds.
     * @return the time between clicks in milliseconds. Returns ints due to being the accepted datatype of delay commands.
     */
    private int calculateTimeBetweenClicks(int minutes, int seconds, int milliseconds, boolean isClicksPerSecond) {
        // If specified clicks/second, pause this many milliseconds between cycles:
        if(isClicksPerSecond) {
            int clicksPerSecond = clickerController.getClicksPerSecondSpinnerValue();
            if(clicksPerSecond == 0) {
                throw new IllegalArgumentException("Clicks per second cannot be 0 if that is the metric you are using.");
            }
            return (int) Math.ceil(1000/ (double) clicksPerSecond);
        }

        // If specified delay time between clicks, pause this many milliseconds between cycles:
        int clickDelay = milliseconds + seconds * 1000 + minutes * 60 * 1000;

        if(clickDelay == 0) {
            throw new IllegalArgumentException("There must be a non-zero delay value if that is the metric you are using.");
        }

        return clickDelay;
    }

    /**
     * Based on the user's input on the GUI, this will call the correct function with the correct parameters
     * to start the auto clicker. This method only routes the information to the start...Clicker() methods.
     *
     * @param isRepeatingUntilStopped The radio button selected on the GUI determines if there is a set maximum number
     *                                of clicks, or if the clicker will continue until stopped.
     */
    private void clickerVersionDecider(boolean isRepeatingUntilStopped) {
        String mouseButton = clickerController.getMouseButtonString();
        String clickMultiple = clickerController.getClickMultipleString();


        int mouseEvent;

        // Just using a switch statement because it's fun, if/else may have been more readable.
        // Changes mouse button the clicker uses based on user input.
        switch(mouseButton) {
            case ClickOptions.LEFT_CLICK:
                mouseEvent = InputEvent.BUTTON1_DOWN_MASK;
                break;
            case ClickOptions.MIDDLE_CLICK:
                mouseEvent = InputEvent.BUTTON2_DOWN_MASK;
                break;
            case ClickOptions.RIGHT_CLICK:
                mouseEvent = InputEvent.BUTTON3_DOWN_MASK;
                break;
            default:
                throw new IllegalArgumentException("mouse button " + mouseButton + "Found to be invalid.");
        }

        // Changes the number of clicks per cycle (single, double, triple) based on the user's input.
        int numberOfClicksPerCycle;
        switch(clickMultiple) {
            case ClickOptions.SINGLE_CLICK:
                numberOfClicksPerCycle = 1;
                break;
            case ClickOptions.DOUBLE_CLICK:
                numberOfClicksPerCycle = 2;
                break;
            case ClickOptions.TRIPLE_CLICK:
                numberOfClicksPerCycle = 3;
                break;
            default:
                throw new IllegalArgumentException("mouse button " + mouseButton + "Found to be invalid.");
        }
        if(isRepeatingUntilStopped) {
            startRepeatUntilStoppedClicker(mouseEvent, numberOfClicksPerCycle);
        }
        else {
            startMaxTotalClicksClicker(mouseEvent, numberOfClicksPerCycle, clickerController.getTotalClicksSpinnerValue());
        }
    }

    /**
     * Starts an auto clicker that will continue to run until the user either presses the stop button, or presses
     * the key assigned to toggle the auto clicker.
     *
     * @param mouseEvent determines if robot will left, middle, or right click.
     * @param numberOfClicksPerCycle determines if we will single, double, or triple click.
     */
    private void startRepeatUntilStoppedClicker(int mouseEvent, int numberOfClicksPerCycle) {

        keyboardListener.setIsAutoClickerClicking(true);

        // Define clicking function to be inside its own thread. Otherwise, the EventListener in KeyboardListener
        // Will run into problems and stack. The EventListener stalls during the while loop to click.
        // This prevents stopping the clicker via. key press after it starts.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Robot robot = new Robot();
                    while (keyboardListener.getIsAutoClickerClicking()) {
                        for(int i = 0; i < numberOfClicksPerCycle; i++) {
                            robot.mousePress(mouseEvent);
                            robot.mouseRelease(mouseEvent);
                            clickerController.getGuiController().totalClicksTextArea.setText(String.valueOf(++totalClicks));
                        }
                        try {
                            Thread.sleep(delayBetweenCycles);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        };
        // Start the thread. I'm not worrying about stopping it anywhere as I am a novice at multithreading, and this works.
        Thread clickingThread = new Thread(runnable);
        clickingThread.start();

    }

    /**
     * Starts an auto clicker that will continue to run until the user presses the stop button, presses
     * the key assigned to toggle the auto clicker, or the auto clicker reaches the max number of clicks.
     *
     * @param mouseEvent determines if robot will left, middle, or right click.
     * @param numberOfClicksPerCycle determines if we will single, double, or triple click.
     */
    private void startMaxTotalClicksClicker(int mouseEvent, int numberOfClicksPerCycle, int maxClicks) {
        keyboardListener.setIsAutoClickerClicking(true);

        // Define clicking function to be inside its own thread. Otherwise, the EventListener in KeyboardListener
        // Will run into problems and stack. The EventListener stalls during the while loop to click.
        // This prevents stopping the clicker via. key press after it starts.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Robot robot = new Robot();
                    while (keyboardListener.getIsAutoClickerClicking()) {
                        for(int i = 0; i < numberOfClicksPerCycle; i++) {
                            robot.mousePress(mouseEvent);
                            robot.mouseRelease(mouseEvent);
                            clickerController.getGuiController().totalClicksTextArea.setText(String.valueOf(++totalClicks));
                        }

                        if(totalClicks >= maxClicks) {
                            keyboardListener.setIsAutoClickerClicking(false);
                            return;
                        }
                        try {
                            Thread.sleep(delayBetweenCycles);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        };
        // Start the thread. I'm not worrying about stopping it anywhere as I am a novice at multithreading, and this works.
        Thread clickingThread = new Thread(runnable);
        clickingThread.start();
    }
}
