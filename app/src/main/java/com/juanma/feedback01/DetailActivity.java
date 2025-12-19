package com.juanma.feedback01;

import android.os.Bundle;
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

        // Datos de prueba (luego vendrán por Intent)
        name.setText("Goblin");
        level.setText("Nivel 3");
    }
}
