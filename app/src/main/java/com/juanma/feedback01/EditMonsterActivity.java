package com.juanma.feedback01;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditMonsterActivity extends AppCompatActivity {

    private DBHelper db;
    private long monsterId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Añadir / Editar");
        setContentView(R.layout.activity_edit);

        db = new DBHelper(this);

        EditText edtName = findViewById(R.id.edtName);
        EditText edtLevel = findViewById(R.id.edtLevel);
        Spinner spType = findViewById(R.id.spType);
        CheckBox chkDefeated = findViewById(R.id.chkDefeatedEdit);
        Button btnSave = findViewById(R.id.btnSave);

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

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String levelStr = edtLevel.getText().toString().trim();
            String type = spType.getSelectedItem().toString();
            boolean defeated = chkDefeated.isChecked();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(levelStr)) {
                Toast.makeText(this, "Rellena nombre y nivel", Toast.LENGTH_SHORT).show();
                return;
            }

            int level;
            try {
                level = Integer.parseInt(levelStr);
            } catch (Exception e) {
                Toast.makeText(this, "Nivel inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertimos el texto del spinner a “clave”
            String typeKey = type.toLowerCase(); // slime/goblin/dragon

            if (monsterId == -1) {
                db.addMonster(name, level, defeated, typeKey);
                Toast.makeText(this, "Monstruo añadido", Toast.LENGTH_SHORT).show();
            } else {
                db.updateMonster(monsterId, name, level, defeated, typeKey);
                Toast.makeText(this, "Monstruo actualizado", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
