package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                // Cuando volvemos de Edit, recargamos el monstruo
                loadMonster();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle");
        setContentView(R.layout.activity_detail);

        db = new DBHelper(this);

        detailImg = findViewById(R.id.detailImg);
        detailName = findViewById(R.id.detailName);
        detailLevel = findViewById(R.id.detailLevel);
        chkDefeated = findViewById(R.id.chkDefeated);
        btnEdit = findViewById(R.id.btnEdit);

        monsterId = getIntent().getLongExtra("monsterId", -1);

        // Checkbox solo informativo aquí (la edición se hace en la pantalla Edit)
        chkDefeated.setClickable(false);
        chkDefeated.setFocusable(false);

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this, EditMonsterActivity.class);
            i.putExtra("monsterId", monsterId);
            editLauncher.launch(i);
        });

        loadMonster();
    }

    private void loadMonster() {
        if (monsterId == -1) return;

        Monster m = db.getMonster(monsterId);
        if (m == null) return;

        detailName.setText(m.name);
        detailLevel.setText("Nivel " + m.level);
        chkDefeated.setChecked(m.defeated);

        // Icono simple por tipo (opcional)
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
}
