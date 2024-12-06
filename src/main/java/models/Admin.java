package src.main.java.models;

public class Admin extends User {
    public Admin() {
        this.setRole("admin");
    }

    public Admin(String username, String password, String email) {
        super(username, password, email);
        this.setRole("admin");
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + "}";
    }
}
