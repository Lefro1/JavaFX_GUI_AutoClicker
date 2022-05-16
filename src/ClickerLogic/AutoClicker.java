package ClickerLogic;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {

    private AutoClickerController clickerController;
    private Robot robot;
    private boolean isActive = false;
    private int delayBetweenCycles;


    public AutoClicker(AutoClickerController clickerController) throws AWTException {
        this.clickerController = clickerController;
        robot = new Robot();
    }

    /**
     * Given either a specified delay between clicks, or an int of clicks per second, defines how fast to click.
     *
     */
    public void startAutoClicker() throws InterruptedException {
        isActive = true;

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
            isActive = false;
            return;
        }

        clickerVersionDecider(isClicksPerSecond, isRepeatUntilStopped);


    }

    public void stopAutoClicker() {
        isActive = false;
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
        System.out.println("isClicksPerSecond " + isClicksPerSecond);
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


        System.out.printf("MouseButton %s     ClickMultiple %s\n", mouseButton, clickMultiple);
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
        System.out.printf("isClicksPerSecond %s     isRepeatingUntilStopped %s\n", isClicksPerSecond, isRepeatingUntilStopped);
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
        System.out.println("reached startClicksPerSecondRepeatUntilStopped");
        int counter = 0;
        while(true) {
            System.out.print("isActive     " + isActive);
                robot.mousePress(mouseEvent);
                robot.mouseRelease(mouseEvent);
            Thread.sleep(delayBetweenCycles);
            if(!isActive) {
                System.out.println("Not Active.");
                return;
            }
        }
    }

    private void startMaxTotalClicksClicker(int mouseEvent, int numberOfClicksPerCycle, int maxClicks) {

    }
}
