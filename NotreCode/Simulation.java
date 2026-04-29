import java.util.ArrayList;

public class Simulation {
    private Terrain terrain; 
    private ArrayList<Agent> agents; 
    private boolean onePieceTrouve = false;
    
    // Statistiques 
    private int nbFruits = 0;
    private int nbCombatsTotal = 0; 
    private int etapeCourante = 0;  

    public Simulation(int nbLignes, int nbColonnes) {
        // Le terrain est un tableau à 2 dimensions 
        this.terrain = new Terrain(nbLignes, nbColonnes);
        this.agents = new ArrayList<>();
        this.initialiser();
    }

    // Initialisation du terrain avec des ressources et des agents 
    private void initialiser() {
        // Ajout des combattants (Equipages et Unités Marine)
        agents.add(new ChapeauxDePaille(1, 1, terrain));
        agents.add(new MarineN55(1, 2, terrain)); 
        
        // Ajout des Ressources 
        // Ressource évolutive 
        terrain.setCase(2, 2, new FruitDuDemon());
        // 2. Ressource stable
        terrain.setCase(4, 1, new OnePiece(500));
    }


    //  Méthodes d'organisation 
    private void detecterEtGererCombats() {
        for (int i = 0; i < agents.size(); i++) {
            for (int j = i + 1; j < agents.size(); j++) {
                Agent a1 = agents.get(i);
                Agent a2 = agents.get(j);
                if (a1.getLigne() == a2.getLigne() && a1.getColonne() == a2.getColonne()) {
                    this.nbCombatsTotal++; // Statistique
                    gererCombat(a1, a2);
                }
            }
        }
    }

    public void gererCombat(Agent a1, Agent a2) {
        // Utilisation de l'interface Combattant
        if (a1 instanceof Combattant && a2 instanceof Combattant) {
            System.out.println("\n COMBAT en (" + a1.getLigne() + "," + a1.getColonne() + ")"); 
            
            if (Math.random() > 0.5) {
                System.out.print(" Vainqueur : ");
                ((Combattant)a1).crierVictoire(); // Utilisation interface
                a2.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1); 
            } else {
                System.out.print(" Vainqueur : ");
                ((Combattant)a2).crierVictoire();
                a1.seDeplacer((int)(Math.random()*5)+1, (int)(Math.random()*5)+1); 
            }
        }
    }

    private void faireEvoluerRessources() {
        int count = 0;
        for (Ressource r : terrain.lesRessources()) {
            if (r instanceof FruitDuDemon) {
                ((FruitDuDemon) r).murer(); // Appel de la méthode d'évolution
                count++;
            }
        }
        this.nbFruits = count; // Mise à jour stat
    }

    private void genererNouveauFruit() {
        if (Math.random() < 0.20) { // 20% de chance par tour
            int l = (int)(Math.random() * 5) + 1;
            int c = (int)(Math.random() * 5) + 1;
            if (terrain.getCase(l, c) == null) {
                terrain.setCase(l, c, new FruitDuDemon());
                System.out.println(" Un nouveau Fruit du Démon est apparu en (" + l + "," + c + ")");
            }
        }
    }

    private void verifierOnePiece() {
        boolean encorePresent = false;
        for (Ressource r : terrain.lesRessources()) {
            if (r instanceof OnePiece) encorePresent = true;
        }
        if (!encorePresent) onePieceTrouve = true;
    }

    //  Affichages 
    public void afficherStats() {
        System.out.println("\n======= STATISTIQUES =======");
        System.out.println("Tour n° : " + etapeCourante);
        System.out.println("Combats enregistrés : " + nbCombatsTotal);
        System.out.println("Fruits présents : " + nbFruits);
        System.out.println("One Piece trouvé : " + (onePieceTrouve ? "OUI" : "NON"));
        System.out.println("============================\n");
    } 

    public void afficherSimulation() {
        String separation = ":----------:----------:----------:----------:----------:";
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
                    String label = (agentIci instanceof EquipagePirate) ? "P:" : "M:";
                    String nomAgent = agentIci.toString();
                    String nomCourt = nomAgent.substring(0, Math.min(nomAgent.length(), 6));
                    System.out.print(String.format(" %-8s |", label + nomCourt));
                } else {
                    Ressource r = terrain.getCase(l, c);
                    if (r != null) {
                        String type = (r instanceof FruitDuDemon) ? "Fruit" : "OnePiece";
                        System.out.print(String.format(" %-8s |", type));
                    } else {
                        System.out.print("          |");
                    }
                }
            }
            System.out.println("\n" + separation);
        }
    }

    //  Réalise une étape complète de la simulation 
    public void etape() {
        this.etapeCourante++; 

        // Actions des agents 
        for (Agent a : agents) {
            a.action();
        }

        // Gestion des combats
        this.detecterEtGererCombats();

        // Évolution des ressources existantes
        this.faireEvoluerRessources();

        // Génération de nouveaux fruits (20% de chance)
        this.genererNouveauFruit();

        // Mise à jour de l'état du One Piece
        this.verifierOnePiece();

        // Affichage complet
        this.afficherSimulation();
        this.afficherStats();
    }

    public boolean isOnePieceTrouve() { 
        return onePieceTrouve; 
    }
}