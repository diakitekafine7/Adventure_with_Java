public class ChapeauxDePaille extends EquipagePirate{
    public ChapeauxDePaille(int lig, int col, Terrain t) {
        super("ChapeauxDePaille", lig, col, t);
    }

    public void action() {
        // Calcul déplacement
        int deltaLig = (int)(Math.random() * 3) - 1; 
        int deltaCol = (int)(Math.random() * 3) - 1;
    
        // Calcul des nouvelles coordonnées à partir de la position actuelle
        int nL = this.getLigne() + deltaLig;
        int nC = this.getColonne() + deltaCol;
    
        // Vérification des limites du terrain (typiquement 1 à 5)
        if (nL >= 1 && nL <= 5 && nC >= 1 && nC <= 5) {
            this.seDeplacer(nL, nC);
            
            // Utilisation des getters pour éviter les erreurs de visibilité
            System.out.println(nom + " navigue vers (" + nL + "," + nC + ")");
        }
    
        // 4. Puis il récolte ce qu'il y a sur la case
        this.recolter();
    }

    public String toString() {
        return "Paille";
    }
    
}