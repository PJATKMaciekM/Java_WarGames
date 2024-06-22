package main;

import java.io.Serializable;

public class Soldier implements Serializable {
    private int rank;
    private int exp;

    public Soldier(int rank, int exp) {
        this.rank = rank;
        this.exp = exp;
        checkRank();
    }
    public boolean rankUp() {
        if (rank < 4 && exp == rank*5) {
            this.rank++;
            this.exp = 1;
            return true;
        } else {
            return false;
        }
    }
    public int getStrength() {
        return rank * exp;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    private void checkRank() {
        if (!(rank >= 1 && rank <= 4)) {
            throw new IllegalArgumentException("Rank out of range");
        }
    }
    public void expUp() {
        this.exp++;
    }
    public void expDown() {
        this.exp--;
    }
    public String toString() {
        return "Soldier [rank=" + rank + ", exp=" + exp + "]";
    }
}
