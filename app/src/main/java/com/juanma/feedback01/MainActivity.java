package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Mini “modelo” (simple y claro)
    static class Monster {
        String name;
        int level;
        int iconResId;

        Monster(String name, int level, int iconResId) {
            this.name = name;
            this.level = level;
            this.iconResId = iconResId;
        }
    }

    private final ArrayList<Monster> monsters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Datos (por ahora hardcodeados)
        monsters.add(new Monster("Slime", 1, R.mipmap.ic_launcher));
        monsters.add(new Monster("Goblin", 3, R.mipmap.ic_launcher));
        monsters.add(new Monster("Dragon", 10, R.mipmap.ic_launcher));

        // 2) ListView + Adapter
        ListView listView = findViewById(R.id.listMonsters);

        ArrayAdapter<Monster> adapter = new ArrayAdapter<Monster>(this, 0, monsters) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_monster, parent, false);
                }

                Monster m = getItem(position);

                ImageView img = convertView.findViewById(R.id.imgMonster);
                TextView txtName = convertView.findViewById(R.id.txtMonsterName);
                TextView txtLevel = convertView.findViewById(R.id.txtMonsterLevel);

                if (m != null) {
                    img.setImageResource(m.iconResId);
                    txtName.setText(m.name);
                    txtLevel.setText("Nivel " + m.level);
                }

                return convertView;
            }
        };

        listView.setAdapter(adapter);

        // 3) CLICK -> abrir DetailActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Monster selected = monsters.get(position);

            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("name", selected.name);
            i.putExtra("level", selected.level);
            i.putExtra("iconResId", selected.iconResId);
            startActivity(i);
        });
    }
}
