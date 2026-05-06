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
            this.fruitsManges.add(new FruitDuDemon((FruitDuDemon)r));
            t.viderCase(lig, col); // Le fruit est ramassé
            System.out.println(nom + " a mangé un Fruit du Démon !");
        } 
        else if (r instanceof OnePiece) {
            // Le cri de victoire se fait au moment de la récolte physique
            System.out.println("!!!! " + nom + " : J'AI TROUVÉ LE ONE PIECE ! !!!!");
            System.out.println("JE SERAI LE ROI DES PIRATES !");
            
            t.viderCase(lig, col); // On retire le One Piece du terrain
        }
    }
    
    public void crierVictoire() { 
        System.out.println(nom + " : Le One Piece sera à nous !"); 
    }

    public ArrayList<FruitDuDemon> getFruitsManges() {
        return this.fruitsManges;
    }
}
