package com.juanma.feedback01;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView listMonsters;
    private MonsterAdapter adapter;

    private boolean onlyDefeated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bestiario RPG");
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        listMonsters = findViewById(R.id.listMonsters);

        // Click: abrir detalle
        listMonsters.setOnItemClickListener((parent, view, position, id) -> {
            Monster m = adapter.getItem(position);
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("monsterId", m.id);
            startActivity(i);
        });

        // Long click: borrar con confirmación
        listMonsters.setOnItemLongClickListener((parent, view, position, id) -> {
            Monster m = adapter.getItem(position);
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar a " + m.name + "?")
                    .setPositiveButton("Sí, borrar", (dialog, which) -> {
                        db.deleteMonster(m.id);
                        reload();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        reload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {
        ArrayList<Monster> data = db.getAll(onlyDefeated);
        adapter = new MonsterAdapter(this, data);
        listMonsters.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem filter = menu.findItem(R.id.action_filter_defeated);
        filter.setTitle(onlyDefeated ? "Ver todos" : "Ver derrotados");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent i = new Intent(this, EditMonsterActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_filter_defeated) {
            onlyDefeated = !onlyDefeated;
            reload();
            invalidateOptionsMenu();
            return true;
        }

        if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle("Acerca de")
                    .setMessage("Bestiario RPG (Feedback01)\n\n" +
                            "- ListView + Adapter\n" +
                            "- SQLite (CRUD)\n" +
                            "- Activities + Intent\n" +
                            "- Menú + Diálogos\n\n" +
                            "Juanma TB")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
