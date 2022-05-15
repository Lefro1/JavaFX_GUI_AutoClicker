package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    public GridPane clickOptionsGridPane;
    public GridPane clickIntervalGridPane;
    public RadioButton clicksPerSecondRadioButton;
    public RadioButton delayBetweenClicksRadioButton;
    public RadioButton repeatUntilStoppedRadioButton;

    public Spinner<Integer> minuteClickDelaySpinner;
    public Spinner<Integer> secondsClickDelaySpinner;
    public Spinner<Integer> millisecondsClickDelaySpinner;
    public Spinner<Integer> clicksPerSecondSpinner;
    public Spinner<Integer> totalClicksSpinner;

    public ToggleGroup totalClicksButtons;
    public ToggleGroup clickSpeedButtons;
    public RadioButton totalClicksRadioButton;

    public RowConstraints startStopGridPane;
    public Button startButton;
    public Button stopButton;
    public Button setHotkeyButton;

    private int minuteClickDelaySpinnerValue;
    private int secondsClickDelaySpinnerValue;
    private int millisecondsClickDelaySpinnerValue;
    private int clicksPerSecondSpinnerValue;
    private int totalClicksSpinnerValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Creating a SpinnerValueFactory to handle the aspects of the Spinner objects. Allowing ints of 0 to 999
        // We do not need numbers larger than this. No support is provided for a click rate less than 1 click/999 minutes.
        // It is not possible with Java to have a sleep time less than 1 ms, so 1,000 is the max theoretical clicks per second.
        setupSpinnerValueFactories();

        // Adding the change listeners to update int fields as changes occur.
        addChangeListenersToSpinners();
    }



    private void setupSpinnerValueFactories() {

        // Every spinner needs its own ValueFactory, otherwise the values will change with each other.
        minuteClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        secondsClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        millisecondsClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        clicksPerSecondSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        totalClicksSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
    }

    /**
     * Adds a listener to every specified Spinner field.
     * This listener updates the int fields of the respective {@literal <Spinner>}Value
     */
    private void addChangeListenersToSpinners() {
        minuteClickDelaySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                minuteClickDelaySpinnerValue = minuteClickDelaySpinner.getValue();
            }
        });
        secondsClickDelaySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                secondsClickDelaySpinnerValue = secondsClickDelaySpinner.getValue();
            }
        });
        millisecondsClickDelaySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                millisecondsClickDelaySpinnerValue = millisecondsClickDelaySpinner.getValue();
            }
        });
        clicksPerSecondSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                clicksPerSecondSpinnerValue = clicksPerSecondSpinner.getValue();
            }
        });
        totalClicksSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                totalClicksSpinnerValue = totalClicksSpinner.getValue();
            }
        });
    }


}
