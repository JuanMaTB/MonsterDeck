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
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_monster, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.rowImg);
        TextView name = convertView.findViewById(R.id.rowName);
        TextView level = convertView.findViewById(R.id.rowLevel);
        CheckBox defeated = convertView.findViewById(R.id.rowDefeated);

        // Imagen chibi según tipo
        img.setImageResource(iconForType(m.type));

        name.setText(m.name);
        level.setText("Nivel " + m.level);

        // En la lista el checkbox es solo informativo
        defeated.setClickable(false);
        defeated.setFocusable(false);
        defeated.setChecked(m.defeated);

        return convertView;
    }

    private int iconForType(String type) {
        if (type == null) {
            return R.drawable.bestiario_rpg;
        }

        switch (type) {
            case "slime":
                return R.drawable.slime;
            case "goblin":
                return R.drawable.goblin;
            case "dragon":
                return R.drawable.dragon;
            default:
                return R.drawable.bestiario_rpg;
        }
    }
}
