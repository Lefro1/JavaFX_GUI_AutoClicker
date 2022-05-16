package ClickerLogic;

import java.awt.*;

public class AutoClicker {

    private AutoClickerController clickerController;
    private Robot robot;

    public AutoClicker(AutoClickerController clickerController) throws AWTException {
        this.clickerController = clickerController;
        robot = new Robot();
    }

    /**
     * Given either a specified delay between clicks, or an int of clicks per second, defines how fast to click.
     *
     * @return The number of clicks per second given the selected options.
     */
    public void startAutoClicker() {
        System.out.println("Delay is: " + calculateTimeBetweenClicks(clickerController.getMinuteClickDelaySpinnerValue(), clickerController.getSecondsClickDelaySpinnerValue(), clickerController.getMillisecondsClickDelaySpinnerValue()));

        boolean isClicksPerSecond = clickerController.isClicksPerSecond();
        boolean isRepeatUntilStopped = clickerController.isRepeatUntilStoppedRadioButton();

        clickerVersionDecider(isClicksPerSecond, isRepeatUntilStopped);

    }

    public void stopAutoClicker() {

    }

    /**
     *
     * @param minutes the time to wait between clicks in minutes.
     * @param seconds the time to wait between clicks in seconds.
     * @param milliseconds the time to wait between clicks in milliseconds.
     * @return the time between clicks in milliseconds.
     */
    private int calculateTimeBetweenClicks(int minutes, int seconds, int milliseconds) {
        return milliseconds + seconds * 1000 + minutes * 60 * 1000;
    }

    private void clickerVersionDecider(boolean isClicksPerSecond, boolean isRepeatingUntilStopped) {
        String mouseButton = clickerController.getMouseButtonString();
        String clickMultiple = clickerController.getClickMultipleString();

        if(isClicksPerSecond && isRepeatingUntilStopped) {
            startClicksPerSecondRepeatUntilStopped(mouseButton, clickMultiple);
        }

        if(isClicksPerSecond && !isRepeatingUntilStopped) {
            startClicksPerSecondTotalClicks(mouseButton, clickMultiple);
        }

        if(!isClicksPerSecond && isRepeatingUntilStopped) {
            startDelayBetweenClicksRepeatUntilStopped(mouseButton, clickMultiple);
        }

        if(!isClicksPerSecond && !isRepeatingUntilStopped) {
            startDelayBetweenClicksTotalClicks(mouseButton, clickMultiple);
        }


    }

    private void startClicksPerSecondRepeatUntilStopped(String mouseButton, String clickMultiple) {

    }

    private void startClicksPerSecondTotalClicks(String mouseButton, String clickMultiple) {

    }

    private void startDelayBetweenClicksRepeatUntilStopped(String mouseButton, String clickMultiple) {

    }

    private void startDelayBetweenClicksTotalClicks(String mouseButton, String clickMultiple) {

    }
}
