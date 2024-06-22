package main;

import main.Secretary.InfoStation;
import main.Secretary.Scribe;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class General implements Serializable {
    private int gold;
    private char num;
    public List<Soldier> army;
    public InfoStation infostation = new InfoStation();
    private static final long serialVersionUID = 1L;
    public General(int gold, List<Soldier> soldiers, String fileName) {
        this.gold = gold;
        this.army = new ArrayList<Soldier>(soldiers);
        this.num = fileName.charAt(6);
        Scribe scribe = new Scribe(fileName);
        infostation.addObserver(scribe);
        infostation.setMessage("General " + num + "  Created, gold: " + gold + " army: " + army.toString());
    }
    public General(int gold, String fileName) {
        this.gold = gold;
        this.army = new ArrayList<Soldier>();
        this.num = fileName.charAt(6);
        Scribe scribe = new Scribe(fileName);
        infostation.addObserver(scribe);
        infostation.setMessage("General " + num + " Created, gold: " + gold);
    }
    public General(String fileName) {
        this.gold = 0;
        this.army = new ArrayList<Soldier>();
        this.num = fileName.charAt(6);
        Scribe scribe = new Scribe(fileName);
        infostation.addObserver(scribe);
        infostation.setMessage("General " + num + " Created, gold: " + gold);
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
        infostation.setMessage("General " + num + " current gold: " + gold);
    }
    public List<Soldier> getArmy() {
        return army;
    }
    public void setArmy(List<Soldier> soldiers) {
        this.army = new ArrayList<Soldier>(soldiers);
        infostation.setMessage("General " + num + " current army: " + this.army);
    }
    public Soldier getSoldier(int idx) {
        return this.army.get(idx);
    }
    public void addSoldier(Soldier soldier) {
        army.add(soldier);
        infostation.setMessage("General " + num + " got a new soldier: " + soldier.toString());
    }
    public void removeSoldier(int idx) {
        infostation.setMessage("General " + num + " removed a soldier: " + getSoldier(idx));
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
            return army.subList(ida, idb+1);
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
        ListIterator<Soldier> it = army.listIterator();
        while(it.hasNext()) {
            Soldier soldier = it.next();
            if(soldier.rankUp()){
                infostation.setMessage("General " + num + " soldier rankUp, now is: " + soldier);
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
        infostation.setMessage("General " + num + " attempted training, but had low gold, gold: " + this.gold);
        return 0;
    }
    public int checkTraining(List<Soldier> regiment) {
        int cost = 0;
        for (Soldier soldier : regiment) {
            cost += soldier.getRank();
        }
        if(cost <= gold) {
            return cost;
        }
        infostation.setMessage("General " + num + " attempted training, but had low gold, gold: " + this.gold);
        return 0;
    }
    public void training() {
        int cost = checkTraining();
        if(cost != 0) {
            this.gold -= cost;
            incExp();
            checkRanks();
            infostation.setMessage("General " + num + " completed training, gold: " + this.gold + " army: " + this.army);
        }
    }
    public void training(List<Soldier> regiment) {
        int cost = checkTraining(regiment);
        if(cost != 0) {
            this.gold -= cost;
            for (Soldier soldier : regiment) {
                soldier.expUp();
            }
            checkRanks();
            infostation.setMessage("General " + num + " completed training, gold: " + this.gold + " army: " + this.army);
        }
    }
    public void checkDeath() {
        Iterator<Soldier> it = army.iterator();
        while(it.hasNext()) {
            Soldier soldier = it.next();
            if(soldier.getExp() == 0) {
                infostation.setMessage("General " + num + " soldier death, lack of exp: " + soldier);
                it.remove();
            }
        }
    }
    public void buySoldier(int rank) {
        if(rank*10 <= gold) {
            Soldier soldier = new Soldier(rank, 1);
            army.add(soldier);
            gold -= rank*10;
            infostation.setMessage("General " + num + " bought a soldier: " + soldier + " gold: " + this.gold);
        }else{
            infostation.setMessage("General " + num + " attempted to buy a soldier, but had low gold, gold: " + this.gold);
        }
    }
    public void attack(General general2) {
        if(getTotalStrength() > general2.getTotalStrength()) {
            int temp = general2.getGold()/10;
            this.gold = getGold() + temp;
            general2.setGold(general2.getGold() - temp);
            general2.decExp();
            incExp();
            infostation.setMessage("General " + num + " attacked and WON, current gold: " + this.gold + " army: " + this.army);
        } else if (getTotalStrength() < general2.getTotalStrength()) {
            int temp = getGold()/10;
            this.gold = getGold() - temp;
            general2.setGold(general2.getGold() + temp);
            decExp();
            general2.incExp();
            infostation.setMessage("General " + num + " attacked and LOST, current gold: " + this.gold + " army: " + this.army);
        }else {
            execute();
            general2.execute();
            infostation.setMessage("General " + num + " attacked and DREW, current gold: " + this.gold + " army: " + this.army);
        }
    }
    public void decExp() {
        for (Soldier soldier : army) {
                soldier.expDown();
            }
        checkDeath();
    }
    public void incExp() {
        for (Soldier soldier : army) {
            soldier.expUp();
        }
        checkRanks();
    }
    public void execute() {
        int random = ThreadLocalRandom.current().nextInt(0, army.size());
        removeSoldier(random);
        checkRanks();
    }
    public void save(String filename) {
        try(FileOutputStream file = new FileOutputStream(filename)) {
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static General load(String filename) {
         try(FileInputStream file = new FileInputStream(filename); ObjectInputStream in = new ObjectInputStream(file)) {
            return (General)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
