package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/*
este archivo es la pantalla principal de la app
aqui se muestra la lista completa de monstruos en un listview

se relaciona con:
- dbhelper: para leer y borrar monstruos de la bd
- monsteradapter: para pintar cada fila del listview
- detailactivity: al pulsar un monstruo se abre su detalle
- editmonsteractivity: desde el menu se puede crear uno nuevo

nota:
esta pantalla no edita directamente
solo navega y refresca la informacion
*/
public class MainActivity extends AppCompatActivity {

    // acceso a la bd
    private DBHelper db;

    // listview donde muestro los monstruos
    private ListView listMonsters;

    // adaptador que convierte los datos en filas visuales
    private MonsterAdapter adapter;

    // flag para saber si estoy filtrando solo derrotados
    private boolean onlyDefeated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // titulo de la pantalla principal
        setTitle("Bestiario RPG");

        // cargo el layout con el listview
        setContentView(R.layout.activity_main);

        // inicializo el helper de la bd
        db = new DBHelper(this);

        // enlazo el listview
        listMonsters = findViewById(R.id.listMonsters);

        /*
        click normal sobre un item
        abre la pantalla de detalle pasando el monsterId
        */
        listMonsters.setOnItemClickListener((parent, view, position, id) -> {
            Monster m = adapter.getItem(position);

            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("monsterId", m.id);
            startActivity(i);
        });

        /*
        click largo sobre un item
        muestra un dialogo para confirmar el borrado
        */
        listMonsters.setOnItemLongClickListener((parent, view, position, id) -> {
            Monster m = adapter.getItem(position);

            new AlertDialog.Builder(this)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar a " + m.name + "?")
                    .setPositiveButton("Sí, borrar", (dialog, which) -> {
                        db.deleteMonster(m.id);
                        // recargo la lista para reflejar el cambio
                        reload();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            // devuelvo true para indicar que el evento esta consumido
            return true;
        });

        // primera carga de datos
        reload();
    }

    /*
    este metodo se ejecuta cada vez que vuelvo a esta pantalla
    por ejemplo despues de editar o borrar un monstruo
    */
    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    /*
    lee los datos de la bd y los pasa al adapter
    aqui aplico el filtro si onlyDefeated esta activo
    */
    private void reload() {
        ArrayList<Monster> data = db.getAll(onlyDefeated);
        adapter = new MonsterAdapter(this, data);
        listMonsters.setAdapter(adapter);
    }

    // ---------------- MENU ----------------

    /*
    cargo el menu comun de la app
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    actualizo el texto del filtro segun el estado actual
    */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem filter = menu.findItem(R.id.action_filter_defeated);

        if (filter != null) {
            filter.setTitle(onlyDefeated ? "Ver todos" : "Ver derrotados");
        }

        return super.onPrepareOptionsMenu(menu);
    }

    /*
    gestiono las acciones del menu
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // opcion anadir monstruo
        if (id == R.id.action_add) {
            Intent i = new Intent(this, EditMonsterActivity.class);
            startActivity(i);
            return true;
        }

        // opcion filtrar derrotados
        if (id == R.id.action_filter_defeated) {
            onlyDefeated = !onlyDefeated;
            reload();
            invalidateOptionsMenu();
            return true;
        }

        // dialogo acerca de
        if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle("Acerca de")
                    .setMessage(
                            "Bestiario RPG (Feedback01)\n\n" +
                                    "- ListView + Adapter\n" +
                                    "- SQLite (CRUD)\n" +
                                    "- Activities + Intent\n" +
                                    "- Menu + Dialogos\n\n" +
                                    "Juanma TB"
                    )
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
