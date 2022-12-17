package com.isep.rpg;

public abstract class Combatant {

    int nbpotionheal = 3;
    int nbpotiondegat = 3;
    int nbfood = 5;

    public Combatant(String n, int h, int a) {
        name = n;
        healthPoint = h;
        armor = a;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
    public int getArmorPoints() {
        return armor;
    }
    public int setFullHealthPoint() {
        return healthPoint = 100;
    }
    public int setFullArmorPoint() {
        return armor = 15;
    }


    public void loose(int hp) {
        if(hp > armor && armor >0){
            armor = 0;
        }else if(armor <= 0){
            healthPoint -= hp;
        }else {
            armor -= hp;
        }
        // ... équivalant à : healthPoint = healthPoint - hp;
    }

    public void heal(int hp) {
        healthPoint += hp;
        // ... équivalant à : healthPoint = healthPoint - hp;
    }

    // Chaque "vrai" combatant (non "abstract") implémente un code différent
    // pour la méthode "fight"
    public abstract void fight(Combatant combatant);
    public abstract void take(Item item);
    public abstract void manger(Combatant combatant);


    private String name;
    private int healthPoint;
    private int armor;

}
