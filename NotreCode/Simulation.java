import java.util.ArrayList;

public class Simulation {
    private Terrain terrain; 
    private static Simulation instance = null; 
    private ArrayList<Agent> agents; 
    private boolean onePieceTrouve = false;
    
    // Statistiques 
    private int nbFruits = 0;
    private int nbCombatsTotal = 0; 
    private int etapeCourante = 0;  

    private Simulation() throws SimulationException {
        // Le terrain utilise les dimensions centralisées
        this.terrain = new Terrain(Config.NB_LIGNES, Config.NB_COLONNES);
        this.agents = new ArrayList<>();
        this.initialiser();
        
        // Initialisation des stats pour l'étape 0
        this.faireEvoluerRessources(); 
    }

    // Methode pour avoir l'instance (Singleton sans paramètres)
    public static Simulation getInstance() throws SimulationException {
        if (instance == null) {
            instance = new Simulation();
        }
        return instance;
    }

    // Initialisation du terrain avec des ressources et des agents 
    private void initialiser() throws SimulationException  {
        // Ajout des combattants
        agents.add(new ChapeauxDePaille(1, 1, terrain));
        agents.add(new MarineN55(1, 2, terrain)); 
        
        // Ajout des Ressources avec vérification des limites
        if (terrain.sontValides(2, 2)) {
            terrain.setCase(2, 2, new FruitDuDemon());
        }
        
        if (terrain.sontValides(4, 1)) {
            terrain.setCase(4, 1, new OnePiece(500));
        }
    }

    // Méthodes d'organisation 
    private void detecterEtGererCombats() {
        for (int i = 0; i < agents.size(); i++) {
            for (int j = i + 1; j < agents.size(); j++) {
                Agent a1 = agents.get(i);
                Agent a2 = agents.get(j);
                if (a1.getLigne() == a2.getLigne() && a1.getColonne() == a2.getColonne()) {
                    this.nbCombatsTotal++; 
                    gererCombat(a1, a2);
                }
            }
        }
    }

    public void gererCombat(Agent a1, Agent a2) {
        if (a1 instanceof Combattant && a2 instanceof Combattant) {
            System.out.println("\n COMBAT en (" + a1.getLigne() + "," + a1.getColonne() + ")"); 
            
            Agent vainqueur = (Math.random() > 0.5) ? a1 : a2;
            Agent perdant = (vainqueur == a1) ? a2 : a1;
    
            ((Combattant)vainqueur).crierVictoire();
    
            if (perdant instanceof EquipagePirate) {
                EquipagePirate pirate = (EquipagePirate) perdant;
                for (FruitDuDemon f : pirate.getFruitsManges()) {
                    // Recréation du fruit au sol via constructeur de copie
                    this.terrain.setCase(perdant.getLigne(), perdant.getColonne(), new FruitDuDemon(f));
                    System.out.println(" ! Un fruit est tombé au sol pendant la fuite !");
                }
                pirate.getFruitsManges().clear();
            }
    
            // Fuite aléatoire
            perdant.seDeplacer(Config.genererPositionAleatoire(), Config.genererPositionAleatoire()); 
        }
    }

    private void faireEvoluerRessources() {
        int count = 0;
        // On scanne la grille réelle
        for (int l = 1; l <= Config.NB_LIGNES; l++) {
            for (int c = 1; c <= Config.NB_COLONNES; c++) {
                Ressource r = terrain.getCase(l, c);
                if (r instanceof FruitDuDemon) {
                    ((FruitDuDemon) r).murer(); 
                    count++;
                }
            }
        }
        this.nbFruits = count; 
    }

    private void genererNouveauFruit() {
        if (Config.testerChance(Config.CHANCE_APPARITION_FRUIT)) { 
            int l = Config.genererPositionAleatoire();
            int c = Config.genererPositionAleatoire();
            
            // Utilisation de la méthode native de Terrain
            if (terrain.caseEstVide(l, c)) {
                terrain.setCase(l, c, new FruitDuDemon());
                System.out.println("Un nouveau Fruit du Démon est apparu en (" + l + "," + c + ")");
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

    public void afficherStats() {
        System.out.println("\n======= STATISTIQUES =======");
        System.out.println("Tour n° : " + etapeCourante);
        System.out.println("Combats enregistrés : " + nbCombatsTotal);
        System.out.println("Fruits présents : " + nbFruits);
        System.out.println("One Piece trouvé : " + (onePieceTrouve ? "OUI" : "NON"));
        System.out.println("============================\n");
    } 

    public void afficherSimulation() {
        // Séparation dynamique
        String separation = "";
        for(int i=0; i<Config.NB_COLONNES; i++) separation += ":----------";
        separation += ":";
        
        System.out.println(separation);
        for (int l = 1; l <= Config.NB_LIGNES; l++) {
            System.out.print("|");
            for (int c = 1; c <= Config.NB_COLONNES; c++) {
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
                        String type = (r instanceof FruitDuDemon) ? "Fruit" : "OnePce";
                        System.out.print(String.format(" %-8s |", type));
                    } else {
                        System.out.print("          |");
                    }
                }
            }
            System.out.println("\n" + separation);
        }
    }

    public void etape() {
        this.etapeCourante++; 

        for (Agent a : agents) {
            a.action();
        }

        this.detecterEtGererCombats();
        this.genererNouveauFruit();
        this.faireEvoluerRessources();
        this.verifierOnePiece();

        this.afficherSimulation();
        this.afficherStats();
        
        // Vérification de l'intégrité des données
        terrain.verifierPositionRessources();
    }

    public boolean isOnePieceTrouve() { 
        return onePieceTrouve; 
    }
}