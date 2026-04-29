public class ChapeauxDePaille extends EquipagePirate{
    public ChapeauxDePaille(int lig, int col, Terrain t) {
        super("ChapeauxDePaille", lig, col, t);
    }

    // Dans ChapeauxDePaille
    public void action() {
        // Déplacement vers une case aléatoire
        int nouvelleLig = (int)(Math.random() * t.nbLignes) + 1;
        int nouvelleCol = (int)(Math.random() * t.nbColonnes) + 1;
        this.seDeplacer(nouvelleLig, nouvelleCol);
        System.out.println(nom + " navigue vers (" + nouvelleLig + "," + nouvelleCol + ")");
    
        // Puis il récolte ce qu'il y a sur la case
        this.recolter();
    }


    public String toString() {
        return "Paille";
    }
    
}