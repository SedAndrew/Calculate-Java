package com.andrew;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                String result = calc(input);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static String calc(String input) throws Exception {
        Calculator calculator = new Calculator(input);
        return calculator.getAnswer();
    }
}