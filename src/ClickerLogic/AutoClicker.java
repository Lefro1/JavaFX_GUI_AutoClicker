package ClickerLogic;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {

    private final AutoClickerController clickerController;
    private final KeyboardListener keyboardListener;
    private final Robot robot;
    private int delayBetweenCycles;
    private int totalClicks;


    public AutoClicker(AutoClickerController clickerController) throws AWTException, NativeHookException {
        this.clickerController = clickerController;
        this.keyboardListener = clickerController.getKeyboardListener();
        robot = new Robot();
        totalClicks = 0;


//        try {
//            GlobalScreen.registerNativeHook();
//            GlobalScreen.isNativeHookRegistered();
//            GlobalScreen.addNativeKeyListener(keyboardListener);
//        } catch (NativeHookException e) {
//            e.printStackTrace();
//        }



       // clickerController.getKeyboardListener().start();
    }

    /**
     * Given either a specified delay between clicks, or an int of clicks per second, defines how fast to click.
     *
     */
    public void startAutoClicker() throws InterruptedException {
        totalClicks = 0;

        boolean isClicksPerSecond = clickerController.isClicksPerSecond();
        boolean isRepeatUntilStopped = clickerController.isRepeatUntilStopped();

        try {
            delayBetweenCycles = calculateTimeBetweenClicks(
                    clickerController.getMinuteClickDelaySpinnerValue(),
                    clickerController.getSecondsClickDelaySpinnerValue(),
                    clickerController.getMillisecondsClickDelaySpinnerValue(),
                    isClicksPerSecond);
        }
        catch (Exception e) {
            return;
        }

        clickerVersionDecider(isClicksPerSecond, isRepeatUntilStopped);


    }

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

    private void clickerVersionDecider(boolean isClicksPerSecond, boolean isRepeatingUntilStopped) throws InterruptedException {
        System.out.println("Reached clickerVersionDecider");
        String mouseButton = clickerController.getMouseButtonString();
        String clickMultiple = clickerController.getClickMultipleString();


        int mouseEvent;

        //Just using a switch statement because it's fun, if/else may have been more readable.
        switch(mouseButton) {
            case ClickOptions.LEFT_CLICK:
                mouseEvent = InputEvent.BUTTON1_DOWN_MASK;
                break;
            case ClickOptions.MIDDLE_CLICK:
                mouseEvent = InputEvent.BUTTON2_DOWN_MASK;
                break;
            case ClickOptions.RIGHT_CLICK:
                mouseEvent = InputEvent.BUTTON3_DOWN_MASK;
            default:
                throw new IllegalArgumentException("mouse button " + mouseButton + "Found to be invalid.");
        }

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
     * While the auto clicker is running, click the specified number of clicks per cycle at
     *
     * @param mouseEvent determines if robot will left, middle, or right click.
     * @param numberOfClicksPerCycle determines if we will single, double, or triple click.
     */
    private void startRepeatUntilStoppedClicker(int mouseEvent, int numberOfClicksPerCycle) throws InterruptedException {
        // CURRENT PROBLEM:
        // Upon starting while(isActive), the nativeKeyPressed() method in KeyboardListener stops.
        // Only upon "stop()" being selected on the app, which calls public void stopAutoClicker() through
        // Another means, and re-assigns the "isAutoClickerClicking" variable to false, does the loop stop.

        // Upon the loop stopping, all "cached" key commands enter at once. Back to back.
        // For example, if you spam clicked the "toggle" (stop/start) key.
        // After clicking the stop button by hand, the cached key command of you hitting the toggle button would register.
        // Because isAutoClickerClicking is now false, it starts the auto clicker again.
        // This will repeat for all cached key input entries while the loop was occurring.



        System.out.println("reached startClicksPerSecondRepeatUntilStopped");
        keyboardListener.setIsAutoClickerClicking(true);
        int counter = 0;
        Thread.sleep(1000);
        for(int i = 0; i < 1000; i++) {
        //while(keyboardListener.getIsAutoClickerClicking()) {
            System.out.println("isActive     " + keyboardListener.getIsAutoClickerClicking());
                robot.mousePress(mouseEvent);
                robot.mouseRelease(mouseEvent);
            keyboardListener.checkIn();
            try {
                Thread.sleep(delayBetweenCycles);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //clickerController.getGuiController().totalClicksTextArea.setText(String.valueOf(totalClicks++));
        }
        keyboardListener.setIsAutoClickerClicking(false);
    }

    private void startMaxTotalClicksClicker(int mouseEvent, int numberOfClicksPerCycle, int maxClicks) {

    }
}
