package src.main.java.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int sellerId;

    public Product(String name, double price, int quantity, int sellerId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public Product(int id, String name, double price, int quantity, int sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSellerId() {
        return sellerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", sellerId=" + sellerId +
                '}';
    }
}
