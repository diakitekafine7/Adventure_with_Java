public class Luffy extends Pirate {
    public Luffy(int lig, int col, Terrain t) {
        super("Luffy", lig, col, t, 3000000);
    }

    public void action() {
        Ressource r = t.getCase(lig, col);
        if (r != null) {
            System.out.println(nom + " mange " + r.toString());
            t.viderCase(lig, col); // Action sur le terrain
        }
    }
}