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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.PopupMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.activity.EdgeToEdge;



public class MainActivity extends AppCompatActivity {
    private EditText numberField;
    private EditText equalField;
    private double number1;
    private double number2;
    private String currentOperation = "";
    private boolean isNewOperation = true;
    private List<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        numberField = findViewById(R.id.numberField);
        equalField = findViewById(R.id.equalsField);

        MaterialToolbar mmt = findViewById(R.id.mainMaterialToolbar);
        mmt.setNavigationOnClickListener(view -> showPopupMenu(view));


        numberField.setText("");
        equalField.setText("");

    }

    // Method for processing numeral pushing
    public void clickNumber(View view) {
        String currentText = numberField.getText().toString();

        if (isNewOperation) {
            numberField.setText("");  // Clear EditText for the new operation
            isNewOperation = false;  // Clear the flag
        }

        int[] buttonsIds = {R.id.but1, R.id.but2, R.id.but3, R.id.but4, R.id.but5,
                R.id.but6, R.id.but7, R.id.but8, R.id.but9, R.id.but0};

        for (int i = 0; i < buttonsIds.length; i++) {
            if (view.getId() == buttonsIds[i]) {
                // If the “0” button is pressed and the number already starts with “0”, don't add another zero
                if (i == 9 && currentText.equals("0")) {
                    return;  // Don't add zero
                }

                // Add a new number to the current string
                numberField.setText(currentText + (i == 9 ? "0" : i + 1));
                break;
            }
        }
    }

    // Method for processing operations
    public void clickOperation(View view) {
        String operation = "";

        // Define the operation
        if (view.getId() == R.id.butAdd) {
            operation = "+";
        } else if (view.getId() == R.id.butSubtract) {
            operation = "-";
        } else if (view.getId() == R.id.butMultiply) {
            operation = "*";
        } else if (view.getId() == R.id.butDivide) {
            operation = "/";
        } else if (view.getId() == R.id.butPercent) {
            operation = "%";
        }

        // If there is already a number entered, add the operation to the string
        String currentText = numberField.getText().toString();

        // Checking if the field is not empty and if there is no operation at the end
        if (!currentText.isEmpty() && !currentText.endsWith(operation)) {
            // Add the operation to the current number
            numberField.setText(currentText + operation);
            currentOperation = operation;  // Save the operation
        } else if (currentText.isEmpty() && currentOperation.isEmpty()) {
            // If the field is empty and no operation is selected, only an operation can be added
            numberField.setText("0" + operation);
            currentOperation = operation;
        }
    }


    // Method for calculating the result
    public void clickEquals(View view) {
        if (!numberField.getText().toString().isEmpty()) {
            // Get the current expression from EditText
            String input = numberField.getText().toString();

            // Modified regular expression to correctly handle negative numbers
            Pattern pattern = Pattern.compile("(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/%])\\s*(-?\\d+(?:\\.\\d+)?)");
            Matcher matcher = pattern.matcher(input);

            // Check if the expression contains three parts: number, operation, number
            if (matcher.matches()) {
                try {
                    // Parse the numbers and operation from the regular expression
                    double number1 = Double.parseDouble(matcher.group(1).trim());  // First number
                    double number2 = Double.parseDouble(matcher.group(3).trim());  // Second number
                    String operation = matcher.group(2).trim();  // Operation (+, -, *, /)

                    // Create an Operation object to perform the operation
                    Operation operationObj = new Operation();

                    // Call the makeOperation method to perform the operation
                    double result = operationObj.makeOperation(number1, number2, operation);

                    // Check if the result is an integer
                    if (result == (int) result) {
                        // If the result is an integer, display it as an integer
                        equalField.setText(String.valueOf((int) result));
                    } else {
                        // If the result is not an integer, display it as a floating-point number
                        equalField.setText(String.valueOf(result));
                    }

                } catch (NumberFormatException e) {
                    // If parsing the number failed, show an error via Toast
                    Toast.makeText(this, "Error: Invalid number format", Toast.LENGTH_SHORT).show();
                } catch (ArithmeticException e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (UnsupportedOperationException e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // If the input expression is invalid
                Toast.makeText(this, "Error: Invalid input", Toast.LENGTH_SHORT).show();
            }

            // Add the expression to the history
            historyList.add(input + " = " + equalField.getText().toString());
            isNewOperation = true;
        }
    }

    // Method for clearing the field
    public void clickClear(View view) {
        // Check if it is a long press
        view.setOnLongClickListener(v -> {
            // If it's a long press, clear all fields
            numberField.setText("");
            equalField.setText("");
            number1 = 0;
            number2 = 0;
            currentOperation = "";
            isNewOperation = true;
            return true; // Return true to handle long press
        });

        // If it's a short press, delete the last character
        String currentText = numberField.getText().toString();
        if (currentText.length() > 1) {
            numberField.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            numberField.setText("");
            equalField.setText("");
        }
    }

    // Method for handling the dot button press
    public void clickDot(View view) {
        String currentInput = numberField.getText().toString();

        // Check if there is text in the input field
        if (!currentInput.isEmpty()) {
            // If no operation has been selected yet (it's the first number), allow adding a dot
            if (currentOperation.isEmpty()) {
                // Check if there is already a dot in the number
                if (!currentInput.contains(".")) {
                    numberField.setText(currentInput + ".");
                }
            } else {
                // If an operation has been selected, handle the second number
                if (!currentInput.contains(" ")) {
                    // If there is no space (no second number), simply add a dot after the operation
                    numberField.setText(currentInput + ".");
                } else {
                    // Split the expression into parts (first number, operation, and second number)
                    String[] parts = currentInput.split("(?=[-+*/%])|(?<=[-+*/%])");  // Split by operations

                    // Check if there is a second number
                    if (parts.length == 3) {
                        String secondNumber = parts[2].trim();
                        if (!secondNumber.contains(".")) {
                            // If the second number doesn't contain a dot, add it
                            parts[2] = secondNumber + ".";
                            numberField.setText(parts[0] + parts[1] + parts[2]);
                        }
                    }
                }
            }
        } else {
            numberField.setText("0.");
        }
    }
    public void clickPlusMinus(View view) {
        String currentInput = numberField.getText().toString();

        // If the text is empty (no number), just add a minus
        if (currentInput.isEmpty()) {
            numberField.setText("-");  // If the field is empty, just enter a minus
        } else {
            // If the text is not empty, check if there is already a minus at the beginning
            if (currentInput.startsWith("-")) {
                // If there is a minus at the beginning, just remove it
                numberField.setText(currentInput.substring(1));
            } else {
                // If there is no minus, add a minus at the beginning
                numberField.setText("-" + currentInput);
            }
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

        // Create a map to store menu item IDs and their corresponding actions
        Map<Integer, Runnable> menuActions = new HashMap<>();
        menuActions.put(R.id.setting, () -> openNewActivity(SettingActivity.class));
        menuActions.put(R.id.history, () -> openNewActivity(HistoryActivity.class));
        menuActions.put(R.id.about, () -> openNewActivity(AboutActivity.class));

        // Replace lambda with anonymous class
        popupMenu.setOnMenuItemClickListener(item -> {
            // Check if there is an action for this menu item
            Runnable action = menuActions.get(item.getItemId());
            if (action != null) {
                action.run();  // Perform the corresponding action
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    // Method for opening a new activity
    private void openNewActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
        // If additional data needs to be passed to the activity
        intent.putStringArrayListExtra("history", new ArrayList<>(historyList));
        startActivity(intent);
    }
}



