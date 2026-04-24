public abstract class UnitéMarine extends Agent implements Combattant{
    protected String grade;
    protected String nom;

    public UnitéMarine(String nom, String grade, int lig, int col, Terrain t) {
        super(lig, col, t); 
        this.nom = nom;
        this.grade = grade;
    }

    public void crierVictoire(){ 
        System.out.println(nom + ": La Justice a triomphé !"); 
    }
}
