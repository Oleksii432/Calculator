/*
 * Copyright (C) 2025 Oleksii Chepishko
 * Calculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Calculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with calculator.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.alexprogram.calculator;

public class Operation {
    public double makeOperation(double number1, double number2, String operationType) {
        switch (operationType) {
            case "+":
                return sum(number1, number2);
            case "-":
                return subtract(number1, number2);
            case "*":
                return multiply(number1, number2);
            case "/":
                return divide(number1, number2);
            case "%":
                return percent(number1, number2);
            default:
                throw new UnsupportedOperationException("This operation isn't supported");
        }
    }

    private double percent(double number1, double number2) {
        return (number1 * number2) / 100;
    }

    private double sum(double number1, double number2) {

        return number1 + number2;
    }

    private double subtract(double number1, double number2) {

        return number1 - number2;
    }

    private double multiply(double number1, double number2) {

        return number1 * number2;
    }

    private double divide(double number1, double number2) {
        if (number2 == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return number1 / number2;
    }
}
