public class MarineN55 extends UniteMarine{

    public MarineN55(int lig, int col, Terrain t) {
        super("MarineN55", "Soldat du G-5", lig, col, t);
    }

    public void action() {

        // On calcule un petit décalage (-1, 0 ou 1)
        int deltaLig = (int)(Math.random() * 3) - 1; 
        int deltaCol = (int)(Math.random() * 3) - 1;
    
        int nL = this.getLigne() + deltaLig;
        int nC = this.getColonne() + deltaCol;
    
        // On vérifie qu'on ne sort pas du terrain (1 à 5)
        if (nL >= 1 && nL <= 5 && nC >= 1 && nC <= 5) {
            this.seDeplacer(nL, nC);
            System.out.println(nom + " (" + grade + ") patrouille en " + nL + "," + nC);
        }
    }

    public String toString() {
        return "Marine: " + nom + " [" + grade + "] en (" + lig + "," + col + ")";
    }
}
