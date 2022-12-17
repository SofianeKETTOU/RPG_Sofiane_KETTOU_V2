package com.isep.rpg;

public class Healer extends SpellCasters {
    int healpoint = 2;
    int loosemana = 15;
    public Healer(String n) {
        // Le guerrier possède 5 points de vie
        super(n, 75, 15);
    }

    // Implémentation de la méthode abstraite "fight" par le guerrier
    @Override
    public void fight(Combatant combatant) {
        if(this.manapoint>0) {
            combatant.heal(healpoint);
            this.manapoint = this.manapoint - loosemana;
            System.out.println("il reste "+this.manapoint+" points de mana à "+this.getName());
        }else{
            System.out.println("Pas assez de manapoints");
        }
    }

    @Override
    public void take(Item item) {

    }

    @Override
    public void manger(Combatant combatant) {
        if(combatant.nbfood>0) {
            combatant.heal(food.getVie());
            combatant.nbfood--;
            System.out.println(combatant.getName()+" n'a plus que "+nbfood+" de nourriture");
        }else{
            System.out.println(combatant.getName()+" n'a plus de nourriture");
        }
    }

    public void takefood(Item item) {
        if (item instanceof Food) {
            food = (Food) item;
        } else {
            Game.displayMessage("Oups ! " + item.getName() + " est inutile...");
        }
    }

    private Food food;
}
