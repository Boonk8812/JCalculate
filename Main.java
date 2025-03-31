import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends JFrame implements ActionListener {

    private static final String APPLICATION_NAME = "JCalculate";
    private static final String AUTHOR = "Boonk8812 (Declan Murphy)";
    private static final LocalDate REVISION_DATE = LocalDate.of(2025, 3, 31);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final String SEPARATOR = "----------------------------------------";

    private JTextField expressionTextField;
    private JTextArea outputTextArea;
    private JButton calculateButton, clearButton, exitButton;
    private JButton addBtn, subtractBtn, multiplyBtn, divideBtn, powerBtn, sqrtBtn, logBtn, log10Btn;
    private JButton sinBtn, cosBtn, tanBtn, asinBtn, acosBtn, atanBtn, absBtn, ceilBtn, floorBtn, roundBtn, expBtn, maxBtn, minBtn;
    private JButton menuButton, commandLineButton; // Added buttons to switch modes

    private ScriptEngine engine;
    private int currentMode = 0; // 0 for Menu Mode, 1 for Command Line Mode
    private Map<Integer, String> MENU_OPTIONS = new HashMap<>();
    private LogHandler logHandler; // Instance of LogHandler

    public Main() {
        // Initialize ScriptEngine
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("graal.js");

        // Initialize LogHandler
        logHandler = new LogHandler();

        // Set frame properties
        setTitle(APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());

        // Initialize Menu Options
        populateMenuOptions();

        // Create components
        createHeaderPanel();
        createInputPanel();
        createButtonPanel();
        createOutputPanel();

        // Make the frame visible
        setVisible(true);
    }

    private void populateMenuOptions() {
        MENU_OPTIONS.put(1, "Calculate Expression (JavaScript)");
        MENU_OPTIONS.put(2, "Calculate Expression (Java Math)");
        MENU_OPTIONS.put(3, "Add");
        MENU_OPTIONS.put(4, "Subtract");
        MENU_OPTIONS.put(5, "Multiply");
        MENU_OPTIONS.put(6, "Divide");
        MENU_OPTIONS.put(7, "Power");
        MENU_OPTIONS.put(8, "Square Root");
        MENU_OPTIONS.put(9, "Logarithm (base e)");
        MENU_OPTIONS.put(10, "Logarithm (base 10)");
        MENU_OPTIONS.put(11, "Sine");
        MENU_OPTIONS.put(12, "Cosine");
        MENU_OPTIONS.put(13, "Tangent");
        MENU_OPTIONS.put(14, "Arcsine");
        MENU_OPTIONS.put(15, "Arccosine");
        MENU_OPTIONS.put(16, "Arctangent");
        MENU_OPTIONS.put(17, "Absolute Value");
        MENU_OPTIONS.put(18, "Ceiling");
        MENU_OPTIONS.put(19, "Floor");
        MENU_OPTIONS.put(20, "Round");
        MENU_OPTIONS.put(21, "Exponential (e^x)");
        MENU_OPTIONS.put(22, "Max");
        MENU_OPTIONS.put(23, "Min");
        MENU_OPTIONS.put(24, "Input raw math equation data via CLI");
        MENU_OPTIONS.put(0, "Exit Menu Mode");
        MENU_OPTIONS.put(99, "Kill Application");
        MENU_OPTIONS.put(98, "Switch to Command Line Mode");
        MENU_OPTIONS.put(97, "Switch to Menu Mode");
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(APPLICATION_NAME, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel authorLabel = new JLabel("Written by: " + AUTHOR, SwingConstants.CENTER);
        JLabel revisionLabel = new JLabel("Latest Revision Date: " + REVISION_DATE.format(DATE_FORMATTER), SwingConstants.CENTER);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        revisionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(authorLabel);
        headerPanel.add(revisionLabel);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel expressionLabel = new JLabel("Expression:");
        expressionTextField = new JTextField(40);
        inputPanel.add(expressionLabel);
        inputPanel.add(expressionTextField);
        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5, 5, 5));  // Adjusted grid layout
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addBtn = new JButton("Add");
        subtractBtn = new JButton("Subtract");
        multiplyBtn = new JButton("Multiply");
        divideBtn = new JButton("Divide");
        powerBtn = new JButton("Power");
        sqrtBtn = new JButton("Sqrt");
        logBtn = new JButton("Log");
        log10Btn = new JButton("Log10");
        sinBtn = new JButton("Sin");
        cosBtn = new JButton("Cos");
        tanBtn = new JButton("Tan");
        asinBtn = new JButton("Arcsin");
        acosBtn = new JButton("Arccos");
        atanBtn = new JButton("Arctan");
        absBtn = new JButton("Abs");
        ceilBtn = new JButton("Ceil");
        floorBtn = new JButton("Floor");
        roundBtn = new JButton("Round");
        expBtn = new JButton("Exp");
        maxBtn = new JButton("Max");
        minBtn = new JButton("Min");

        calculateButton = new JButton("Calculate");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        menuButton = new JButton("Menu");             // Added Menu Mode Button
        commandLineButton = new JButton("Command Line"); // Added Command Line Mode Button

        // Add action listeners to buttons
        addBtn.addActionListener(this);
        subtractBtn.addActionListener(this);
        multiplyBtn.addActionListener(this);
        divideBtn.addActionListener(this);
        powerBtn.addActionListener(this);
        sqrtBtn.addActionListener(this);
        logBtn.addActionListener(this);
        log10Btn.addActionListener(this);
        sinBtn.addActionListener(this);
        cosBtn.addActionListener(this);
        tanBtn.addActionListener(this);
        asinBtn.addActionListener(this);
        acosBtn.addActionListener(this);
        atanBtn.addActionListener(this);
        absBtn.addActionListener(this);
        ceilBtn.addActionListener(this);
        floorBtn.addActionListener(this);
        roundBtn.addActionListener(this);
        expBtn.addActionListener(this);
        maxBtn.addActionListener(this);
        minBtn.addActionListener(this);

        calculateButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);
        menuButton.addActionListener(this);           // Added action listener for Menu Mode
        commandLineButton.addActionListener(this);     // Added action listener for Command Line Mode

        // Add buttons to the panel
        buttonPanel.add(addBtn);
        buttonPanel.add(subtractBtn);
        buttonPanel.add(multiplyBtn);
        buttonPanel.add(divideBtn);
        buttonPanel.add(powerBtn);
        buttonPanel.add(sqrtBtn);
        buttonPanel.add(logBtn);
        buttonPanel.add(log10Btn);
        buttonPanel.add(sinBtn);
        buttonPanel.add(cosBtn);
        buttonPanel.add(tanBtn);
        buttonPanel.add(asinBtn);
        buttonPanel.add(acosBtn);
        buttonPanel.add(atanBtn);
        buttonPanel.add(absBtn);
        buttonPanel.add(ceilBtn);
        buttonPanel.add(floorBtn);
        buttonPanel.add(roundBtn);
        buttonPanel.add(expBtn);
        buttonPanel.add(maxBtn);
        buttonPanel.add(minBtn);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(menuButton);           // Added Menu Mode Button to GUI
        buttonPanel.add(commandLineButton);     // Added Command Line Mode Button to GUI

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        add(outputPanel, BorderLayout.EAST);
    }

    private void displayAppInfo() {
        outputTextArea.append("   $$$$$\\  $$$$$$\\            $$\\                     $$\\            $$\\               \n");
        outputTextArea.append("  \\__$$ |$$  __$$\\           $$ |                    $$ |           $$ |              \n");
        outputTextArea.append("     $$ |$$ /  \\__| $$$$$$\  $$ | $$$$$$$\\ $$\\   $$\\ $$ | $$$$$$\ $$$$$$\    $$$$$$\\  \n");
        outputTextArea.append("     $$ |$$ |       \\____$$\\ $$ |$$  _____|$$ |  $$ |$$ | \\<pad><pad><pad><pad>

















    \n");
        outputTextArea.append("$$\\   $$ |$$ |       $$$$$$$ |$$ |$$ /      $$ |  $$ |$$ | $$$$$$$ | $$ |    $$$$$$$$ |\n");
        outputTextArea.append("$$ |  $$ |$$ |  $$\\ $$  __$$ |$$ |$$ |      $$ |  $$ |$$ |$$  __$$ | $$ |$$\\ $$   ____|\n");
        outputTextArea.append("\\$$$$$$  |\\$$$$$$  |\\$$$$$$$ |$$ |\\$$$$$$$\\\ \$$$$$$  |$$ |\\$$$$$$$ | \\$$$$  |\\$$$$$$$\\\\n");
        outputTextArea.append(" \\______/  \\______/  \\_______|\\__| \\_______| \\______/ \\__| \\_______|  \\____/  \\_______|\n");
        outputTextArea.append("                                                                                       \n");
        outputTextArea.append("                                                                                       \n");
        outputTextArea.append("                                                                                       \n");

        outputTextArea.append(SEPARATOR + "\n");
        outputTextArea.append(APPLICATION_NAME + " - Java Swing Interface\n");
        outputTextArea.append("Written by: " + AUTHOR + "\n");
        outputTextArea.append("Latest Revision Date: " + REVISION_DATE.format(DATE_FORMATTER) + "\n");
        outputTextArea.append(SEPARATOR + "\n");
        showMenuMode(); // Start in Menu Mode
    }

    private double calculate(String expression) throws ScriptException {
        Object result = engine.eval(expression);
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            throw new ScriptException("Expression did not return a number");
        }
    }

    private double[] getNumbers(String expression) {
        String[] numberStrings = expression.split(" ");
        double[] numbers = new double[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            try {
                numbers[i] = Double.parseDouble(numberStrings[i]);
            } catch (NumberFormatException e) {
                outputTextArea.append("Invalid input: " + expression + "\n");
                return null;
            }
        }
        return numbers;
    }

    private void performCalculation(String expression) {
        try {
            if (!expression.isEmpty()) {
                double result = calculate(expression);
                outputTextArea.append("Result of " + expression + ": " + result + "\n");
                expressionTextField.setText("");
            } else {
                outputTextArea.append("Please enter an expression to calculate.\n");
            }
        } catch (ScriptException ex) {
            outputTextArea.append("Error: " + ex.getMessage() + "\n");
            logHandler.logError(ex); // Log the error
        } catch (Exception ex) {
            outputTextArea.append("An unexpected error occurred: " + ex.getMessage() + "\n");
            logHandler.logError(ex); // Log the error
        }
    }

    private void showMenuMode() {
        currentMode = 0;
        outputTextArea.setText("");
        displayAppInfo();
        outputTextArea.append("\n" + SEPARATOR + "\n");
        outputTextArea.append("Menu Mode: Please use the buttons for calculations.\n");
        outputTextArea.append(SEPARATOR + "\n");
        enableButtons(true);
        expressionTextField.setText("");
        expressionTextField.setEnabled(false);
    }

    private void showCommandLineMode() {
        currentMode = 1;
        outputTextArea.setText("");
        displayAppInfo();
        outputTextArea.append("\n" + SEPARATOR + "\n");
        outputTextArea.append("Command Line Mode: Enter expressions in the text field.\n");
        outputTextArea.append(SEPARATOR + "\n");
        enableButtons(false);
        expressionTextField.setText("");
        expressionTextField.setEnabled(true);
        expressionTextField.requestFocusInWindow();
    }

    private void enableButtons(boolean enable) {
        addBtn.setEnabled(enable);
        subtractBtn.setEnabled(enable);
        multiplyBtn.setEnabled(enable);
        divideBtn.setEnabled(enable);
        powerBtn.setEnabled(enable);
        sqrtBtn.setEnabled(enable);
        logBtn.setEnabled(enable);
        log10Btn.setEnabled(enable);
        sinBtn.setEnabled(enable);
        cosBtn.setEnabled(enable);
        tanBtn.setEnabled(enable);
        asinBtn.setEnabled(enable);
        acosBtn.setEnabled(enable);
        atanBtn.setEnabled(enable);
        absBtn.setEnabled(enable);
        ceilBtn.setEnabled(enable);
        floorBtn.setEnabled(enable);
        roundBtn.setEnabled(enable);
        expBtn.setEnabled(enable);
        maxBtn.setEnabled(enable);
        minBtn.setEnabled(enable);
        calculateButton.setEnabled(enable);  // keep calculate button enabled in menu mode.
        clearButton.setEnabled(enable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == calculateButton) {
                if (currentMode == 0) { // Menu Mode
                    performCalculation(expressionTextField.getText().trim());
                } else { // Command Line Mode
                    performCalculation(expressionTextField.getText().trim());
                }
            } else if (e.getSource() == clearButton) {
                expressionTextField.setText("");
                outputTextArea.setText("");
                if (currentMode == 0) {
                    showMenuMode();
                } else {
                    showCommandLineMode();
                }
            } else if (e.getSource() == exitButton) {
                System.exit(0);
            } else if (e.getSource() == menuButton) {
                showMenuMode();
            } else if (e.getSource() == commandLineButton) {
                showCommandLineMode();
            } else { // Handle other buttons for Menu Mode
                String command = e.getActionCommand();
                String currentExpression = expressionTextField.getText().trim();

                switch (command) {
                    case "Add":
                        currentExpression = currentExpression + " + ";
                        break;
                    case "Subtract":
                        currentExpression = currentExpression + " - ";
                        break;
                    case "Multiply":
                        currentExpression = currentExpression + " * ";
                        break;
                    case "Divide":
                        currentExpression = currentExpression + " / ";
                        break;
                    case "Power":
                        currentExpression = currentExpression + " Math.pow(";
                        break;
                    case "Sqrt":
                        currentExpression = currentExpression + " Math.sqrt(";
                        break;
                    case "Log":
                        currentExpression = currentExpression + " Math.log(";
                        break;
                    case "Log10":
                        currentExpression = currentExpression + " Math.log10(";
                        break;
                    case "Sin":
                        currentExpression = currentExpression + " Math.sin(";
                        break;
                    case "Cos":
                        currentExpression = currentExpression + " Math.cos(";
                        break;
                    case "Tan":
                        currentExpression = currentExpression + " Math.tan(";
                        break;
                    case "Arcsin":
                        currentExpression = currentExpression + " Math.asin(";
                        break;
                    case "Arccos":
                        currentExpression = currentExpression + " Math.acos(";
                        break;
                    case "Arctan":
                        currentExpression = currentExpression + " Math.atan(";
                        break;
                    case "Abs":
                        currentExpression = currentExpression + " Math.abs(";
                        break;
                    case "Ceil":
                        currentExpression = currentExpression + " Math.ceil(";
                        break;
                    case "Floor":
                        currentExpression = currentExpression + " Math.floor(";
                        break;
                    case "Round":
                        currentExpression = currentExpression + " Math.round(";
                        break;
                    case "Exp":
                        currentExpression = currentExpression + " Math.exp(";
                        break;
                    case "Max":
                        currentExpression = currentExpression + " Math.max(";
                        break;
                    case "Min":
                        currentExpression = currentExpression + " Math.min(";
                        break;
                    default:
                        currentExpression = currentExpression + command + "(";
                }
                expressionTextField.setText(currentExpression);
            } catch (NullPointerException ex) {
                outputTextArea.append("Error: NullPointerException - Please check your input.\n");
                logHandler.logError(ex); // Log the error
            } catch (Exception ex) {
                outputTextArea.append("An unexpected error occurred: " + ex.getMessage() + "\n");
                logHandler.logError(ex); // Log the error
            }
        }
    }

    public static void main(String[] args) {
        // Use invokeLater to ensure that the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
        });
    }
}

class LogHandler {
    private PrintWriter writer;

    public LogHandler() {
        try {
            writer = new PrintWriter(new FileWriter("error_log.txt", true)); // Append to the file
        } catch (IOException e) {
            // Handle the exception appropriately, e.g., display an error message
            System.err.println("Error initializing LogHandler: " + e.getMessage());
            // Consider throwing a RuntimeException if logging is critical and the application cannot continue
            // throw new RuntimeException("Failed to initialize logger", e);
        }
    }

    public void logError(Exception e) {
        if (writer != null) {
            writer.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ": " + e.toString());
            e.printStackTrace(writer); // Print the stack trace to the log
            writer.flush(); // Flush the writer to ensure the log is written immediately
        } else {
            // Handle the case where the writer is null (e.g., logging failed to initialize)
            System.err.println("Error: Logger not initialized.  Cannot log error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Optional: Add a close method to explicitly close the writer when the application terminates
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
