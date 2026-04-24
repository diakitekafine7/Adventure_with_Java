public class Membre extends Pirate {
    private int prime;

    public Membre(String nom, int lig, int col, Terrain t, int prime) {
        super(nom, lig, col, t);
        this.prime = prime;
    }

    public void action() {
        Ressource r = t.getCase(lig, col);
        if (r != null) {
            System.out.println(nom + " mange " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
}