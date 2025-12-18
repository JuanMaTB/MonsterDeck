package com.juanma.feedback01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView txtName = findViewById(R.id.txtMonsterName);
        TextView txtLevel = findViewById(R.id.txtMonsterLevel);
        Button btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int level = intent.getIntExtra("level", 1);

        txtName.setText("Nombre: " + name);
        txtLevel.setText("Nivel: " + level);

        btnBack.setOnClickListener(v -> finish());
    }
}
