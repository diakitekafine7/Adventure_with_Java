public final class Config {
    // Attributs statiques (Constantes)
    public static final int NB_LIGNES = 5;
    public static final int NB_COLONNES = 5;
    public static final double CHANCE_APPARITION_FRUIT = 0.20;
    public static final int QUANTITE_INITIALE_FRUIT = 10;

    // Constructeur privé pour empêcher l'instanciation
    private Config() {}

    // Méthodes statiques 
    public static int genererPositionAleatoire() {
        return (int)(Math.random() * NB_LIGNES) + 1;
    }

    public static int deplacementAgent(int positionActuelle, int limiteMax) {
        int delta = (int)(Math.random() * 3) - 1; // -1, 0 ou 1
        int nouvellePos = positionActuelle + delta;

        // Sécurisation : on reste entre 1 et limiteMax
        if (nouvellePos < 1) return 1;
        if (nouvellePos > limiteMax) return limiteMax;
        
        return nouvellePos;
    }

    public static boolean testerChance(double seuil) {
        return Math.random() < seuil;
    }
}