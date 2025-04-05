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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {

    private String[] itemList = new String[] {"Github", "Version", "License"};
    private String[] subList = new String[] {"GitHub repository link", "1.0.0", "GPLv3" };
    private ListView aboutListView;
    private int[] icons = {
            R.drawable.baseline_code_24,
            R.drawable.baseline_info_outline_24,
            R.drawable.baseline_copyright_24
    };
    private int versionClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialization of ListView
        aboutListView = findViewById(R.id.aboutListView);  // Find ListView by id
        CustomAdapter adapter = new CustomAdapter(this, itemList, subList, icons);
        aboutListView.setAdapter(adapter);

        // Get access to MaterialToolbar
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar); // Set up as ActionBar

        // Processing the click on a list element
        aboutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected element of the list
                String selectedItem = itemList[position];

                // Use switch-case to handle the touch
                switch (selectedItem) {
                    case "Github":

                        break;

                    case "Version":
                        versionClickCount++; // Increase counter
                        // If pressed more than 6 times, show Toast
                        if (versionClickCount > 6) {
                            Toast.makeText(AboutActivity.this, "You will succeed!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "License":

                        break;

                    default:
                        break;
                }
            }
        });
    }

    // Button back "Back"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            // Clear activity flag
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
