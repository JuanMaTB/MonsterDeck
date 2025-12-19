package com.juanma.feedback01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonsterAdapter extends ArrayAdapter<Monster> {

    public MonsterAdapter(Context context, List<Monster> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Monster m = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_monster, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.rowImg);
        TextView name = convertView.findViewById(R.id.rowName);
        TextView level = convertView.findViewById(R.id.rowLevel);
        CheckBox defeated = convertView.findViewById(R.id.rowDefeated);

        img.setImageResource(iconForType(m.type));
        name.setText(m.name);
        level.setText("Nivel " + m.level);

        // Ojo: en la lista NO queremos que el checkbox “robe” el click del row
        defeated.setClickable(false);
        defeated.setFocusable(false);
        defeated.setChecked(m.defeated);

        return convertView;
    }

    private int iconForType(String type) {
        if (type == null) return android.R.drawable.ic_menu_help;

        switch (type) {
            case "slime":
                return android.R.drawable.presence_online;
            case "goblin":
                return android.R.drawable.ic_menu_myplaces;
            case "dragon":
                return android.R.drawable.ic_menu_compass;
            default:
                return android.R.drawable.ic_menu_help;
        }
    }
}
