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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private String[] itemList;
    private String[] subList;
    private int[] icons;

    public CustomAdapter(Context context, String[] settingList, String[] subSettingList, int[] icons) {
        this.context = context;
        this.itemList = settingList;
        this.subList = subSettingList;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return itemList.length;  // The number of elements in the list
    }

    @Override
    public Object getItem(int position) {
        return itemList[position];  // Return the element at a specific position
    }

    @Override
    public long getItemId(int position) {
        return position;  // Item identifier (can simply return the position)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If the view is not created yet, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.setting_adapter_list, parent, false);
        }

        // Find the elements in the layout
        ImageView itemIcon = convertView.findViewById(R.id.itemIcon);
        TextView mainItem = convertView.findViewById(R.id.main_item);
        TextView subItem = convertView.findViewById(R.id.sub_item);

        // Set the icon, main text and subtext
        itemIcon.setImageResource(icons[position]);
        mainItem.setText(itemList[position]);
        subItem.setText(subList[position]);
        return convertView;
    }
}

