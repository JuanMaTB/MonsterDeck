package com.juanma.feedback01;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
este archivo representa la pantalla de inicio de la app (splash)
es la primera pantalla que se ve al abrir la aplicacion

se relaciona con:
- mainactivity: despues de tocar la pantalla se navega a la lista principal
- activity_splash.xml: layout con la imagen grande y el texto de version

nota:
esta pantalla no usa bd ni logica de negocio
solo sirve para dar una entrada visual mas cuidada a la app
*/
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cargo el layout del splash
        setContentView(R.layout.activity_splash);

        /*
        muestro la version de la app debajo
        el titulo ya va integrado en la imagen principal
        */
        TextView txtVersion = findViewById(R.id.txtVersion);
        txtVersion.setText(getAppVersionText());

        /*
        toda la pantalla es clickable
        al tocar se entra en la pantalla principal
        */
        findViewById(R.id.splashRoot).setOnClickListener(v -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

            // cierro el splash para que no vuelva al pulsar atras
            finish();
        });
    }

    /*
    obtiene la version de la app desde el package
    asi no tengo la version hardcodeada
    */
    private String getAppVersionText() {
        try {
            PackageInfo pInfo =
                    getPackageManager().getPackageInfo(getPackageName(), 0);

            return "v" + pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // fallback por si algo falla
            return "v1.0";
        }
    }
}
