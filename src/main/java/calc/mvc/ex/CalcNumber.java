package calc.mvc.ex;

public class CalcNumber {
    private String numStr = "";
    private double numDb;
    private String error;
    private boolean errorStatus = false;

    public CalcNumber() {
    }

    public CalcNumber(String number) {
        numStr = number;
        numDb = convertToDouble(number);
    }

    public CalcNumber(Double number) {
        numDb = number;
        numStr = Double.toString(number);
    }

    private double convertToDouble(String s) {
        if (s.equals("-"))
            s = "0";

        return Double.parseDouble(s);
    }

    public void concat(char num) {
        if (numStr.isEmpty())
            numStr = toString() + num;
        else {
            String strTemp = toString();
            if (isNumber(strTemp) && !(toString(true).equals("0") && num == '0'))
                numStr = toString(true) + num;
        }
    }

    public void concat(char num, boolean trim) {
        if (trim) {
            if (num == '.') {
                if (numStr.equals(""))
                    numStr = "0.";

                if (!numStr.contains("."))
                    concat(num);

            } else {
                if (numStr.equals("0"))
                    numStr = "";

                concat(num);
            }
        } else {
            concat(num);
        }
    }

    public void eraseValue() {
        numStr = "0";
    }

    public void removeOne() {
        numStr = toString(true).substring(0, toString(true).length() - 1);
    }

    public boolean isNumber(String s) {
        try {
            double num = Double.parseDouble(s);
            return !(Double.isInfinite(num));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double getDouble() {
        numDb = convertToDouble(numStr);
        return numDb;
    }

    public void setNumStr(String num) {
        numStr = num;
    }

    public void setError(String message) {
        error = message;
    }

    public String getError() {
        return error;
    }

    public boolean isError() {
        return errorStatus;
    }

    public void setErrorStatus(boolean status) {
        errorStatus = status;
    }

    public CalcNumber add(CalcNumber calcNumber) {
        return new CalcNumber(getDouble() + calcNumber.getDouble());
    }

    public CalcNumber subtract(CalcNumber calcNumber) {
        return new CalcNumber(getDouble() - calcNumber.getDouble());
    }

    public CalcNumber multiply(CalcNumber calcNumber) {
        return new CalcNumber(getDouble() * calcNumber.getDouble());
    }

    public CalcNumber divide(CalcNumber calcNumber) {
        double fN = getDouble();
        double sN = calcNumber.getDouble();

        if (sN == 0) {
            CalcNumber err = new CalcNumber();
            err.setErrorStatus(true);
            err.setError("Cannot divide by 0");
            return err;
        }

        return new CalcNumber(fN / sN);
    }

    public CalcNumber powTwo() {
        return new CalcNumber(Math.pow(getDouble(), 2));
    }

    public CalcNumber modulo(CalcNumber calcNumber) {
        return new CalcNumber(getDouble() % calcNumber.getDouble());
    }

    public CalcNumber percent(CalcNumber calcNumber) {
        return new CalcNumber(getDouble() * (calcNumber.getDouble() / 100));
    }

    public CalcNumber sqrt() {
        if (getDouble() < 0) {
            CalcNumber err = new CalcNumber();
            err.setErrorStatus(true);
            err.setError("Invalid number");
            return err;
        } else {
            return new CalcNumber(Math.sqrt(getDouble()));
        }
    }

    public String toString() {
        return numStr;
    }
    public String toString(boolean isInt) {
        if (isInt) {
            numDb = getDouble();
            if ((numDb % 1 == 0) && (numStr.length() > 1) && (numStr.charAt(numStr.length() - 1) == '0') && (numStr.charAt(numStr.length() - 2) == '.')) {
                return numStr.substring(0, numStr.length() - 2);
            }
        }

        return toString();
    }
}