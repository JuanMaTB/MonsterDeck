package com.juanma.feedback01;

/*
este archivo representa el modelo monster
es un objeto simple que agrupa todos los datos de un monstruo

se relaciona con:
- dbhelper: crea objetos monster al leer de la bd
- mainactivity: usa monster para mostrar la lista
- monsteradapter: usa monster para pintar cada fila
- detailactivity: muestra los datos de un monster concreto
- editmonsteractivity: modifica los datos de un monster

nota:
esta clase no tiene logica
solo sirve para transportar datos de forma ordenada
*/
public class Monster {

    // id unico del monstruo en la bd
    long id;

    // nombre del monstruo
    String name;

    // nivel del monstruo
    int level;

    // indica si esta derrotado o no
    boolean defeated;

    // tipo del monstruo
    // se usa para decidir que imagen mostrar
    String type;

    /*
    constructor basico
    aqui creo el objeto monster con todos sus datos
    */
    public Monster(long id, String name, int level, boolean defeated, String type) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.defeated = defeated;
        this.type = type;
    }
}
