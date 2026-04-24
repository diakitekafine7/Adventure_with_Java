public class Membre extends ChapeauxDePaille {
    private String nomMembre;
    private int prime;
   

    public Membre( int lig, int col, Terrain t, String nomMembre, int prime) {
        super(lig, col, t);
        this.nomMembre = nomMembre;
        this.prime = prime;
    }

    public void action() {
        Ressource r = t.getCase(lig, col);
        if (r != null) {
            System.out.println(nomMembre + " mange " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
}