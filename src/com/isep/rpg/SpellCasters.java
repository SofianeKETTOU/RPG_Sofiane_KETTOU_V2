package com.isep.rpg;

public abstract class SpellCasters extends Combatant {
    int manapoint = 100;
    public SpellCasters(String n, int h, int a) {
        super(n, h, a);
    }

    // Implémentation de la méthode abstraite "fight" par le guerrier
    public abstract void fight(Combatant combatant);

    public abstract void takefood(Item item);

    // Abstrait car n'importe quel hero peut prendre un objet mais son
    // utilisation dépend du type du héro (une arme n'est pas utile à un mage)
}
