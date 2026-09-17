public class Position {
    private Stock stock;
    private int quantity;

    public Position(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        quantity = quantity + amount;
    }

    public void removeQuantity(int amount) {
        quantity = quantity - amount;
    }

    public double getValue() {
        return stock.getPrice() * quantity;
    }
}
