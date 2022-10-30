package com.andrew;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Operations of addition, subtraction, multiplication and division with two numbers.
 * Can work with Arabic (1,2,3,4,5...) and Roman (I, II, III, IV, V...) numbers.
 */
public class Calculator {
    Operator operator;
    String firstValue;
    String secondValue;
    public Calculator(String input) throws Exception {
        Pattern pattern = Pattern.compile(".*\s\\W\s.*");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new ExpressionException("A string is not a mathematical operation.");
        }

        String[] parsInput = input.split("\s");
        if (parsInput.length > 3) {
            throw new ExpressionException("The format of the mathematical operation does not satisfy the task - " +
                    "two operands and one operator (+, -, /, *)");
        }

        firstValue = parsInput[0];
        secondValue = parsInput[2];

        if (parsInput[1].equals("+")) {
            operator = Operator.ADDITION;
        } else if (parsInput[1].equals("-")) {
            operator = Operator.SUBTRACTION;
        } else if (parsInput[1].equals("*")) {
            operator = Operator.MULTIPLICATION;
        } else if (parsInput[1].equals("/")) {
            operator = Operator.DIVISION;
        } else {
            throw new OperatorException("Unprocessed operator.");
        }
    }
    private boolean hasArabic(String value) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    private boolean hasRoman(String value) throws ExpressionException {
        try {
            RomanNumber.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ExpressionException("The calculator must accept numbers from 1 to 10 inclusive, no more.");
        }
        return true;
    }
    private int arabicExpression(String a, String b, Operator op) throws CalculatorException, ExpressionException {
        if (Integer.parseInt(a) > 10 | Integer.parseInt(b) > 10) {
            throw new ExpressionException("Numbers no more than 10 inclusive are allowed.");
        }
        return calculation(Integer.parseInt(a), Integer.parseInt(b), op);
    }
    private String romanExpression(String a, String b, Operator op) throws CalculatorException, ExpressionException {
        int valueA = RomanNumber.valueOf(a).getValue();
        int valueB = RomanNumber.valueOf(b).getValue();
        if (valueA > 10 | valueB > 10) {
            throw new ExpressionException("The calculator must accept numbers from 1 to 10 inclusive, no more.");
        }
        int arabicResult = arabicExpression("" + valueA, "" + valueB, op);
        if (arabicResult < 1) {
            throw new CalculatorException("There are no negative numbers in the Roman numeral system.");
        }
        return convRoman(arabicResult);
    }
    private String convRoman(int value) {
        int dec = value / 10;
        int unit = value % 10 - 1;
        StringBuilder res = new StringBuilder();
        if (dec > 0) {
            res.append(String.valueOf(RomanNumber.X.getKey()).repeat(dec));
        }
        if (unit >= 0) {
            res.append(RomanNumber.values()[unit].getKey());
        }
        return res.toString();
    }
    public int calculation(int a, int b, Operator op) throws CalculatorException {
        int result = 0;
        if (op == Operator.ADDITION) {
            result = a + b;
        } else if (op == Operator.SUBTRACTION) {
            result = a - b;
        } else if (op == Operator.MULTIPLICATION) {
            result = a * b;
        } else if (op == Operator.DIVISION) {
            if (b == 0) {
                throw new CalculatorException("Attempt to divide by zero.");
            }
            result = a / b;
        }
        return result;
    }
    public String getAnswer() throws CalculatorException, ExpressionException {
        String answer = "";
        if (hasRoman(firstValue) && hasRoman(secondValue)) {
            answer = romanExpression(firstValue, secondValue, operator);
        } else if (hasArabic(firstValue) && hasArabic(secondValue)) {
            answer = "" + arabicExpression(firstValue, secondValue, operator);
        } else {
            throw new CalculatorException("Different number systems are used simultaneously");
        }
        return answer;
    }
}
