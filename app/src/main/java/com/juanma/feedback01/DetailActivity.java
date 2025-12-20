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

public class DetailActivity extends AppCompatActivity {

    private DBHelper db;

    private long monsterId = -1;

    private ImageView detailImg;
    private TextView detailName;
    private TextView detailLevel;
    private CheckBox chkDefeated;
    private Button btnEdit;

    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> loadMonster());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle");
        setContentView(R.layout.activity_detail);

        // Flecha de volver en la barra
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        db = new DBHelper(this);

        detailImg = findViewById(R.id.detailImg);
        detailName = findViewById(R.id.detailName);
        detailLevel = findViewById(R.id.detailLevel);
        chkDefeated = findViewById(R.id.chkDefeated);
        btnEdit = findViewById(R.id.btnEdit);

        monsterId = getIntent().getLongExtra("monsterId", -1);

        chkDefeated.setClickable(false);
        chkDefeated.setFocusable(false);

        btnEdit.setOnClickListener(v -> openEdit());

        loadMonster();
    }

    private void openEdit() {
        if (monsterId == -1) return;
        Intent i = new Intent(DetailActivity.this, EditMonsterActivity.class);
        i.putExtra("monsterId", monsterId);
        editLauncher.launch(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // vuelve a la pantalla anterior
        return true;
    }

    private void loadMonster() {
        if (monsterId == -1) return;

        Monster m = db.getMonster(monsterId);
        if (m == null) return;

        detailName.setText(m.name);
        detailLevel.setText("Nivel " + m.level);
        chkDefeated.setChecked(m.defeated);

        if ("slime".equals(m.type)) {
            detailImg.setImageResource(android.R.drawable.presence_online);
        } else if ("goblin".equals(m.type)) {
            detailImg.setImageResource(android.R.drawable.presence_away);
        } else if ("dragon".equals(m.type)) {
            detailImg.setImageResource(android.R.drawable.presence_busy);
        } else {
            detailImg.setImageResource(android.R.drawable.sym_def_app_icon);
        }
    }

    // -------- MENÚ --------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // En detalle: no tiene sentido add ni filtro
        MenuItem add = menu.findItem(R.id.action_add);
        MenuItem filter = menu.findItem(R.id.action_filter_defeated);

        if (add != null) add.setVisible(false);
        if (filter != null) filter.setVisible(false);

        // En detalle: sí tiene sentido editar y eliminar
        MenuItem edit = menu.findItem(R.id.action_edit);
        MenuItem delete = menu.findItem(R.id.action_delete);

        if (edit != null) edit.setVisible(true);
        if (delete != null) delete.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_edit) {
            openEdit();
            return true;
        }

        if (id == R.id.action_delete) {
            confirmDelete();
            return true;
        }

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

    private void confirmDelete() {
        if (monsterId == -1) return;

        String nombre = (detailName.getText() != null) ? detailName.getText().toString() : "este monstruo";

        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Seguro que quieres eliminar a " + nombre + "?")
                .setPositiveButton("Sí, borrar", (dialog, which) -> {
                    db.deleteMonster(monsterId);
                    finish(); // vuelve a Main (y Main recarga en onResume)
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
