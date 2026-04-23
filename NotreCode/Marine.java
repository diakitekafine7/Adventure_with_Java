public abstract class Marine extends Agent {
    protected String grade;
    protected String nom;

    public Marine(String nom, String grade, int lig, int col, Terrain t) {
        super(lig, col, t); 
        this.nom = nom;
        this.grade = grade;
    }

    // On peut ajouter une méthode pour capturer un pirate apres
}
