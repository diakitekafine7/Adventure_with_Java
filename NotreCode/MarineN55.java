public class MarineN55 extends UniteMarine{

    public MarineN55(int lig, int col, Terrain t) {
        super("MarineN55", "Soldat du G-5", lig, col, t);
    }

    public void action() {
        //  déplacement aléatoire pour la simulation 
        int nouvelleLig = (int)(Math.random() * t.nbLignes) + 1;
        int nouvelleCol = (int)(Math.random() * t.nbColonnes) + 1;
        
        this.seDeplacer(nouvelleLig, nouvelleCol);
        
        System.out.println(nom + " (" + grade + ") patrouille en " + lig + "," + col);
    }

    public String toString() {
        return "Marine: " + nom + " [" + grade + "] en (" + lig + "," + col + ")";
    }
}
