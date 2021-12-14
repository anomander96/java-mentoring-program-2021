package com.javamentoring.javamentoring;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private static final Stack<BigInteger> numbers = new Stack<>();
    private static final Stack<String> operations = new Stack<>();


    private static void calculate(String expression) {
        int index = 0;

        while (index < expression.length()) {
            if (expression.charAt(index) == '(') {

            } else if (expression.charAt(index) == '+' || expression.charAt(index) == '-' ||
                expression.charAt(index) == '*' || expression.charAt(index) == '/') {
                operations.push(String.valueOf(expression.charAt(index)));
            } else if (expression.charAt(index) == ')') {
                executeOperation();
            } else {
                StringBuilder value = new StringBuilder(String.valueOf(expression.charAt(index)));

                while ((index + 1) < expression.length() &&
                        Character.isDigit(expression.charAt(index + 1))) {
                    value.append(expression.charAt(++index));
                }
                numbers.push(BigInteger.valueOf(Long.parseLong(value.toString())));
            }
            index++;
        }

        while (!operations.empty() && !numbers.empty()) {
            executeOperation();
        }
    }

    public static void executeOperation() {
        String operation = operations.pop();
        BigInteger lastElement = numbers.pop();
        BigInteger previousElement = numbers.pop();

        switch(operation) {
            case "*": numbers.push(previousElement.multiply(lastElement));
                break;
            case "/":
                numbers.push(previousElement.divide(lastElement));
                break;
            case "+": numbers.push(previousElement.add(lastElement));
                break;
            case "-": numbers.push(previousElement.subtract(lastElement));
                break;
            default:
                System.out.println("Wrong expression");

        }
    }

    public static void main(String[] args) {
        System.out.println("Enter your expression : ");
        System.out.println("For example: 1+1*(2+2)");

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine().replaceAll("\\s", "");
                calculate(expression);
                System.out.println(expression + "=" + numbers.pop());
            }
        } catch (NoSuchElementException ex) {
            System.err.println("ERROR: Please enter your equation!");
        }
    }

}
