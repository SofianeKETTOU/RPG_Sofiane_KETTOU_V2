import com.isep.rpg.Dragon;
import com.isep.rpg.Warrior;
import com.isep.rpg.Weapon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombatantTest {

    @Test
    void fightTest() {
        Warrior w = new Warrior("Ron");
        Weapon sword = new Weapon("sword", 1);
        w.take(sword);
        Dragon d = new Dragon("Dracofeu");
        d.fight(w);
        w.fight(d);
        assertTrue(d.getHealthPoint() == 4);
        assertEquals(3, w.getHealthPoint());
    }

}
