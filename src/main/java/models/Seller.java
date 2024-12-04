package src.main.java.models;

public class Seller extends User {
    public Seller() {
        this.setRole("seller");
    }

    @Override
    public String toString() {
        return "Seller{" + super.toString() + "}";
    }
}

