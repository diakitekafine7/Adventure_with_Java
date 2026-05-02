public class MarineN55 extends UniteMarine{

    public MarineN55(int lig, int col, Terrain t) {
        super("MarineN55", "Soldat du G-5", lig, col, t);
    }

    public void action() {
        // Patrouille aléatoire grâce à Config
        int nL = Config.deplacementAgent(this.getLigne(), Config.NB_LIGNES);
        int nC = Config.deplacementAgent(this.getColonne(), Config.NB_COLONNES);
        
    
        this.seDeplacer(nL, nC);
        System.out.println(nom + " (" + grade + ") patrouille en " + nL + "," + nC);
    }

    public String toString() {
        return "Marine: " + nom + " [" + grade + "] en (" + lig + "," + col + ")";
    }
}
