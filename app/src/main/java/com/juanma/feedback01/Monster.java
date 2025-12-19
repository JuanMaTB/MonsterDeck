package com.juanma.feedback01;

public class Monster {
    long id;
    String name;
    int level;
    boolean defeated;
    String type; // para decidir icono

    public Monster(long id, String name, int level, boolean defeated, String type) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.defeated = defeated;
        this.type = type;
    }
}
