package calc.mvc;

import calc.mvc.ex.CalcNumber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalcController implements Initializable {
    private CalcView view;
    private CalcModel model;

    private CalcNumber firstCalcNumber;
    private CalcNumber secondCalcNumber;

    private char currentMode = '0';
    private int operatingNum;
    private int wasEqualed = 0;

    @FXML
    private Label results;
    @FXML
    private Label numLabel;
    @FXML
    private Label modeLabel;
    @FXML
    private ButtonBar numbers1;
    @FXML
    private ButtonBar numbers2;
    @FXML
    private ButtonBar numbers3;

    private final ArrayList<ButtonBar> buttonBarList = new ArrayList<>(3);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view = new CalcView();
        model = new CalcModel();

        firstCalcNumber = new CalcNumber("0");
        secondCalcNumber = new CalcNumber("0");

        buttonBarList.add(numbers1);
        buttonBarList.add(numbers2);
        buttonBarList.add(numbers3);

        for (ButtonBar bar: buttonBarList) {
            for (int i = 0; i < bar.getButtons().size(); i++) {
                Button btn = (Button) (bar.getButtons().get(i));
                btn.setOnAction(event -> alterNumAndChangeLabel(btn.getText().charAt(0)));
            }
        }
    }

    @FXML
    public void keyPressAction() {
        Scene scene = numLabel.getScene();

        scene.setOnKeyPressed(keyEvent -> {
            KeyCode key = keyEvent.getCode();

            if (keyEvent.isShiftDown()) {
                switch (key) {
                    case EQUALS -> add();
                    case DIGIT5 -> percent();
                    case DIGIT8 -> multiply();
                }
            } else {
                switch (key) {
                    case DIGIT0, NUMPAD0 -> alterNumAndChangeLabel('0');
                    case DIGIT1, NUMPAD1 -> alterNumAndChangeLabel('1');
                    case DIGIT2, NUMPAD2 -> alterNumAndChangeLabel('2');
                    case DIGIT3, NUMPAD3 -> alterNumAndChangeLabel('3');
                    case DIGIT4, NUMPAD4 -> alterNumAndChangeLabel('4');
                    case DIGIT5, NUMPAD5 -> alterNumAndChangeLabel('5');
                    case DIGIT6, NUMPAD6 -> alterNumAndChangeLabel('6');
                    case DIGIT7, NUMPAD7 -> alterNumAndChangeLabel('7');
                    case DIGIT8, NUMPAD8 -> alterNumAndChangeLabel('8');
                    case DIGIT9, NUMPAD9 -> alterNumAndChangeLabel('9');
                    case PERIOD, COMMA -> getDot();
                    case M -> modulo();
                    case BACK_SPACE -> removeOne();
                    case DELETE -> clearAll();
                    case MINUS -> subtract();
                    case SLASH -> divide();
                }
            }
        });
    }

    public void getZero() {
        alterNumAndChangeLabel('0');
    }

    public void getDot() {
        alterNumAndChangeLabel('.');
    }

    public void alterNumAndChangeLabel(char concatChar) {
            if (operatingNum == 1) {
                if (secondCalcNumber.toString().length() < 28) {
                    model.alterNumber(secondCalcNumber, concatChar);
                    view.setLabelText(results, secondCalcNumber.toString());
                }
            } else {
                if (firstCalcNumber.toString().length() < 28) {
                    model.alterNumber(firstCalcNumber, concatChar);
                    view.setLabelText(results, firstCalcNumber.toString());
                }
            }

        view.matchSize(results);
    }

    public void add() {
        improvedEquals();
        changeMode('+');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    public void multiply() {
        improvedEquals();
        changeMode('*');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    public void modulo() {
        improvedEquals();
        changeMode('m');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    public void sqrt() {
        if (firstCalcNumber.toString().length() == 0)
            firstCalcNumber.setNumStr("0");

        model.sqrt(firstCalcNumber);

        CalcNumber resultObject = model.getResultObj();

        if (resultObject.isError())
            view.setLabelText(results, resultObject.getError());
        else
            view.setLabelText(results, resultObject.toString(true));

        view.matchSize(results);
        firstCalcNumber = resultObject;
        operatingNum = 0;
    }

    public void powTwo() {
        model.powTwo(firstCalcNumber);
        view.setLabelText(results, model.getResultObj().toString(true));
        view.matchSize(results);
        firstCalcNumber = model.getResultObj();
        operatingNum = 0;
    }

    public void percent() {
        improvedEquals();
        changeMode('%');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    public void divide() {
        improvedEquals();
        changeMode('/');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    public void subtract() {
        improvedEquals();
        changeMode('-');
        view.setLabelText(modeLabel, currentMode);
        view.setLabelText(numLabel, firstCalcNumber.toString(true));
        operatingNum = 1;
    }

    private void changeMode(char mode) {
        secondCalcNumber.setNumStr("");
        wasEqualed = 0;
        currentMode = mode;
    }

    public void clear() {
        if (firstCalcNumber.isError())
            clearAll();

        if (operatingNum == 1) {
            model.clearOne(secondCalcNumber);
            view.setLabelText(results, "0");
        } else {
            model.clearOne(firstCalcNumber);
            view.setLabelText(results, "0");
        }

        view.changeLabelFontSize(results, 40);
    }

    public void clearAll() {
        CalcNumber[] numbers = {firstCalcNumber, secondCalcNumber};
        model.clearAll(numbers);
        currentMode = '0';
        operatingNum = 0;
        view.changeLabelFontSize(results, 40);
        view.setLabelText(modeLabel, "");
        view.setLabelText(numLabel, "");
        view.setLabelText(results, "0");
    }

    public void removeOne() {
        if (firstCalcNumber.isError())
            clearAll();

        String text = view.getLabelText(results);

        if (operatingNum == 1 && secondCalcNumber.toString().length() > 0) {
            if (text.length() > 1) {
                view.setLabelText(results, model.removeOne(secondCalcNumber));
            } else {
                view.setLabelText(results, "0");
                secondCalcNumber.setNumStr("0");
            }
        } else if (operatingNum == 0) {
            if (text.length() > 1) {
                view.setLabelText(results, model.removeOne(firstCalcNumber));
            } else {
                view.setLabelText(results, "0");
                firstCalcNumber.setNumStr("0");
            }
        }

        view.matchSize(results);
    }

    public void improvedEquals() {
        if (wasEqualed == 0) {
            equals();
        }
    }

    public void equals() {
        if (currentMode != '0' && secondCalcNumber.toString().length() > 0) {
            wasEqualed = 1;

            if (firstCalcNumber.toString().equals(""))
                firstCalcNumber.setNumStr("0");

            switch (currentMode) {
                case '+' -> model.add(firstCalcNumber, secondCalcNumber);
                case '-' -> model.subtract(firstCalcNumber, secondCalcNumber);
                case '*' -> model.multiply(firstCalcNumber, secondCalcNumber);
                case '/' -> model.divide(firstCalcNumber, secondCalcNumber);
                case 'm' -> model.modulo(firstCalcNumber, secondCalcNumber);
                case '%' -> model.percent(firstCalcNumber, secondCalcNumber);
            }

            CalcNumber resultObject = model.getResultObj();

            if (resultObject.isError()) {
                view.setLabelText(results, resultObject.getError());
                resultObject.setNumStr("0");
            } else
                view.setLabelText(results, resultObject.toString(true));

            if (resultObject.toString().equals("0.0"))
                resultObject.setNumStr("0");

            firstCalcNumber = resultObject;

            view.matchSize(results);

            if (secondCalcNumber.toString(true).equals("0"))
                view.setLabelText(numLabel, "");
            else
                view.setLabelText(numLabel, secondCalcNumber.toString(true));

            view.setLabelText(modeLabel, currentMode);

            operatingNum = 0;
        }
    }
}