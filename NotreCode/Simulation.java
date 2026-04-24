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
        // On initialise des membres pirate
        agents.add(new Membre("Luffy", 1, 1, terrain, 3000000));
        agents.add(new Membre("Zoro", 3, 3, terrain, 1100000));
        
        // On ajoute le navire de la Marine
        agents.add(new MarineN55("Smoker", 5, 5, terrain));
        
        // Placement des ressources
        terrain.setCase(2, 2, new FruitDuDemon());
        terrain.setCase(4, 1, new OnePiece(500));
    }

    public void gererCombat(Agent a1, Agent a2) {
        if (a1 instanceof Combattant && a2 instanceof Combattant) {
            System.out.println("[COMBAT] Rencontre en (" + a1.getLigne() + "," + a1.getColonne() + ")");
            
            // Calcul aléatoire simple
            if (Math.random() > 0.5) {
                System.out.print("[LOG] Vainqueur : ");
                ((Combattant)a1).crierVictoire();
                // On peut imaginer que le perdant est déplacé aléatoirement
                a2.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1); 
            } else {
                System.out.print("[LOG] Vainqueur : ");
                ((Combattant)a2).crierVictoire();
                a1.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1);
            }
        }
    }

    public void etape() {
        // 1. Actions des agents
        for (Agent a : agents) {
            a.action();
            System.out.println(">>> " + a.toString() + " est en position (" + a.getLigne() + "," + a.getColonne() + ")");
        } 

        // 2. Mise à jour des fruits
        ArrayList<Ressource> res = terrain.lesRessources();
        for (Ressource r : res) {
            if (r instanceof FruitDuDemon) {
                ((FruitDuDemon) r).murer();
            }
        }
        
        // 3. Appel de l'affichage personnalisé
        this.afficherSimulation(); 
    } // <--- BIEN FERMER LA MÉTHODE ETAPE ICI

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