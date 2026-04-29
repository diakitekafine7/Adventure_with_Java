public class FruitDuDemon extends Ressource {

    public FruitDuDemon() {
        // Appelle Ressource(String type, int quantite)
        super("Fruit", 10); 
    }

    // Constructeur de copie
    public FruitDuDemon(FruitDuDemon autre) {
        // On récupère le type et la quantité de l'objet à copier
        super(autre.type, autre.getQuantite()); 

        
        // Si le fruit original était sur le terrain, on peut copier sa position
        if (autre.getLigne() != -1) {
            this.setPosition(autre.getLigne(), autre.getColonne());
        }
    }

    // Méthode pour faire évoluer la ressource au fil du temps 
    public void murer() {
        this.setQuantite(this.getQuantite() + 1);
    }
}