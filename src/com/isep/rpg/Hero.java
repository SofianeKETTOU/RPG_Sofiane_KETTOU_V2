package com.isep.rpg;

public abstract class Hero extends Combatant {

    public Hero(String n, int h, int a) {
        super(n, h, a);
    }

    // Abstrait car n'importe quel hero peut prendre un objet mais son
    // utilisation dépend du type du héro (une arme n'est pas utile à un mage)
    public abstract void take(Item item);
    public abstract void takefood(Item item);
}
