package com.juanma.feedback01;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/*
este archivo representa la pantalla de alta y edicion de monstruos
la misma pantalla sirve para crear uno nuevo o modificar uno existente

se relaciona con:
- mainactivity: desde ahi se abre para crear un monstruo
- detailactivity: desde ahi se abre para editar un monstruo existente
- dbhelper: aqui se guardan los cambios en la bd
- monster: objeto que representa los datos

nota:
uso una sola pantalla para no duplicar codigo y hacerlo mas sencillo
*/
public class EditMonsterActivity extends AppCompatActivity {

    // acceso a la bd
    private DBHelper db;

    // id del monstruo, si es -1 significa que estoy creando uno nuevo
    private long monsterId = -1;

    // vistas del formulario
    private EditText edtName;
    private EditText edtLevel;
    private Spinner spType;
    private CheckBox chkDefeated;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // titulo generico porque sirve para anadir y editar
        setTitle(getString(R.string.add_edit_title));

        // cargo el layout del formulario
        setContentView(R.layout.activity_edit);

        // activo la flecha de volver arriba
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // creo el helper de la bd
        db = new DBHelper(this);

        // enlazo las vistas del layout
        edtName = findViewById(R.id.edtName);
        edtLevel = findViewById(R.id.edtLevel);
        spType = findViewById(R.id.spType);
        chkDefeated = findViewById(R.id.chkDefeatedEdit);
        btnSave = findViewById(R.id.btnSave);

        /*
        preparo el spinner de tipos usando un array de strings
        asi no tengo los tipos hardcodeados en el codigo
        */
        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.monster_types,
                        android.R.layout.simple_spinner_item
                );
        typeAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spType.setAdapter(typeAdapter);

        // recupero el id si vengo desde la pantalla de detalle
        monsterId = getIntent().getLongExtra("monsterId", -1);

        /*
        si monsterId es distinto de -1 estoy editando
        cargo los datos del monstruo en el formulario
        */
        if (monsterId != -1) {
            Monster m = db.getMonster(monsterId);
            if (m != null) {
                edtName.setText(m.name);
                edtLevel.setText(String.valueOf(m.level));
                chkDefeated.setChecked(m.defeated);

                // selecciono el tipo correcto en el spinner
                int pos = 0;
                if ("slime".equals(m.type)) pos = 0;
                else if ("goblin".equals(m.type)) pos = 1;
                else if ("dragon".equals(m.type)) pos = 2;

                spType.setSelection(pos);
            }
        }

        // boton guardar valida y guarda en la bd
        btnSave.setOnClickListener(v -> saveAndExit());
    }

    /*
    comportamiento de la flecha de volver
    no guardo nada automaticamente
    */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ---------------- MENU ----------------

    /*
    reutilizo el menu comun de la app
    luego oculto lo que no tiene sentido aqui
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    en esta pantalla solo dejo la opcion acerca de
    */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem add = menu.findItem(R.id.action_add);
        MenuItem edit = menu.findItem(R.id.action_edit);
        MenuItem delete = menu.findItem(R.id.action_delete);
        MenuItem filter = menu.findItem(R.id.action_filter_defeated);

        if (add != null) add.setVisible(false);
        if (edit != null) edit.setVisible(false);
        if (delete != null) delete.setVisible(false);
        if (filter != null) filter.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    /*
    gestiono las opciones del menu
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // flecha de volver
        if (id == android.R.id.home) {
            finish();
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
    valida los datos y guarda en la bd
    segun el monsterId decide si inserta o actualiza
    */
    private void saveAndExit() {

        String name = edtName.getText().toString().trim();
        String levelStr = edtLevel.getText().toString().trim();
        String type = spType.getSelectedItem().toString();
        boolean defeated = chkDefeated.isChecked();

        // validacion basica para no guardar basura
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(levelStr)) {
            Toast.makeText(
                    this,
                    getString(R.string.fill_name_level),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        int level;
        try {
            level = Integer.parseInt(levelStr);
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    getString(R.string.invalid_level),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // normalizo el tipo para guardarlo en la bd
        String typeKey = type.toLowerCase();

        if (monsterId == -1) {
            // inserto nuevo monstruo
            db.addMonster(name, level, defeated, typeKey);
            Toast.makeText(
                    this,
                    getString(R.string.monster_added),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // actualizo monstruo existente
            db.updateMonster(monsterId, name, level, defeated, typeKey);
            Toast.makeText(
                    this,
                    getString(R.string.monster_updated),
                    Toast.LENGTH_SHORT
            ).show();
        }

        // cierro la pantalla y vuelvo atras
        finish();
    }
}
