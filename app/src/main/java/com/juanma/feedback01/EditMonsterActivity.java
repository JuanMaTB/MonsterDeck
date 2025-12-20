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

public class EditMonsterActivity extends AppCompatActivity {

    private DBHelper db;
    private long monsterId = -1;

    private EditText edtName;
    private EditText edtLevel;
    private Spinner spType;
    private CheckBox chkDefeated;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.add_edit_title));
        setContentView(R.layout.activity_edit);

        // Flecha de volver en la barra
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        db = new DBHelper(this);

        edtName = findViewById(R.id.edtName);
        edtLevel = findViewById(R.id.edtLevel);
        spType = findViewById(R.id.spType);
        chkDefeated = findViewById(R.id.chkDefeatedEdit);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.monster_types, android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);

        monsterId = getIntent().getLongExtra("monsterId", -1);

        if (monsterId != -1) {
            Monster m = db.getMonster(monsterId);
            if (m != null) {
                edtName.setText(m.name);
                edtLevel.setText(String.valueOf(m.level));
                chkDefeated.setChecked(m.defeated);

                int pos = 0;
                if ("slime".equals(m.type)) pos = 0;
                else if ("goblin".equals(m.type)) pos = 1;
                else if ("dragon".equals(m.type)) pos = 2;
                spType.setSelection(pos);
            }
        }

        btnSave.setOnClickListener(v -> saveAndExit());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // vuelve a la pantalla anterior
        return true;
    }

    // -------- MENÚ --------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // En editar: solo dejamos "Acerca de"
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
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

    private void saveAndExit() {
        String name = edtName.getText().toString().trim();
        String levelStr = edtLevel.getText().toString().trim();
        String type = spType.getSelectedItem().toString();
        boolean defeated = chkDefeated.isChecked();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(levelStr)) {
            Toast.makeText(this, getString(R.string.fill_name_level), Toast.LENGTH_SHORT).show();
            return;
        }

        int level;
        try {
            level = Integer.parseInt(levelStr);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_level), Toast.LENGTH_SHORT).show();
            return;
        }

        String typeKey = type.toLowerCase();

        if (monsterId == -1) {
            db.addMonster(name, level, defeated, typeKey);
            Toast.makeText(this, getString(R.string.monster_added), Toast.LENGTH_SHORT).show();
        } else {
            db.updateMonster(monsterId, name, level, defeated, typeKey);
            Toast.makeText(this, getString(R.string.monster_updated), Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
