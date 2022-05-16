package ClickerLogic;

public class AutoClicker {

    private AutoClickerController clickerController;
    public AutoClicker(AutoClickerController clickerController) {
        this.clickerController = clickerController;
    }

    /**
     * Given either a specified delay between clicks, or an int of clicks per second, defines how fast to click.
     *
     * @return The number of clicks per second given the selected options.
     */
    public void startAutoClicker() {
        System.out.println("Delay is: " + calculateTimeBetweenClicks(clickerController.getMinuteClickDelaySpinnerValue(), clickerController.getSecondsClickDelaySpinnerValue(), clickerController.getMillisecondsClickDelaySpinnerValue()));
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
}
