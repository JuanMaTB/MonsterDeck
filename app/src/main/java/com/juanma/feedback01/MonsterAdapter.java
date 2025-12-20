package com.juanma.feedback01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
este archivo es el adapter del listview
su funcion es transformar una lista de objetos monster en filas visuales

se relaciona con:
- mainactivity: le pasa la lista de monsters
- monster: es el objeto que contiene los datos
- row_monster.xml: layout de cada fila del listview

nota:
toda la logica visual de cada fila esta aqui
no en la activity
*/
public class MonsterAdapter extends ArrayAdapter<Monster> {

    /*
    constructor del adapter
    recibe el contexto y la lista de monstruos
    */
    public MonsterAdapter(Context context, List<Monster> objects) {
        // paso 0 porque yo inflare manualmente el layout
        super(context, 0, objects);
    }

    /*
    este metodo se ejecuta por cada fila del listview
    android lo llama tantas veces como elementos haya
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // obtengo el monstruo que corresponde a esta posicion
        Monster m = getItem(position);

        /*
        reutilizo la vista si existe para mejorar rendimiento
        si no existe la creo inflando el layout
        */
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.row_monster, parent, false);
        }

        // enlazo las vistas de la fila
        ImageView img = convertView.findViewById(R.id.rowImg);
        TextView name = convertView.findViewById(R.id.rowName);
        TextView level = convertView.findViewById(R.id.rowLevel);
        CheckBox defeated = convertView.findViewById(R.id.rowDefeated);

        /*
        asigno la imagen chibi segun el tipo del monstruo
        */
        img.setImageResource(iconForType(m.type));

        // muestro nombre y nivel
        name.setText(m.name);
        level.setText("Nivel " + m.level);

        /*
        el checkbox en la lista es solo informativo
        no quiero que robe el click del row
        */
        defeated.setClickable(false);
        defeated.setFocusable(false);
        defeated.setChecked(m.defeated);

        return convertView;
    }

    /*
    devuelve el recurso de imagen segun el tipo
    asi centralizo la decision en un solo sitio
    */
    private int iconForType(String type) {

        if (type == null) {
            return R.drawable.bestiario_rpg;
        }

        switch (type) {
            case "slime":
                return R.drawable.slime;
            case "goblin":
                return R.drawable.goblin;
            case "dragon":
                return R.drawable.dragon;
            default:
                return R.drawable.bestiario_rpg;
        }
    }
}
