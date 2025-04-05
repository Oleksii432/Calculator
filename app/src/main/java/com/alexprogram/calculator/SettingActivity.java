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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.appbar.MaterialToolbar;



public class SettingActivity extends AppCompatActivity {

    private String[] itemList = new String[] {"Appearance"};
    private String[] subList = new String[] {"Change theme settings"};
    private int[] icons = {
            R.drawable.baseline_mode_night_24 // Icon for "Appearance"
    };
    private ListView settingListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Initialization ListView
        settingListView = findViewById(R.id.settingListView);  // Find ListView by id

        // Creating instance for the custom adapter
        CustomAdapter adapter = new CustomAdapter(this, itemList, subList, icons);

        // Set up an adapter
        settingListView.setAdapter(adapter);

        // Recieve access to the MaterialToolbar
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar); // Встановлюємо як ActionBar

        // Processing of touching on a list element
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Якщо вибрано пункт "Appearance"
                if (itemList[position].equals("Appearance")) {
                    showThemeDialog();
                }
            }
        });
    }

    // Function to show the theme selection dialog box
    private void showThemeDialog() {
        // Array for theme variants
        String[] themes = {"Light", "Dark", "Auto"};

        // Creating the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Theme")
                .setItems(themes, (dialog, which) -> {
                    // Apply the selected theme
                    switch (which) {
                        case 0:
                            // Light Theme
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            break;
                        case 1:
                            // Dark Theme
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            break;
                        case 2:
                            // Auto (System Default)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            break;
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

