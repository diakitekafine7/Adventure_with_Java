public abstract class Pirate extends Agent{
    protected String nom;

    public Pirate(String nom, int lig, int col, Terrain t) {
        super(lig, col, t);
        this.nom = nom;
    }

    public void recolter() {
        Ressource r = t.getCase(lig, col);
        if (r != null) {
            System.out.println(nom + " récolte " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
}