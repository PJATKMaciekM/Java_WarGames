package test;

import main.General;
import main.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class War_Test {
    private General general;
    private General general2;
    private ArrayList<Soldier> soldiers;
    private ArrayList<Soldier> soldiers2;

    @BeforeEach
    void setUp() throws IOException {
        new FileWriter("Scribe1.txt", false).close();
        new FileWriter("Scribe2.txt", false).close();
        general = new General(1000, "Scribe1.txt");
        general2 = new General(1000, "Scribe2.txt");
        soldiers = new ArrayList<>(Arrays.asList(new Soldier(2, 2), new Soldier(3, 3), new Soldier(4, 4), new Soldier(1, 1), new Soldier(1, 2), new Soldier(1, 3), new Soldier(1, 4), new Soldier(1, 1), new Soldier(2, 1), new Soldier(2, 3), new Soldier(2, 4)));
        soldiers2 = new ArrayList<>(Arrays.asList(new Soldier(2, 2), new Soldier(3, 3), new Soldier(4, 4), new Soldier(1, 1), new Soldier(1, 2), new Soldier(1, 3), new Soldier(1, 4), new Soldier(1, 1), new Soldier(2, 1), new Soldier(2, 3), new Soldier(2, 4)));
        general.setArmy(soldiers);
        general2.setArmy(soldiers2);
    }
    @Test
    void test_war1() { //str = 56
        general.training(); //str = 73
        general.attack(general2);
        assertEquals(900, general2.getGold());
    }
    @Test
    void test_draw() {
        general.attack(general2);
        assertEquals(10, general.getArmy().size());
    }
    @Test
    void test_war_lost() {
        general.training();
        general2.attack(general);
        assertEquals(1080, general.getGold());
    }
    @Test
    void test_war2() {
        general.training();
        for(int i = 0; i < 10; i++){
            general.training();
            general2.buySoldier(4);
        }
        general2.attack(general);
        assertEquals(540, general2.getGold());
    }
    @Test
    void test_war3() {
        general.training();
        for(int i = 0; i < 10; i++){
            general.training();
            general2.buySoldier(4);
        }
        general2.training();
        general2.attack(general);
        for(int i = 0; i < 10; i++){
            general2.training();
        }
        general2.attack(general);
    }
    @Test
    void test_war4() {
        general.training();
        for(int i = 0; i < 10; i++){
            general.training();
            general2.buySoldier(4);
        }
        general2.training();
        general2.attack(general);
        for(int i = 0; i < 10; i++){
            general2.training();
        }
        for(int i = 0; i < 10; i++){
            general2.attack(general);
        }
        assertEquals(274, general.getGold());
    }
}
