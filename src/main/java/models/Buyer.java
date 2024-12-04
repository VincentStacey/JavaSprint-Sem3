package src.main.java.models;

public class Buyer extends User {
    public Buyer() {
        this.setRole("buyer");
    }

    @Override
    public String toString() {
        return "Buyer{" + super.toString() + "}";
    }
}

