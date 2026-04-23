public class MarineN55 extends Marine {

    public MarineN55(String nom, int lig, int col, Terrain t) {
        super(nom, "Soldat du G-5", lig, col, t);
    }

    public void action() {
        //  déplacement aléatoire pour la simulation 
        int nouvelleLig = (int)(Math.random() * 10); 
        int nouvelleCol = (int)(Math.random() * 10);
        
        this.seDeplacer(nouvelleLig, nouvelleCol);
        
        System.out.println(nom + " (" + grade + ") patrouille en " + lig + "," + col);
    }

    public String toString() {
        return "Marine: " + nom + " [" + grade + "] en (" + lig + "," + col + ")";
    }
}
