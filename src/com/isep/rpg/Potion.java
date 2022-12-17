package com.isep.rpg;

public class Potion extends Consumable{
    int healvalue = 15;
    int degatvalue = 15;
    int winmanapoint = 15;

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

    public void mana(SpellCasters hero) {
        if(hero.nbpotionmana>0) {
            hero.manapoint = hero.manapoint + winmanapoint;
            hero.nbpotionmana--;
        }else{
            System.out.println(hero.getName()+"n'a plus de potion de mana");
        }
    }
    public int getVie() {
        return vie;
    }

    private int vie;

}
