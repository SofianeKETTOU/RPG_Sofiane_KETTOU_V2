package com.isep.rpg;

public class Warrior extends Hero {
    public Warrior(String n) {
        // Le guerrier possède 5 points de vie
        super(n, 75, 15);
    }

    // Implémentation de la méthode abstraite "fight" par le guerrier
    @Override
    public void fight(Combatant combatant) {
        combatant.loose( weapon.getDamagePoints() );
    }


    public void manger(Combatant combatant) {
        if(combatant.nbfood>0) {
            combatant.heal(food.getVie());
            combatant.nbfood--;
            System.out.println(combatant.getName()+" n'a plus que "+nbfood+" de nourriture");
        }else{
            System.out.println(combatant.getName()+" n'a plus de nourriture");
        }
    }

    // Implémentation de la méthode abstraite "take" par le guerrier :
    //   Le guerrier ne peut utiliser que les objets de type "Weapon"
    @Override
    public void take(Item item) {
        if (item instanceof Weapon) {
            weapon = (Weapon) item;
        } else {
            Game.displayMessage("Oups ! " + item.getName() + " est inutile...");
        }
    }

    public void takefood(Item item) {
        if (item instanceof Food) {
            food = (Food) item;
        } else {
            Game.displayMessage("Oups ! " + item.getName() + " est inutile...");
        }
    }
    private Weapon weapon;
    private Food food;
}
