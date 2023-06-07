package calc.mvc;

import calc.mvc.ex.CalcNumber;

public class CalcModel {
    private CalcNumber result;

    public CalcNumber getResultObj() {
        return result;
    }

    public void add(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.add(secondNumber);
    }

    public void subtract(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.subtract(secondNumber);
    }

    public void multiply(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.multiply(secondNumber);
    }

    public void divide(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.divide(secondNumber);
    }

    public void powTwo(CalcNumber firstNumber) {
        result = firstNumber.powTwo();
    }

    public void sqrt(CalcNumber firstNumber) {
        result = firstNumber.sqrt();
    }

    public void percent(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.percent(secondNumber);
    }

    public void modulo(CalcNumber firstNumber, CalcNumber secondNumber) {
        result = firstNumber.modulo(secondNumber);
    }

    public void alterNumber(CalcNumber num, char concatChar) {
        num.concat(concatChar, true);
    }

    public void clearOne(CalcNumber calcNumber) {
        calcNumber.setErrorStatus(false);
        calcNumber.eraseValue();
    }

    public void clearAll(CalcNumber[] numbers) {
        for (CalcNumber number: numbers) {
            clearOne(number);
        }
    }

    public String removeOne(CalcNumber number) {
        if (number.isNumber(number.toString()))
            number.removeOne();
        else {
            number.setNumStr("0");
            return "0";
        }

        return number.toString();
    }
}