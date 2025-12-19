package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private DBHelper db;
    private long monsterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle");
        setContentView(R.layout.activity_detail);

        db = new DBHelper(this);

        monsterId = getIntent().getLongExtra("monsterId", -1);

        ImageView detailImg = findViewById(R.id.detailImg);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailLevel = findViewById(R.id.detailLevel);
        CheckBox chkDefeated = findViewById(R.id.chkDefeated);
        Button btnEdit = findViewById(R.id.btnEdit);

        Monster m = db.getMonster(monsterId);
        if (m == null) {
            finish();
            return;
        }

        detailImg.setImageResource(iconForType(m.type));
        detailName.setText(m.name);
        detailLevel.setText("Nivel " + m.level);
        chkDefeated.setChecked(m.defeated);

        chkDefeated.setOnCheckedChangeListener((buttonView, isChecked) -> db.setDefeated(monsterId, isChecked));

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this, EditMonsterActivity.class);
            i.putExtra("monsterId", monsterId);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Monster m = db.getMonster(monsterId);
        if (m == null) {
            finish();
        } else {
            ImageView detailImg = findViewById(R.id.detailImg);
            TextView detailName = findViewById(R.id.detailName);
            TextView detailLevel = findViewById(R.id.detailLevel);
            CheckBox chkDefeated = findViewById(R.id.chkDefeated);

            detailImg.setImageResource(iconForType(m.type));
            detailName.setText(m.name);
            detailLevel.setText("Nivel " + m.level);
            chkDefeated.setChecked(m.defeated);
        }
    }

    private int iconForType(String type) {
        if (type == null) return android.R.drawable.ic_menu_help;
        switch (type) {
            case "slime":
                return android.R.drawable.presence_online;
            case "goblin":
                return android.R.drawable.ic_menu_myplaces;
            case "dragon":
                return android.R.drawable.ic_menu_compass;
            default:
                return android.R.drawable.ic_menu_help;
        }
    }
}
