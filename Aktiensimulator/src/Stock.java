public class Stock {
   private String name;
   private String ticker;
   private double price;
   
   public Stock(String name, String ticker, double price) {
       this.name = name;
       this.ticker = ticker;
       this.price = price;
   }

    public String getName() {
         return name;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override

    //Verstehe nicht ganz, warum man hier ein format...
    public String toString() {
        return ticker + " - " + name + ";" + String.format("%.2f CHF", price);
    }
}
