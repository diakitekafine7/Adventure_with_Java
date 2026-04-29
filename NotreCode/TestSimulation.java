public class TestSimulation {
    public static void main(String[] args) {
        Simulation sim = new Simulation(5, 5);
        int i=0;
        while (i < 5 && sim.isOnePieceTrouve()== false) {
            System.out.println("--- Étape " + i + " ---");
            sim.etape();
            i++;
        }
        if (sim.isOnePieceTrouve()==true) {
        System.out.println(" LE ONE PIECE A ÉTÉ TROUVÉ ! JE SERAI ROI DES PIRATES !");
        }
    }
}
