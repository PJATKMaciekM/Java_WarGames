package main.Soldiers;

public abstract class Soldier {
    protected int rank;
    protected int exp;


    public Soldier(int rank, int exp) {
        this.rank = rank;
        this.exp = exp;
    }
   //TODO death w generale
    public abstract void rankUp();
    public int getStrength() {
        return rank * exp;
    }
}
