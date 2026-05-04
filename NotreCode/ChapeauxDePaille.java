public class ChapeauxDePaille extends EquipagePirate{
    public ChapeauxDePaille(int lig, int col, Terrain t) {
        super("ChapeauxDePaille", lig, col, t);
    }

    public void action() {
        // Utilisation de la méthode statique pour trouver une case au hasard
        int nL = Config.deplacementAgent(this.getLigne(), Config.NB_LIGNES);
        int nC = Config.deplacementAgent(this.getColonne(), Config.NB_COLONNES);

        if (t.sontValides(nL, nC)) {
            this.seDeplacer(nL, nC);
            System.out.println(nom + " navigue vers (" + nL + "," + nC + ")");
        }

        this.recolter();
    }

    public String toString() {
        return "ChapP";
    }
    
}