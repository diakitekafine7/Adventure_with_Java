public class FruitDuDemon extends Ressource {
    public FruitDuDemon() {
        super("Fruit", 10);
    }

    // Méthode pour faire évoluer la ressource au fil du temps 
    public void murer() {
        this.setQuantite(this.getQuantite() + 1);
    }
}