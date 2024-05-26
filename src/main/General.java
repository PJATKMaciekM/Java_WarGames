package main;

import java.util.ArrayList;
import java.util.List;

public class General {
    private int gold;
    public ArrayList<Soldier> army;
    public General(int gold, ArrayList<Soldier> army) {
        this.gold = gold;
        this.army = army;
    }
    public General(int gold) {
        this.gold = gold;
    }
    public General() {
        this.gold = 0;
        this.army = new ArrayList<Soldier>();
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public ArrayList<Soldier> getArmy() {
        return army;
    }
    public void setArmy(ArrayList<Soldier> army) {
        this.army = army;
    }
    public void addSoldier(Soldier soldier) {
        this.army.add(soldier);
    }
    public void removeSoldier(Soldier soldier) {
        this.army.remove(soldier);
    }
    public List<Soldier> getRegiment(int id) {
        if(id >=0 && id < army.size()) {
            return army.subList(0,id);
        }
        return null;
    }
    public List<Soldier> getRegiment(int ida, int idb) {
        if(ida >=0 && idb < army.size() && ida < idb) {
            return army.subList(ida, idb);
        }
        return null;
    }
    public int getTotalStrength() {
        int total = 0;
        for (Soldier soldier : army) {
            total += soldier.getStrength();
        }
        return total;
    }
    public void checkRanks() {
        for (Soldier soldier : army) {
            if(soldier.rankUp() != 0) {
                int temp = soldier.getRank() + 1;
                removeSoldier(soldier);
                addSoldier(new Soldier(temp, 1));
            }
        }
    }
    public int checkTraining() {
        int cost = 0;
        for (Soldier soldier : army) {
            cost += soldier.getRank();
        }
        if(cost <= gold) {
            return cost;
        }
        return 0;
    }
    public int checkTraining(@org.jetbrains.annotations.NotNull List<Soldier> regiment) {
        int cost = 0;
        for (Soldier soldier : regiment) {
            cost += soldier.getRank();
        }
        if(cost <= gold) {
            return cost;
        }
        return 0;
    }
    public void training() {
        int cost = checkTraining();
        if(cost != 0) {
            this.gold -= cost;
            for (Soldier soldier : army) {
                soldier.setExp(soldier.getExp()+1);
            }
            checkRanks();
        }
    }
    public void training(@org.jetbrains.annotations.NotNull List<Soldier> regiment) {
        int cost = checkTraining(regiment);
        if(cost != 0) {
            this.gold -= cost;
            for (Soldier soldier : regiment) {
                soldier.setExp(soldier.getExp()+1);
            }
            checkRanks();
        }
    }
}
