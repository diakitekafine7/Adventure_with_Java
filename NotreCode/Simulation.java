import java.util.ArrayList;

/**
 * Classe gérant la simulation, le terrain et les interactions entre agents.
 */
public class Simulation {
    private Terrain terrain; // 
    private ArrayList<Agent> agents; // 

    public Simulation(int nbLignes, int nbColonnes) {
        // Le terrain est représenté par un tableau à 2 dimensions 
        this.terrain = new Terrain(nbLignes, nbColonnes);
        this.agents = new ArrayList<>();
        this.initialiser();
    }

    /**
     * Initialisation du terrain avec des ressources et des agents 
     */
    private void initialiser() {
        // Ajout des combattants (Equipages et Unités Marine)
        // On les place au même endroit pour tester le combat immédiatement
        agents.add(new ChapeauxDePaille(1, 1, terrain));
        agents.add(new MarineN55(1, 1, terrain)); 
        
        // Ajout des deux types de ressources obligatoires 
        // 1. Ressource évolutive (Fruit du Démon) 
        terrain.setCase(2, 2, new FruitDuDemon());
        // 2. Ressource stable (One Piece / Trésor) 
        terrain.setCase(4, 1, new OnePiece(500));
    }

    /**
     * Gère les duels lorsque deux agents se rencontrent sur la même case 
     */
    public void gererCombat(Agent a1, Agent a2) {
        // Utilisation de l'interface Combattant 
        if (a1 instanceof Combattant && a2 instanceof Combattant) {
            System.out.println("\n[COMBAT] Rencontre en (" + a1.getLigne() + "," + a1.getColonne() + ")"); 
            
            if (Math.random() > 0.5) {
                System.out.print("[LOG] Vainqueur : ");
                ((Combattant)a1).crierVictoire();
                // Le perdant est expulsé vers une position aléatoire 
                a2.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1); 
            } else {
                System.out.print("[LOG] Vainqueur : ");
                ((Combattant)a2).crierVictoire();
                a1.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1); 
            }
        }
    }
    
    /**
     * Réalise une étape complète de la simulation 
     */
    public void etape() {
        // 1. Chaque agent réalise une action (déplacement/récolte) 
        for (Agent a : agents) {
            a.action();
        } 
    
        // 2. Détection des rencontres et déclenchement des combats
        for (int i = 0; i < agents.size(); i++) {
            for (int j = i + 1; j < agents.size(); j++) {
                Agent a1 = agents.get(i);
                Agent a2 = agents.get(j);
                if (a1.getLigne() == a2.getLigne() && a1.getColonne() == a2.getColonne()) {
                    gererCombat(a1, a2);
                }
            }
        }
    
        // 3. Mise à jour automatique des ressources évolutives 
        ArrayList<Ressource> res = terrain.lesRessources();
        for (Ressource r : res) {
            if (r instanceof FruitDuDemon) {
                ((FruitDuDemon) r).murer();
            }
        }

        // 4. Affichage des informations sur l'étape 
        this.afficherSimulation(); 
    }

    /**
     * Affiche l'état du terrain et la position des agents sous forme de grille 
     */
    public void afficherSimulation() {
        String separation = ":-------:-------:-------:-------:-------:";
        System.out.println(separation);
    
        for (int l = 1; l <= 5; l++) {
            System.out.print("|"); 
            for (int c = 1; c <= 5; c++) {
                Agent agentIci = null;
                for (Agent a : agents) {
                    if (a.getLigne() == l && a.getColonne() == c) {
                        agentIci = a;
                        break;
                    }
                }
    
                if (agentIci != null) {
                    // Identification visuelle des types d'agents
                    String label = (agentIci instanceof EquipagePirate) ? "P:" : "M:";
                    String nomAgent = agentIci.toString();
                    String nomCourt = nomAgent.substring(0, Math.min(nomAgent.length(), 5));
                    System.out.print(String.format(" %-5s |", label + nomCourt));
                } else {
                    Ressource r = terrain.getCase(l, c); // 
                    if (r != null) {
                        String type = (r instanceof FruitDuDemon) ? "Fruit" : "Piece";
                        System.out.print(String.format(" %-5s |", type));
                    } else {
                        System.out.print("       |"); 
                    }
                }
            }
            System.out.println("\n" + separation);
        }
    }
}