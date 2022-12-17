package com.isep.rpg;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Goblin extends Ennemy {


    public Goblin(String n) {
        // Le dragon possède 5 points de vie et inflige 3 points de dégats
        super(n, 20,0, 5);
    }

    // Implémentation de la méthode abstraite "fight" par le dragon
    @Override
    public void fight(Combatant combatant) {
        combatant.loose( getDamagePoints() );
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
