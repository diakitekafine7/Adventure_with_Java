import java.util.ArrayList;

public abstract class EquipagePirate extends Agent implements Combattant{
    protected String nom;
    protected ArrayList<FruitDuDemon> fruitsManges = new ArrayList<>();

    public EquipagePirate(String nom, int lig, int col, Terrain t) {
        super(lig, col, t);
        this.nom = nom;
    }

    public void recolter() {
        Ressource r = t.getCase(lig, col);
        if (r instanceof FruitDuDemon) {
            fruitsManges.add(new FruitDuDemon((FruitDuDemon)r));
            System.out.println(nom + " récolte " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
    
    public void crierVictoire() { 
        System.out.println(nom + " : Le One Piece sera à nous !"); 
    }

    public ArrayList<FruitDuDemon> getFruitsManges() {
        return this.fruitsManges;
    }
}
