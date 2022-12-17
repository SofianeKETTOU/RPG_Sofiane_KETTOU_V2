package com.isep.rpg;

public class Dragon extends Ennemy {

    public Dragon(String n) {
        // Le dragon possède 5 points de vie et inflige 3 points de dégats
        super(n, 300,0 ,20);
    }

    // Implémentation de la méthode abstraite "fight" par le dragon
    @Override
    public void fight(Combatant combatant) {
        combatant.loose( getDamagePoints());
    }

    @Override
    public void manger(Combatant combatant) {

    }

    @Override
    public void potionvie(Combatant combatant) {

    }

    @Override
    public void potionforce(Combatant combatant) {

    }

    public void take(Item item){};

}
