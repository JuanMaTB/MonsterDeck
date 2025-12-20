package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/*
este archivo representa la pantalla de detalle de un monstruo
aqui muestro toda la informacion de uno solo

se relaciona con:
- mainactivity: desde ahi entro aqui pasando el monsterId
- editmonsteractivity: desde aqui puedo editar el monstruo
- dbhelper: para leer y borrar el monstruo
- monster: objeto que contiene los datos

nota:
esta pantalla no modifica datos directamente
solo muestra y delega acciones como editar o borrar
*/
public class DetailActivity extends AppCompatActivity {

    // acceso a la bd
    private DBHelper db;

    // id del monstruo que estoy mostrando
    private long monsterId = -1;

    // vistas del layout
    private ImageView detailImg;
    private TextView detailName;
    private TextView detailLevel;
    private CheckBox chkDefeated;
    private Button btnEdit;

    /*
    launcher moderno para abrir la pantalla de edicion
    cuando vuelve, recargo el monstruo por si hubo cambios
    */
    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> loadMonster()
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // titulo simple para la barra superior
        setTitle("Detalle");

        // cargo el layout de detalle
        setContentView(R.layout.activity_detail);

        // activo la flecha de volver arriba
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // creo el helper de la bd
        db = new DBHelper(this);

        // enlazo las vistas
        detailImg = findViewById(R.id.detailImg);
        detailName = findViewById(R.id.detailName);
        detailLevel = findViewById(R.id.detailLevel);
        chkDefeated = findViewById(R.id.chkDefeated);
        btnEdit = findViewById(R.id.btnEdit);

        // recupero el id que me paso la pantalla anterior
        monsterId = getIntent().getLongExtra("monsterId", -1);

        // el checkbox aqui es solo informativo
        chkDefeated.setClickable(false);
        chkDefeated.setFocusable(false);

        // boton editar abre la pantalla de edicion
        btnEdit.setOnClickListener(v -> openEdit());

        // cargo los datos del monstruo
        loadMonster();
    }

    /*
    abre la pantalla de edicion pasando el monsterId
    */
    private void openEdit() {
        if (monsterId == -1) return;

        Intent i = new Intent(DetailActivity.this, EditMonsterActivity.class);
        i.putExtra("monsterId", monsterId);
        editLauncher.launch(i);
    }

    /*
    comportamiento de la flecha de volver
    simplemente cierro esta pantalla
    */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /*
    carga el monstruo desde la bd y lo muestra en pantalla
    este metodo se llama al crear la activity y al volver de editar
    */
    private void loadMonster() {
        if (monsterId == -1) return;

        Monster m = db.getMonster(monsterId);
        if (m == null) return;

        detailName.setText(m.name);
        detailLevel.setText("Nivel " + m.level);
        chkDefeated.setChecked(m.defeated);

        // selecciono la imagen segun el tipo
        // aqui uso mis dibujos chibi propios
        if ("slime".equals(m.type)) {
            detailImg.setImageResource(R.drawable.slime);
        } else if ("goblin".equals(m.type)) {
            detailImg.setImageResource(R.drawable.goblin);
        } else if ("dragon".equals(m.type)) {
            detailImg.setImageResource(R.drawable.dragon);
        } else {
            detailImg.setImageResource(R.drawable.bestiario_rpg);
        }
    }

    // ---------------- MENU ----------------

    /*
    inflo el menu comun de la app
    luego en onprepare decido que opciones se ven aqui
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    aqui adapto el menu a esta pantalla
    oculto lo que no tiene sentido y muestro lo que si
    */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // en detalle no tiene sentido anadir ni filtrar
        MenuItem add = menu.findItem(R.id.action_add);
        MenuItem filter = menu.findItem(R.id.action_filter_defeated);

        if (add != null) add.setVisible(false);
        if (filter != null) filter.setVisible(false);

        // en detalle si tiene sentido editar y eliminar
        MenuItem edit = menu.findItem(R.id.action_edit);
        MenuItem delete = menu.findItem(R.id.action_delete);

        if (edit != null) edit.setVisible(true);
        if (delete != null) delete.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    /*
    gestiono los clicks del menu
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // flecha de volver
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        // editar monstruo
        if (id == R.id.action_edit) {
            openEdit();
            return true;
        }

        // borrar monstruo con confirmacion
        if (id == R.id.action_delete) {
            confirmDelete();
            return true;
        }

        // dialogo acerca de
        if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about_title)
                    .setMessage(R.string.about_message)
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    muestra un dialogo para confirmar el borrado
    si acepta, borro de la bd y vuelvo a la lista
    */
    private void confirmDelete() {
        if (monsterId == -1) return;

        String nombre =
                (detailName.getText() != null)
                        ? detailName.getText().toString()
                        : "este monstruo";

        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Seguro que quieres eliminar a " + nombre + "?")
                .setPositiveButton("Sí, borrar", (dialog, which) -> {
                    db.deleteMonster(monsterId);
                    // al cerrar vuelvo a main y la lista se recarga en onresume
                    finish();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
