public class ChapeauxDePaille extends EquipagePirate{
    public ChapeauxDePaille(int lig, int col, Terrain t) {
        super("ChapeauxDePaille", lig, col, t);
    }

    @Override
    public void action() {
        this.recolter(); 
    }
}