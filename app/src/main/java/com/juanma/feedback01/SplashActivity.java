package com.juanma.feedback01;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Versión (debajo, porque el título ya va integrado en la imagen)
        TextView txtVersion = findViewById(R.id.txtVersion);
        txtVersion.setText(getAppVersionText());

        // Toca para continuar
        findViewById(R.id.splashRoot).setOnClickListener(v -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private String getAppVersionText() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "v" + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "v1.0";
        }
    }
}
