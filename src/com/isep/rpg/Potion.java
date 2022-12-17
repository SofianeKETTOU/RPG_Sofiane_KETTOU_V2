package com.isep.rpg;

public class Potion extends Consumable{
    int healvalue = 15;
    int degatvalue = 15;

    public Potion(String name) {
        super(name);
    }

    public void regenerate(Combatant hero) {
        if(hero.nbpotionheal>0) {
            hero.heal(healvalue);
            if (hero.getHealthPoint() > 100) {
                hero.setFullHealthPoint();
            }
            hero.nbpotionheal--;
        }else{
            System.out.println("Vous n'avez plus de potion de soin");
        }
    }

    public void degat(Combatant enemie,Combatant hero) {
        if(hero.nbpotiondegat>0) {
            enemie.loose(degatvalue);
            hero.nbpotiondegat--;
        }else{
            System.out.println("Vous n'avez plus de potion de dégats");
        }
    }
    public int getVie() {
        return vie;
    }

    private int vie;

}
