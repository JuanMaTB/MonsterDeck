package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> monsters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.listMonsters);

        // Datos de prueba
        monsters = new ArrayList<>();
        monsters.add("Slime - Nivel 1");
        monsters.add("Goblin - Nivel 3");
        monsters.add("Dragon - Nivel 10");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                monsters
        );

        list.setAdapter(adapter);

        // Click -> abre detalle
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                String line = monsters.get(position); // "Goblin - Nivel 3"
                String[] parts = line.split(" - Nivel ");

                String name = parts[0].trim();
                int level = 1;
                if (parts.length > 1) {
                    try { level = Integer.parseInt(parts[1].trim()); } catch (Exception ignored) {}
                }

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("level", level);
                startActivity(intent);
            }
        });
    }
}
