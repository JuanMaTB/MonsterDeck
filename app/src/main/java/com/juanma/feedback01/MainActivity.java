package com.juanma.feedback01;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia al ListView
        ListView listView = findViewById(R.id.listMonsters);

        // Datos de ejemplo (frikis, fáciles y válidos para el feedback)
        ArrayList<String> monsters = new ArrayList<>();
        monsters.add("Slime - Nivel 1");
        monsters.add("Goblin - Nivel 3");
        monsters.add("Dragon - Nivel 10");

        // Adaptador simple
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                monsters
        );

        // Asignar adaptador al ListView
        listView.setAdapter(adapter);
    }
}
