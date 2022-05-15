package GUI;

import ClickerLogic.AutoClickerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    public ToggleGroup clickSpeedButtons;
    public RadioButton delayBetweenClicksRadioButton;
    public RadioButton clicksPerSecondRadioButton;

    public ToggleGroup totalClicksButtons;
    public RadioButton totalClicksRadioButton;
    public RadioButton repeatUntilStoppedRadioButton;

    public Spinner<Integer> minuteClickDelaySpinner;
    public Spinner<Integer> secondsClickDelaySpinner;
    public Spinner<Integer> millisecondsClickDelaySpinner;
    public Spinner<Integer> clicksPerSecondSpinner;
    public Spinner<Integer> totalClicksSpinner;

    public MenuButton mouseButtonMenuButton;
    public MenuButton clickMultipleMenuButton;

    public Button startButton;
    public Button stopButton;
    public Button setHotkeyButton;
    public TextArea totalClicksTextArea;


    private AutoClickerController autoClickerController;

    private final MenuItem menuItemLeftClick = new MenuItem("LeftClick");
    private final MenuItem menuItemMiddleClick = new MenuItem("MiddleClick");
    private final MenuItem menuItemRightClick = new MenuItem("RightClick");
    private final MenuItem menuItemTripleClick = new MenuItem("TripleClick");
    private final MenuItem menuItemSingleClick = new MenuItem("SingleClick");
    private final MenuItem menuItemDoubleClick = new MenuItem("DoubleClick");


    private int minuteClickDelaySpinnerValue;
    private int secondsClickDelaySpinnerValue;
    private int millisecondsClickDelaySpinnerValue;
    private int clicksPerSecondSpinnerValue;
    private int totalClicksSpinnerValue;

    private String mouseButtonString;
    private String clickMultipleString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Creating a SpinnerValueFactory to handle the aspects of the Spinner objects. Allowing ints of 0 to 999
        setupSpinnerValueFactories();

        // Adding the change listeners to update int fields as changes occur. Without this, local values will not update.
        addChangeListenersToSpinners();

        // Initialize the menu buttons to have selection options.
        initializeMenuItems();

        try {
            autoClickerController = new AutoClickerController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Each spinner needs its own unique ValueFactory, otherwise the values will change with each other.
     * Setting up all value selection spinners to accept ints ranging from 0 to 999
     * We do not need numbers larger than this. No support is provided for a click rate less than 1 click/999 minutes.
     * It is not possible with Java to have a sleep time less than 1 ms, so 1,000 is the max theoretical clicks per second.
     */
    private void setupSpinnerValueFactories() {
        minuteClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        secondsClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        millisecondsClickDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        clicksPerSecondSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
        totalClicksSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999));
    }

    /**
     * Adds a listener to every specified Spinner field.
     * This listener updates the int fields of the respective {@literal <Spinner>}Value
     *
     * Currently is lacking regex check for non-numeric values {@code if(!newValue.matches("\\d*"))}
     * This will allow users to enter invalid entries, but program will not run with text or decimals in fields.
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


    /**
     * Adds all MenuItems to their respective MenuButtons.
     * Sets the "On Action" of the MenuItems within the MenuButton to update the Button and assign the choice.
     *
     * In use, when "LeftClick" is selected, it updates the MenuButton to display that, and re-assigns the internal
     * String "mouseButtonString" to be used in other methods, tracking the current state.
     */
    private void initializeMenuItems() {
        // Adding MenuItems to MenuButtons
        mouseButtonMenuButton.getItems().addAll(menuItemLeftClick, menuItemMiddleClick, menuItemRightClick);
        clickMultipleMenuButton.getItems().addAll(menuItemSingleClick, menuItemDoubleClick, menuItemTripleClick);

        // Defining defaults of MenuButtons on startup.
        mouseButtonMenuButton.setText(menuItemLeftClick.getText());
        mouseButtonString = menuItemLeftClick.getText();

        clickMultipleMenuButton.setText(menuItemSingleClick.getText());
        clickMultipleString = menuItemSingleClick.getText();

        // Assigning the "onAction" of all MenuItems so when selected, values update.
        menuItemLeftClick.setOnAction(e -> {
            mouseButtonMenuButton.setText(menuItemLeftClick.getText());
            mouseButtonString = menuItemLeftClick.getText();
        });

        menuItemMiddleClick.setOnAction(e -> {
            mouseButtonMenuButton.setText(menuItemMiddleClick.getText());
            mouseButtonString = menuItemMiddleClick.getText();
        });

        menuItemRightClick.setOnAction(e -> {
            mouseButtonMenuButton.setText(menuItemRightClick.getText());
            mouseButtonString = menuItemRightClick.getText();
        });

        menuItemSingleClick.setOnAction(e -> {
            clickMultipleMenuButton.setText(menuItemSingleClick.getText());
            clickMultipleString = menuItemSingleClick.getText();
        });

        menuItemDoubleClick.setOnAction(e -> {
            clickMultipleMenuButton.setText(menuItemDoubleClick.getText());
            clickMultipleString = menuItemDoubleClick.getText();
        });

        menuItemTripleClick.setOnAction(e -> {
            clickMultipleMenuButton.setText(menuItemTripleClick.getText());
            clickMultipleString = menuItemTripleClick.getText();
        });
    }


    public void printHelloMethod() {
        System.out.println("Hello, this is from the GUIController class.");
    }

    public void startClicker(ActionEvent event) {
        System.out.println("Start Clicker from GUIController class.");
        autoClickerController.start();
    }
}
