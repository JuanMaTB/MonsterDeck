package com.juanma.feedback01;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.detailImg);
        TextView name = findViewById(R.id.detailName);
        TextView level = findViewById(R.id.detailLevel);
        CheckBox chkDefeated = findViewById(R.id.chkDefeated);

        // Recibir datos
        String monsterName = getIntent().getStringExtra("name");
        int monsterLevel = getIntent().getIntExtra("level", 1);

        if (monsterName == null) monsterName = "Desconocido";

        name.setText(monsterName);
        level.setText("Nivel " + monsterLevel);

        // Imagen placeholder (para no depender de drawables custom aún)
        img.setImageResource(R.mipmap.ic_launcher);

        // Checkbox por defecto
        chkDefeated.setChecked(false);
    }
}
