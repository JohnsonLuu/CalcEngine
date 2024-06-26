package com.pluralsight.calcengine;

import java.io.OutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        double[] leftVals = {100.0d, 25.0d, 225.0d, 11.0d};
        double[] rightVals = {50.0d, 92.0d, 17.0d, 3.0d};
        char[] opCodes = {'d', 'a', 's', 'm'};
        double[] results = new double[opCodes.length];

        if (args.length == 0) {
            for (int i = 0; i < opCodes.length; i++){
                results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);
            }

            for(double currentResult : results)
                System.out.println(currentResult);
        }
        else if (args.length == 1 && args[0].equals("interactive")){
            executeInteractively();
        }
        else if (args.length == 3){
            handleCommandLine(args);
        }
        else{
            System.out.println("Please provide an operation code and 2 numeric values");
        }
    }

    static void executeInteractively(){
        System.out.println("Enter an operation and two numbers");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] parts = userInput.split(" ");
        performOperation(parts);
    }
    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);
        double leftVal = valueFromWord(parts[1]);
        double rightVal = valueFromWord(parts[2]);
        double result = execute(opCode, leftVal, rightVal);
        displayResult(opCode, leftVal, rightVal, result);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);
        StringBuilder builder = new StringBuilder(20);
        // we have integers, char and string literals, which StringBuilder can handle
        builder.append(leftVal);
        builder.append(" ");
        builder.append(symbol);
        builder.append(" ");
        builder.append(rightVal);
        builder.append(" = ");
        builder.append(result);
        String output = builder.toString();
        System.out.println(output);

    }

    private static char symbolFromOpCode(char opCode){
        // loop through opCodes array to find associated symbol
        char[] opCodes = {'a', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';

        for(int index = 0; index < opCodes.length; index++){
            if(opCode == opCodes[index]){
                symbol = symbols[index];
                // no need to waste iterations after finding the symbol
                break;
            }
        }
        return symbol;
    }
    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        double result = execute(opCode, leftVal, rightVal);
        System.out.println(result);
    }

    static double execute(char opCode, double leftVal, double rightVal){
        double result;
        result = switch (opCode) {
            case 'a' -> leftVal + rightVal;
            case 's' -> leftVal - rightVal;
            case 'm' -> leftVal * rightVal;
            case 'd' -> rightVal != 0 ? leftVal / rightVal : 0.0d;
            default -> {
                System.out.println("Invalid opCode: " + opCode);
                yield 0.0d;
            }
        };

        return result;
    }
    static char opCodeFromString(String operationName){
        char opCode = operationName.charAt(0);
        return opCode;
    }

    static double valueFromWord(String word){
        String [] numberWords = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"
        };
        double value = 0d;
        for(int index = 0; index < numberWords.length; index++){
            if(word.equals(numberWords[index])){
                value = index;
                break;
            }
        }
        return value;
    }
}
