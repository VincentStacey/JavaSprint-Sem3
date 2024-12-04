package src.main.java.models;

public class Admin extends User {
    public Admin() {
        this.setRole("admin");
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + "}";
    }
}

