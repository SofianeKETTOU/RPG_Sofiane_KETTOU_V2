package com.isep.rpg;

import com.isep.utils.InputParser;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Game {
    int degatcouteau = 1;
    int degatarc = 4;
    public Game(InputParser inputParser) {

        this.inputParser = inputParser;


        // Il faut normalement 5 héros de types différents...
        heros = new ArrayList<>();

        // Il faut normalement 5 ennemis de types différents...
        enemies = new ArrayList<>();
    }


    public void start() throws InterruptedException {
       String filePath = "src/sound/free-orchestral-game-music-2.wav";
        try {
            // Création d'un flux audio à partir du fichier
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePath));
            // Création d'un clip à partir du flux audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            // Lecture en boucle
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ANSI_RED_BG = "\u001B[0;41m";
        String ANSI_GREEN = "\u001B[0;92m";
        String ANSI_RESET = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);

        //Random random= new Random();

       Potion potion_heal = new Potion("Potion de régénération");
       Potion potion_degat = new Potion("Potion de dégat");
       Potion potion_mana = new Potion("Potion de mana");

        System.out.println("Combien de Héros voulez vous: ");
        int nbheros = scanner.nextInt();
        while(nbheros<1 || nbheros>4 ){
            displayMessage("Veuillez choisir une valeur comprise entre 1 et 4 :");
            nbheros = scanner.nextInt();
        }
        System.out.println("Veuillez choisir vos héros: \n1-Warrior / 2-Hunter / 3-Healer / 4-Mage");
        for (int i = 1; i <= nbheros; i++) {
            System.out.println("Veuillez choisir votre "+i+"e hero:");
            int choixheros = scanner.nextInt();
            while(choixheros>4 || choixheros<1) {
                displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 4. \n Veuillez choisir une autre valeur:");
                choixheros = scanner.nextInt();
            }
            if (choixheros == 1) {
                Hero ronal = new Warrior("Ronal");
                ronal.take(new Weapon("knife", degatcouteau));
                ronal.takefood(new Food("Apple", 3));
                heros.add(ronal);
            } else if (choixheros == 2) {
                Hero Connie = new Hunter("Connie");
                Connie.take(new Weapon("Bow", degatarc));
                Connie.takefood(new Food("Apple", 3));
                heros.add(Connie);
            } else if (choixheros == 3) {
                SpellCasters jean = new Healer("Jean");
                jean.takefood(new Food("Apple", 3));
                heros.add(jean);
            } else if (choixheros == 4) {
                SpellCasters conan = new Mage("Conan");
                conan.takefood(new Food("Apple", 3));
                heros.add(conan);
            }

        }
        // Boucle de jeu
        for (int i = 1; i <= 5; i++) {
            Random random = new Random();
            int whofight = random.nextInt(2)+1;
                int ixHero = 0;
                int ixEnemy = 1;
                if (i == 5) {
                    enemies.add(new Dragon("Dracofeu"));
                } else {
                    switch (heros.size()) {
                        case 1:
                            enemies.add(new Goblin("Gaston"));
                            break;
                        case 2:
                            enemies.add(new Goblin("Gaston"));
                            enemies.add(new Goblin("Smaug"));
                            break;
                        case 3:
                            enemies.add(new Goblin("Gaston"));
                            enemies.add(new Goblin("Smaug"));
                            enemies.add(new Goblin("Garache"));
                            break;
                        case 4:
                            enemies.add(new Goblin("Gaston"));
                            enemies.add(new Goblin("Smaug"));
                            enemies.add(new Goblin("Garache"));
                            enemies.add(new Goblin("Garuda"));
                            break;
                        case 5:
                            enemies.add(new Goblin("Gaston"));
                            enemies.add(new Goblin("Smaug"));
                            enemies.add(new Goblin("Garache"));
                            enemies.add(new Goblin("Garuda"));
                            enemies.add(new Goblin("Yeti"));
                            break;
                    }
                }
            if (whofight == 1) {
                displayMessage("Les monstres commence !");
                while (true) {
                    displayStatus(heros, enemies);

                    Combatant goodOne = heros.get(ixHero);
                    Combatant badOne = enemies.get(ixEnemy - 1);

                    // Attaque de l'ennemi
                    displayMessage("Le méchant " + badOne.getName() + " attaque le gentil " + goodOne.getName() + "...");
                    Thread.sleep(400);
                    String filePathHit = "src/sound/Hit.wav";
                    try {
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathHit));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInput);
                        clip.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    badOne.fight(goodOne);
                    if (goodOne.getHealthPoint() <= 0) {
                        displayMessage(ANSI_RED_BG+"Le pauvre " + goodOne.getName() + " a été vaincu..."+ANSI_RESET);
                        heros.remove(ixHero);
                        ixHero--; // Correction: évite que le suivant perde son tour
                    } else {
                        if (goodOne instanceof Healer) {
                            System.out.println("Que voulez vous faire: \n1-Soigner / 2- Manger \uD83C\uDF54 / 3-Potion \uD83E\uDDEA / 4-Ce protéger ✋");
                        } else {
                            System.out.println("Que voulez vous faire: \n1-Attaquer ⚔ / 2- Manger \uD83C\uDF54 / 3-Potion \uD83E\uDDEA / 4-Ce protéger ✋");
                        }
                        int choixattaque = scanner.nextInt();
                        while (choixattaque > 4 || choixattaque < 1) {
                            displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 3. \n Veuillez choisir une autre valeur:");
                            choixattaque = scanner.nextInt();
                        }
                        if (choixattaque == 1) {
                            if (goodOne instanceof Healer) {
                                displayMessage("Qui voulez vous soigner ?");
                                for(int l = 0; l<heros.size();l++){
                                    displayMessage((l+1)+"-"+heros.get(l).getName());
                                }
                                int healerchoice = scanner.nextInt();
                                while (healerchoice < 1 || healerchoice > heros.size()) {
                                    displayMessage("Veuillez choisir une valeur comprise entre 1 et " + heros.size());
                                    healerchoice = scanner.nextInt();
                                }
                                goodOne.fight(heros.get(healerchoice - 1));
                                String filePathHealing = "src/sound/healing.wav";
                                try {
                                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathHealing));
                                    Clip clip = AudioSystem.getClip();
                                    clip.open(audioInput);
                                    clip.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                displayMessage("Qui voulez vous attaquer ?");
                                for(int l = 0; l<enemies.size();l++){
                                    displayMessage((l+1)+"-"+enemies.get(l).getName());
                                }
                                ixEnemy = scanner.nextInt();
                                while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                                    displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                                    ixEnemy = scanner.nextInt();
                                }
                                String filePathPotion = "src/sound/fight.wav";
                                try {
                                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                    Clip clip = AudioSystem.getClip();
                                    clip.open(audioInput);
                                    clip.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                badOne = enemies.get((ixEnemy - 1));
                                displayMessage("Le gentil " + goodOne.getName() + " attaque le méchant " + badOne.getName() + "...");
                                goodOne.fight(badOne);
                                if (badOne.getHealthPoint() <= 0) {
                                    displayMessage(ANSI_GREEN+"Bravo, " + goodOne.getName() + " a vaincu " + badOne.getName() + " !!!"+ANSI_RESET);
                                    enemies.remove((ixEnemy - 1));
                                    ixEnemy = 1;
                                }
                            }
                        } else if (choixattaque == 2) {
                            goodOne.manger(goodOne);
                            if(goodOne.nbfood>0){
                            String filePathPotion = "src/sound/Eating.wav";
                            try {
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInput);
                                clip.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                                System.out.println(goodOne.getName() + " à manger, voici c'est point de vie: " + goodOne.getHealthPoint());
                            }else{
                                displayMessage("Vous n'avez plus de nourriture votre tour a été sauté");
                            }
                        } else if (choixattaque == 3) {
                            if(goodOne instanceof SpellCasters){
                                displayMessage("Veuillez choisir votre potion: \n 1-Potion de régénération("+goodOne.nbpotionheal+") / 2-Potion de dégat("+goodOne.nbpotiondegat+") / 3-Potion de mana("+((SpellCasters) goodOne).nbpotionmana+")");
                                int choixpotion = scanner.nextInt();
                                while (choixpotion > 3 || choixpotion < 1) {
                                    displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 3. \n Veuillez choisir une autre valeur:");
                                    choixpotion = scanner.nextInt();
                                }
                                if (choixpotion == 1) {
                                    String filePathPotion = "src/sound/PotionDrinking.wav";
                                    try {
                                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioInput);
                                        clip.start();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Thread.sleep(750);
                                    potion_heal.regenerate(goodOne);
                                    displayMessage("Il reste " + goodOne.nbpotionheal + " potion de heal à " + goodOne.getName());
                                } else if (choixpotion == 2) {
                                    displayMessage("Sur qui voulez vous lancer la potion:");
                                    for(int l = 0; l<enemies.size();l++){
                                        displayMessage((l+1)+"-"+enemies.get(l).getName());
                                    }
                                    ixEnemy = scanner.nextInt();
                                    while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                                        displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                                        ixEnemy = scanner.nextInt();
                                    }
                                    badOne = enemies.get((ixEnemy-1));
                                    potion_degat.degat(badOne, goodOne);
                                    displayMessage("Il reste " + goodOne.nbpotiondegat + " potion de dégat à " + goodOne.getName());
                                    if (badOne.getHealthPoint() <= 0) {
                                        enemies.remove(badOne);
                                        displayMessage(ANSI_GREEN+"Bravo, " + goodOne.getName() + " a vaincu " + badOne.getName() + " !!!"+ANSI_RESET);
                                        // ixEnemy=1;
                                    }
                                }else if(choixpotion == 3){
                                    potion_mana.mana((SpellCasters) goodOne);
                                }
                            }else {
                                displayMessage("Veuillez choisir votre potion: \n 1-Potion de régénération(" + goodOne.nbpotionheal + ") / 2-Potion de dégat(" + goodOne.nbpotiondegat + ")");
                                int choixpotion = scanner.nextInt();
                                while (choixpotion > 2 || choixpotion < 1) {
                                    displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 2. \n Veuillez choisir une autre valeur:");
                                    choixpotion = scanner.nextInt();
                                }
                                if (choixpotion == 1) {
                                    String filePathPotion = "src/sound/PotionDrinking.wav";
                                    try {
                                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioInput);
                                        clip.start();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Thread.sleep(750);
                                    potion_heal.regenerate(goodOne);
                                    displayMessage("Il reste " + goodOne.nbpotionheal + " potion de heal à " + goodOne.getName());
                                } else if (choixpotion == 2) {
                                    displayMessage("Sur qui voulez vous lancer la potion:");
                                    for (int l = 0; l < enemies.size(); l++) {
                                        displayMessage((l + 1) + "-" + enemies.get(l).getName());
                                    }
                                    ixEnemy = scanner.nextInt();
                                    while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                                        displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                                        ixEnemy = scanner.nextInt();
                                    }
                                    badOne = enemies.get((ixEnemy - 1));
                                    potion_degat.degat(badOne, goodOne);
                                    displayMessage("Il reste " + goodOne.nbpotiondegat + " potion de dégat à " + goodOne.getName());
                                    if (badOne.getHealthPoint() <= 0) {
                                        enemies.remove(badOne);
                                        displayMessage(ANSI_GREEN + "Bravo, " + goodOne.getName() + " a vaincu " + badOne.getName() + " !!!" + ANSI_RESET);
                                        // ixEnemy=1;
                                    }
                                }
                            }
                        } else if (choixattaque == 4) {
                            String filePathprotec = "src/sound/protection.wav";
                            try {
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathprotec));
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInput);
                                clip.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Thread.sleep(1000);
                            if (goodOne.getArmorPoints() > 0) {
                                // goodOne.armor = goodOne.armor + 3;
                            } else {
                                goodOne.heal(3);
                            }
                        }
                        // Riposte du gentil, s'il n'est pas vaincu
                    }

                    // Tests de fin du jeu
                    if (heros.size() == 0) {
                        displayMessage(ANSI_RED_BG+"Les héros ont perdu, c'est la fin du monde..."+ANSI_RESET);
                        String filePathlose = "src/sound/lose.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathlose));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(2000);
                        break;
                    }
                    if (enemies.size() == 0) {
                        displayMessage(ANSI_GREEN+"BRAVO, les héros ont gagné, le monde est sauvé !!!"+ANSI_RESET);
                        String filePathwin = "src/sound/victory.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathwin));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(2000);
                        reward(potion_heal,potion_degat,potion_mana);
                        break;
                    }

                    // Au tour du héro suivant
                    ixHero = (ixHero + 1) % heros.size();
                }
                if (heros.size() == 0) {
                    break;
                }
                if (enemies.size() == 0) {
                    displayMessage("Round " + (i + 1));
                }
            } else if (whofight == 2) {
                displayMessage("Les héros commence !");
                while (true) {
                displayStatus(heros, enemies);

                Combatant goodOne = heros.get(ixHero);
                Combatant badOne = enemies.get(ixEnemy - 1);

                if (goodOne instanceof Healer) {
                    System.out.println("Que voulez vous faire: \n1-Soigner / 2- Manger \uD83C\uDF54 / 3-Potion \uD83E\uDDEA / 4-Ce protéger ✋");
                } else {
                    System.out.println("Que voulez vous faire: \n1-Attaquer ⚔ / 2- Manger \uD83C\uDF54 / 3-Potion \uD83E\uDDEA / 4-Ce protéger ✋");
                }
                int choixattaque = scanner.nextInt();
                while (choixattaque > 4 || choixattaque < 1) {
                    displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 4. \n Veuillez choisir une autre valeur:");
                    choixattaque = scanner.nextInt();
                }
                if (choixattaque == 1) {
                    if (goodOne instanceof Healer) {
                        displayMessage("Qui voulez vous soigner ?");
                        for(int l = 0; l<heros.size();l++){
                            displayMessage((l+1)+"-"+heros.get(l).getName());
                        }
                        int healerchoice = scanner.nextInt();
                        while (healerchoice < 1 || healerchoice > heros.size()) {
                            displayMessage("Veuillez choisir une valeur comprise entre 1 et " + heros.size());
                            healerchoice = scanner.nextInt();
                        }
                        goodOne.fight(heros.get(healerchoice - 1));
                        String filePathHealing = "src/sound/healing.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathHealing));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        displayMessage("Qui voulez vous attaquer ?");
                        for(int l = 0; l<enemies.size();l++){
                            displayMessage((l+1)+"-"+enemies.get(l).getName());
                        }
                        ixEnemy = scanner.nextInt();
                        while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                            displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                            ixEnemy = scanner.nextInt();
                        }
                        String filePathPotion = "src/sound/fight.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        badOne = enemies.get((ixEnemy - 1));
                        // Riposte du gentil, s'il n'est pas vaincu
                        displayMessage("Le gentil " + goodOne.getName() + " attaque le méchant " + badOne.getName() + "...");
                        goodOne.fight(badOne);
                    }
                } else if (choixattaque == 2) {
                    goodOne.manger(goodOne);
                    if(goodOne.nbfood>0){
                        String filePathPotion = "src/sound/Eating.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(goodOne.getName() + " à manger, voici c'est point de vie: " + goodOne.getHealthPoint());
                    }else{
                        displayMessage("Vous n'avez plus de nourriture votre tour a été sauté");
                    }
                } else if (choixattaque == 3) {
                    if(goodOne instanceof SpellCasters){
                            displayMessage("Veuillez choisir votre potion: \n 1-Potion de régénération("+goodOne.nbpotionheal+") / 2-Potion de dégat("+goodOne.nbpotiondegat+") / 3-Potion de mana("+((SpellCasters) goodOne).nbpotionmana+")");
                            int choixpotion = scanner.nextInt();
                            while (choixpotion > 3 || choixpotion < 1) {
                                displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 3. \n Veuillez choisir une autre valeur:");
                                choixpotion = scanner.nextInt();
                            }
                            if (choixpotion == 1) {
                                String filePathPotion = "src/sound/PotionDrinking.wav";
                                try {
                                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                    Clip clip = AudioSystem.getClip();
                                    clip.open(audioInput);
                                    clip.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Thread.sleep(750);
                                potion_heal.regenerate(goodOne);
                                displayMessage("Il reste " + goodOne.nbpotionheal + " potion de heal à " + goodOne.getName());
                            } else if (choixpotion == 2) {
                                displayMessage("Sur qui voulez vous lancer la potion:");
                                for(int l = 0; l<enemies.size();l++){
                                    displayMessage((l+1)+"-"+enemies.get(l).getName());
                                }
                                ixEnemy = scanner.nextInt();
                                while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                                    displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                                    ixEnemy = scanner.nextInt();
                                }
                                badOne = enemies.get((ixEnemy-1));
                                potion_degat.degat(badOne, goodOne);
                                displayMessage("Il reste " + goodOne.nbpotiondegat + " potion de dégat à " + goodOne.getName());
                                if (badOne.getHealthPoint() <= 0) {
                                    enemies.remove(badOne);
                                    displayMessage(ANSI_GREEN+"Bravo, " + goodOne.getName() + " a vaincu " + badOne.getName() + " !!!"+ANSI_RESET);
                                    // ixEnemy=1;
                                }
                            }else if(choixpotion == 3){
                                potion_mana.mana((SpellCasters) goodOne);
                            }
                    }else {
                        displayMessage("Veuillez choisir votre potion: \n 1-Potion de régénération(" + goodOne.nbpotionheal + ") / 2-Potion de dégat(" + goodOne.nbpotiondegat + ")");
                        int choixpotion = scanner.nextInt();
                        while (choixpotion > 2 || choixpotion < 1) {
                            displayMessage("La valeur que vouus avez choisi n'est pas comprise entre 1 et 2. \n Veuillez choisir une autre valeur:");
                            choixpotion = scanner.nextInt();
                        }
                        if (choixpotion == 1) {
                            String filePathPotion = "src/sound/PotionDrinking.wav";
                            try {
                                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathPotion));
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInput);
                                clip.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Thread.sleep(750);
                            potion_heal.regenerate(goodOne);
                            displayMessage("Il reste " + goodOne.nbpotionheal + " potion de heal à " + goodOne.getName());
                        } else if (choixpotion == 2) {
                            displayMessage("Sur qui voulez vous lancer la potion:");
                            for (int l = 0; l < enemies.size(); l++) {
                                displayMessage((l + 1) + "-" + enemies.get(l).getName());
                            }
                            ixEnemy = scanner.nextInt();
                            while (ixEnemy < 1 || ixEnemy > enemies.size()) {
                                displayMessage("Veuillez choisir une valeur comprise entre 1 et " + enemies.size());
                                ixEnemy = scanner.nextInt();
                            }
                            badOne = enemies.get((ixEnemy - 1));
                            potion_degat.degat(badOne, goodOne);
                            displayMessage("Il reste " + goodOne.nbpotiondegat + " potion de dégat à " + goodOne.getName());
                        }
                    }
                } else if (choixattaque == 4) {
                    displayMessage("Le héros "+goodOne.getName()+" s'est protéger de l'attaque !");
                    String filePathprotec = "src/sound/protection.wav";
                    try {
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathprotec));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInput);
                        clip.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                    continue;
                }
                    if (badOne.getHealthPoint() <= 0) {
                        displayMessage(ANSI_GREEN+"Bravo, " + goodOne.getName() + " a vaincu " + badOne.getName() + " !!!"+ANSI_RESET);
                        enemies.remove((ixEnemy - 1));
                        ixEnemy = 1;
                    }else{
                        Thread.sleep(400);
                        String filePathHit = "src/sound/Hit.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathHit));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        badOne.fight(goodOne);
                        if (goodOne.getHealthPoint() <= 0) {
                            displayMessage(ANSI_RED_BG+"Non, " + badOne.getName() + " a vaincu " + goodOne.getName() + " !!!"+ANSI_RESET);
                            heros.remove(ixHero);
                            ixHero--;
                        };
                    }
                    if (heros.size() == 0) {
                        displayMessage(ANSI_RED_BG+"Les héros ont perdu, c'est la fin du monde..."+ANSI_RESET);
                        String filePathlose = "src/sound/lose.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathlose));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(2000);
                        break;
                    }
                    if (enemies.size() == 0) {
                        displayMessage(ANSI_GREEN+"BRAVO, les héros ont gagné, le monde est sauvé !!!"+ANSI_RESET);
                        String filePathwin = "src/sound/victory.wav";
                        try {
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filePathwin));
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(2000);
                        reward(potion_heal,potion_degat,potion_mana);
                        break;
                    }
                    ixHero = (ixHero + 1) % heros.size();
                }
                if (heros.size() == 0) {
                    break;
                }
                if (enemies.size() == 0) {
                    displayMessage("Round " + (i + 1));
                }
            }
        }
    }

    public void reward(Potion potion_heal,Potion potion_degat, Potion potion_mana){
        Scanner scanner = new Scanner(System.in);
        displayMessage("Quels reward vous voulez choisir: \n 1-Augementation de dégats\n 2-Augementation des flèches pour les hunters et baisse du cout en mana pour les Healer et Mage\n 3-Augementation de la résistance au attaques\n 4- Augementer le nombre de potion et de nourriture\n 5- Augementer l'efficaciter des potion et de la nourriture");
        int choixrew = scanner.nextInt();
        while (choixrew < 1 || choixrew > 5) {
            displayMessage("Veuillez choisir un jeu entre 1 et 5");
            choixrew = scanner.nextInt();
        }

        switch (choixrew){
            case 1:
                for(int t=0; t<heros.size();t++){
                    if(heros.get(t) instanceof Warrior) {
                        degatcouteau=degatcouteau*2;
                        heros.get(t).take(new Weapon("knife", degatcouteau));
                    }else if(heros.get(t) instanceof Hunter) {
                        degatarc=degatarc*2;
                        heros.get(t).take(new Weapon("Bow", degatarc));
                    }else if(heros.get(t) instanceof Mage) {
                        ((Mage) heros.get(t)).damage = ((Mage) heros.get(t)).damage *2;
                    }else if(heros.get(t) instanceof Healer) {
                        ((Healer) heros.get(t)).healpoint = ((Healer) heros.get(t)).healpoint *2;
                    }
                }
                break;
            case 2:
                for(int j=0; j< heros.size();j++){
                    if(heros.get(j) instanceof Mage){
                        ((Mage) heros.get(j)).loosemana = ((Mage) heros.get(j)).loosemana - 2;
                        displayMessage("Le cout en mana pour le hero "+heros.get(j).getName()+" a été reduite a "+((Mage) heros.get(j)).loosemana+" points de mana.");
                    }else if(heros.get(j) instanceof Healer){
                        ((Healer) heros.get(j)).loosemana = ((Healer) heros.get(j)).loosemana - 2;
                        displayMessage("Le cout en mana pour le hero "+heros.get(j).getName()+" a été reduite a "+((Healer) heros.get(j)).loosemana+" points de mana.");
                    }else if(heros.get(j) instanceof Hunter){
                        ((Hunter) heros.get(j)).arrow = ((Hunter) heros.get(j)).arrow + 10;
                        displayMessage("Le hero "+heros.get(j).getName()+" posède maintenant "+((Hunter) heros.get(j)).arrow+" flèches.");
                    }
                }
                break;
            case 3:
                for(int j=0; j< heros.size();j++){
                    if(heros.get(j).getHealthPoint()<75){
                        heros.get(j).setFullHealthPoint();
                        displayMessage("La vie de "+heros.get(j).getName()+" a été remise au max.");
                    }else{
                        heros.get(j).setFullArmorPoint();
                        displayMessage("Comme la vie de "+heros.get(j).getName()+" êtait déja au max sont armure a été remise au max.");
                    }
                }
                break;
            case 4:
                for(int j=0; j< heros.size();j++) {
                    heros.get(j).nbpotionheal = heros.get(j).nbpotionheal + 3;
                    heros.get(j).nbpotiondegat = heros.get(j).nbpotiondegat + 3;
                    if (heros.get(j) instanceof SpellCasters) {
                        ((SpellCasters) heros.get(j)).nbpotionmana = ((SpellCasters) heros.get(j)).nbpotionmana + 3;
                        displayMessage(heros.get(j).getName() + " posède maintenant " + heros.get(j).nbpotionheal + " potions de heal, " + heros.get(j).nbpotiondegat + " potions de dégats et "+((SpellCasters)heros.get(j)).nbpotionmana+" potion de mana");
                    } else
                        displayMessage(heros.get(j).getName() + " posède maintenant " + heros.get(j).nbpotionheal + " potions de heal et " + heros.get(j).nbpotiondegat + " potions de dégats");
                    }
                break;
            case 5:
                potion_heal.healvalue = potion_heal.healvalue+5;
                potion_degat.degatvalue = potion_degat.degatvalue+5;
                potion_mana.winmanapoint = potion_degat.winmanapoint+5;
                displayMessage("Les potions de régénération, de dégat et de mana ont gagné +5 d'efficacité");
                break;
        }
    }

    private InputParser inputParser;

    private List<Combatant> heros;
    private List<Combatant> enemies;


    // Méthodes d'affichage
    // (STATIQUES pour pouvoir les appeler depuis n'importe où dans le programme)
    //
    // => pourraient éventuellement être déplacées dans le package
    //    "com.isep.utils", en s'inspirant de "InputParser" (méthodes de saisie)

    public static void displayStatus(List<Combatant> h, List<Combatant> e) {
        System.out.println("#########################");
        for (Combatant c: h) {
            System.out.print(c.getName() + "(" + c.getHealthPoint() + "\u2665 - "+c.getArmorPoints()+"\uD83D\uDEE1)");
        }
        System.out.println();
        for (Combatant c: e) {
            System.out.print(c.getName() + "(" + c.getHealthPoint() + "\uD83D\uDC9C) ");
        }
        System.out.println();
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }
}