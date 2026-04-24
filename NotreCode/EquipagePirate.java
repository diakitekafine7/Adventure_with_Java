public abstract class EquipagePirate extends Agent implements Combattant{
    protected String nom;

    public EquipagePirate(String nom, int lig, int col, Terrain t) {
        super(lig, col, t);
        this.nom = nom;
    }

    public void recolter() {
        Ressource r = t.getCase(lig, col);
        if (r != null) {
            System.out.prxintln(nom + " récolte " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
    public void crierVictoire() { 
        System.out.println(nom + " : Le One Piece sera à nous !"); 
    }
}
