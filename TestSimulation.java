public class TestSimulation {
    public static void main(String[] args) {
        Simulation sim = new Simulation(5, 5);
        for (int i = 0; i < 3; i++) {
            System.out.println("--- Étape " + i + " ---");
            sim.etape();
        }
    }
}