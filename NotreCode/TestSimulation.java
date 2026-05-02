public class TestSimulation {
    public static void main(String[] args) {
        try {
            Simulation sim = Simulation.getInstance(5, 5);
            System.out.println("--- ÉTAT INITIAL (Étape 0) ---");
            sim.afficherSimulation(); 
            sim.afficherStats();
            
            int i = 1;

            while (i < 10 && sim.isOnePieceTrouve() == false) {
                System.out.println("--- Étape " + i + " ---");
                sim.etape();
                i++;
            }

            if (sim.isOnePieceTrouve() == true) {
                System.out.println("LE ONE PIECE A ÉTÉ TROUVÉ ! JE SERAI ROI DES PIRATES !");
            }

        } catch (SimulationException e) {
            System.out.println("ERREUR " + e.getMessage());
        }
    }
}