public abstract class Pirate extends Agent {
    protected int prime;
    protected String nom;

    public Pirate(String nom, int lig, int col, Terrain t, int prime) {
        super(lig, col, t);
        this.nom = nom;
        this.prime = prime;
    }
}