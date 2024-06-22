package test;

import main.General;
import main.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class General_Test {
    private General general;
    private Soldier soldier;

    @BeforeEach
    void setUp() throws IOException {
        new FileWriter("Scribe1.txt", false).close();
        general = new General("Scribe1.txt");
    }

    @Test
    void testAddSoldier() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        assertEquals("Soldier [rank=2, exp=2]", general.getSoldier(0).toString());
    }
   @Test
    void testgetArmy() {
        List<Soldier> expected = Arrays.asList(new Soldier(2,2), new Soldier(1,1), new Soldier(3,2));
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        List<Soldier> actual = general.getArmy();
        assertEquals(expected.toString(), actual.toString());
   }
   @Test
    void testTraining() {
       soldier = new Soldier(2, 2);
       general.addSoldier(soldier);
       soldier = new Soldier(1, 1);
       general.addSoldier(soldier);
       soldier = new Soldier(3, 2);
       general.addSoldier(soldier);
       general.setGold(7);
       general.training();
       assertEquals(17, general.getTotalStrength());
    }
    @Test
    void testgetRegiment() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        List<Soldier> reg = general.getRegiment(1);
        assertEquals(2, reg.size());
    }
    @Test
    void removeSoldier() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        general.removeSoldier(0);
        assertEquals("Soldier [rank=1, exp=1]", general.getSoldier(0).toString());
    }
    @Test
    void testrankUp() {
        soldier = new Soldier(1,4);
        general.addSoldier(soldier);
        general.setGold(2);
        general.training();
        assertEquals("Soldier [rank=2, exp=1]", general.getSoldier(0).toString());
    }
    @Test
    void testrankUp_more() {
        soldier = new Soldier(1,4);
        general.addSoldier(soldier);
        soldier = new Soldier(2,2);
        general.addSoldier(soldier);
        general.setGold(5);
        general.training();
        assertEquals("Soldier [rank=2, exp=1]", general.getSoldier(0).toString());
    }
    @Test
    void testcheckDeath() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 0);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        general.checkDeath();
        assertEquals(2, general.getArmy().size());
    }
    @Test
    void testTraining_Reg() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(4, 3);
        general.addSoldier(soldier);
        general.setGold(100);
        general.training(general.getRegiment(1,2));
        assertEquals(27, general.getTotalStrength());
    }
    @Test
    void testbuySoldier() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        general.setGold(100);
        general.buySoldier(4);
        assertEquals(15, general.getTotalStrength());
    }
    @Test
    void testSave() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        general.setGold(100);
        general.buySoldier(4);
        general.save("gen1.ser");
        assertEquals(15, general.getTotalStrength());
    }
    @Test
    void testLoad() {
        soldier = new Soldier(2, 2);
        general.addSoldier(soldier);
        soldier = new Soldier(1, 1);
        general.addSoldier(soldier);
        soldier = new Soldier(3, 2);
        general.addSoldier(soldier);
        general.setGold(100);
        general.buySoldier(4);
        general.save("gen1.ser");
        General general_test = general.load("gen1.ser");
        assert general_test != null;
        assertEquals(15, general_test.getTotalStrength());
    }
}
