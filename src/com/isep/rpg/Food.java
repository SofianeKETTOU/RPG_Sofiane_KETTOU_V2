package com.isep.rpg;

public class Food extends Consumable{

    public Food(String name, int vie) {
        super(name);
        this.vie = vie;
    }

    public int getVie() {
        return vie;
    }

    // Une arme inflige des points de dégats
    private int vie;
}
