package main;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class General {
    private int gold;
    public Vector<Soldier> army;
    public General(int gold, Vector<Soldier> army) {
        this.gold = gold;
        this.army = army;
    }
    public General(int gold) {
        this.gold = gold;
    }
    public General() {
        this.gold = 0;
        this.army = new Vector<Soldier>();
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public Vector<Soldier> getArmy() {
        return army;
    }
    public void setArmy(Vector<Soldier> army) {
        this.army = army;
    }
    public Soldier getSoldier(int idx) {
        return this.army.get(idx);
    }
    public void addSoldier(Soldier soldier) {
        this.army.add(soldier);
    }
    public void removeSoldier(int idx) {
        this.army.remove(idx);
    }
    public List<Soldier> getRegiment(int id) {
        if(id >=0 && id < army.size()) {
            return army.subList(0,id+1);
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
        Iterator<Soldier> it = army.iterator();
        while(it.hasNext()) {
            Soldier soldier = it.next();
            soldier.rankUp();
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
                soldier.expUp();
            }
            checkRanks();
        }
    }
    public void training(@org.jetbrains.annotations.NotNull List<Soldier> regiment) {
        int cost = checkTraining(regiment);
        if(cost != 0) {
            this.gold -= cost;
            for (Soldier soldier : regiment) {
                soldier.expUp();
            }
            checkRanks();
        }
    }
    public void checkDeath() {
        Iterator<Soldier> it = army.iterator();
        while(it.hasNext()) {
            Soldier soldier = it.next();
            if(soldier.getExp() == 0) {
                it.remove();
            }
        }
    }
    public void buySoldier(@NotNull Soldier soldier) {
        if(soldier.getRank()*10 <= gold) {
            army.add(soldier);
            gold -= soldier.getRank()*10;
        }else{
            //TODO inform observer
        }
    }
    public void attack(@NotNull General general2) {
        if(getTotalStrength() > general2.getTotalStrength()) {
            //TODO victory
        } else if (getTotalStrength() < general2.getTotalStrength()) {
            //TODO lost
        }else {
            //TODO shoot random
        }
    }
}
