public abstract class Agent {
    protected int lig, col;
    protected Terrain t;

    public Agent(int lig, int col, Terrain t) {
        this.lig = lig;
        this.col = col;
        this.t = t;
    }

    public double distance(int lig, int col) {
        //pow = puissance
        return Math.sqrt(Math.pow(this.lig - lig, 2) + Math.pow(this.col - col, 2));
    }

    public void seDeplacer(int lig, int col) {
        this.lig = lig;
        this.col = col;
    }

    public abstract void action(); // Méthode abstraite pour la checklist [cite: 117]
    
    public int getLigne() {
        return this.lig;
    }

    public int getColonne() {
        return this.col;
    }

    public String toString() {
        return "Agent[pos=" + lig + "," + col + "]";
    }

}