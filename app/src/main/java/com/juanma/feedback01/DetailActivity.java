package com.juanma.feedback01;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Botón “volver” arriba (flecha)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalle");
        }

        ImageView img = findViewById(R.id.detailImg);
        TextView name = findViewById(R.id.detailName);
        TextView level = findViewById(R.id.detailLevel);
        CheckBox defeated = findViewById(R.id.chkDefeated);

        String monsterName = getIntent().getStringExtra("name");
        int monsterLevel = getIntent().getIntExtra("level", 1);
        int iconResId = getIntent().getIntExtra("iconResId", R.mipmap.ic_launcher);

        img.setImageResource(iconResId);
        name.setText(monsterName != null ? monsterName : "Monster");
        level.setText("Nivel " + monsterLevel);

        // Por ahora no guarda en BD; luego lo persistimos.
        defeated.setChecked(false);
    }

    // Flecha de “up” en la action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
