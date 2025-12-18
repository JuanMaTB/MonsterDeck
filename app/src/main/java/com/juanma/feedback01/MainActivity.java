package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Mini “modelo” para no complicarnos aún
    static class Monster {
        String name;
        int level;

        Monster(String name, int level) {
            this.name = name;
            this.level = level;
        }

        @Override
        public String toString() {
            return name + " - Nivel " + level;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listMonsters);

        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster("Slime", 1));
        monsters.add(new Monster("Goblin", 3));
        monsters.add(new Monster("Dragon", 10));

        ArrayAdapter<Monster> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                monsters
        );
        listView.setAdapter(adapter);

        // Click en un monstruo -> abrir detalle
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Monster selected = monsters.get(position);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("name", selected.name);
            intent.putExtra("level", selected.level);
            startActivity(intent);
        });
    }
}
