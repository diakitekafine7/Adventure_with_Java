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
        // Le terrain est un tableau à 2 dimensions 
        this.terrain = new Terrain(Config.NB_LIGNES, Config.NB_COLONNES);
        this.agents = new ArrayList<>();
        this.initialiser();
        
        // Initialisation des stats pour l'étape 0
        this.faireEvoluerRessources(); 
    }

    //Methode pour avoir l'instance 
    public static Simulation getInstance() throws SimulationException {
        if (instance == null) {
            instance = new Simulation();
        }
        return instance;
    }

    // Initialisation du terrain avec des ressources et des agents 
    private void initialiser() throws SimulationException  {
        // Ajout des combattants (Equipages et Unités Marine)
        agents.add(new ChapeauxDePaille(1, 1, terrain));
        agents.add(new MarineN55(1, 2, terrain)); 
        
        // Ajout des Ressources 
        // Ressource évolutive 
        boolean ok1 = terrain.setCase(2, 2, new FruitDuDemon());
        if (ok1 == false) {
            throw new SimulationException("Impossible de placer le Fruit du Démon en (2,2) !");
        }
        // Ressource stable
        boolean ok2 = terrain.setCase(4, 1, new OnePiece(500));
        if (ok2 == false) {
            throw new SimulationException("Impossible de placer le One Piece en (4,1) !");
        }
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
        if (a1 instanceof Combattant && a2 instanceof Combattant) {
            System.out.println("\n COMBAT en (" + a1.getLigne() + "," + a1.getColonne() + ")"); 
            
            Agent vainqueur;
            Agent perdant;

            // Détermination du gagnant
            if (Math.random() > 0.5) {
                vainqueur = a1;
                perdant = a2;
            } else {
                vainqueur = a2;
                perdant = a1;
            }

            // Le vainqueur crie victoire
            ((Combattant)vainqueur).crierVictoire();

            // Si le perdant est notre équipage de pirates, perte de Fruit
            if (perdant instanceof EquipagePirate) {
                EquipagePirate pirate = (EquipagePirate) perdant;
                
                // Les fruits sont perdus et on les copies 
                for (FruitDuDemon f : pirate.getFruitsManges()) {
                    // Utilisation du constructeur de copie pour recréer le fruit au sol
                    FruitDuDemon fruitTombe = new FruitDuDemon(f);
                    
                    // On remet le fruit sur le terrain (sur la case actuelle du combat)
                    this.terrain.setCase(perdant.getLigne(), perdant.getColonne(), fruitTombe);
                    System.out.println(" ! Un fruit est tombé au sol pendant la fuite !");
                }
                
                // On vide le sac du pirate puisqu'il a tout perdu
                pirate.getFruitsManges().clear();
            }

            // Le perdant s'enfuit en utilisant la classe statique Config
            perdant.seDeplacer(Config.genererPositionAleatoire(), Config.genererPositionAleatoire()); 
        }
    }

    private void faireEvoluerRessources() {
        int count = 0;
        for (int l = 1; l <= Config.NB_LIGNES; l++) {
            for (int c = 1; c <= Config.NB_COLONNES; c++) {
                Ressource r = terrain.getCase(l, c);
                if (r instanceof FruitDuDemon) {
                    ((FruitDuDemon) r).murer(); // Appel de la méthode d'évolution
                    count++;
                }
            }
        }
        this.nbFruits = count; // Mise à jour stat
    }

    private void genererNouveauFruit() {
        // Utilisation de la méthode statique de Config pour tester la chance
        if (Config.testerChance(Config.CHANCE_APPARITION_FRUIT)) { 
            // Utilisation de la méthode statique de Config pour les positions
            int l = Config.genererPositionAleatoire();
            int c = Config.genererPositionAleatoire();
            
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

        // Génération de nouveaux fruits AVANT de compter (pour les stats temps réel)
        this.genererNouveauFruit();

        // Évolution et comptage des ressources
        this.faireEvoluerRessources();

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