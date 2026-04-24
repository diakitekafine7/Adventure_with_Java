import java.util.ArrayList;

public class Simulation {
    private Terrain terrain;
    private ArrayList<Agent> agents; 

    public Simulation(int nbLignes, int nbColonnes) {
        this.terrain = new Terrain(nbLignes, nbColonnes);
        this.agents = new ArrayList<>();
        this.initialiser();
    }

    private void initialiser() {
        // On place deux ennemis sur la même case (1,1) pour déclencher un combat immédiat
        agents.add(new ChapeauxDePaille(1, 1, terrain));
        agents.add(new MarineN55(1, 1, terrain)); 
        
        // Ressources
        terrain.setCase(2, 2, new FruitDuDemon());
        terrain.setCase(4, 1, new OnePiece(500));
    }
    
    public void etape() {
        // 1. Actions des agents
        for (Agent a : agents) {
            a.action();
        } 
    
        // 2. Détection des combats (Rencontre sur la même case)
        for (int i = 0; i < agents.size(); i++) {
            for (int j = i + 1; j < agents.size(); j++) {
                Agent a1 = agents.get(i);
                Agent a2 = agents.get(j);
                if (a1.getLigne() == a2.getLigne() && a1.getColonne() == a2.getColonne()) {
                    gererCombat(a1, a2);
                }
            }
        }
    
        // 3. Évolution des ressources [cite: 32]
        ArrayList<Ressource> res = terrain.lesRessources();
        for (Ressource r : res) {
            if (r instanceof FruitDuDemon) {
                ((FruitDuDemon) r).murer();
            }
        }
        this.afficherSimulation(); 
    }

    public void afficherSimulation() {
        String separation = ":-----:-----:-----:-----:-----:";
        System.out.println(separation);
    
        for (int l = 1; l <= 5; l++) {
            System.out.print("|"); // Début de ligne
            for (int c = 1; c <= 5; c++) {
                Agent agentIci = null;
                for (Agent a : agents) {
                    if (a.getLigne() == l && a.getColonne() == c) {
                        agentIci = a;
                        break;
                    }
                }
    
                if (agentIci != null) {
                    // On affiche "Luffy" ou "Marine" (tronqué/centré sur 5 car.)
                    String nom = (agentIci instanceof Luffy) ? "Luffy" : "Marines";
                    System.out.print(String.format("%-5s|", nom));
                } else {
                    Ressource r = terrain.getCase(l, c);
                    if (r != null) {
                        // On affiche "Fruit" si c'est un FruitDuDemon
                        String type = (r instanceof FruitDuDemon) ? "Fruit" : "Ress";
                        System.out.print(String.format("%-5s|", type));
                    } else {
                        System.out.print("     |"); // Case vide (5 espaces)
                    }
                }
            }
            System.out.println("\n" + separation);
        }
    }
}