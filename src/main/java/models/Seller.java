package src.main.java.models;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<Product> products;

    public Seller() {
        this.setRole("seller");
        this.products = new ArrayList<>();
    }

    public Seller(String username, String password, String email) {
        super(username, password, email);
        this.setRole("seller");
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(int productId) {
        return products.removeIf(product -> product.getId() == productId);
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", products=" + products +
                '}';
    }
}
