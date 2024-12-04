package src.main.java;

import src.main.java.dao.ProductDAO;
import src.main.java.dao.UserDAO;
import src.main.java.models.Admin;
import src.main.java.models.Buyer;
import src.main.java.models.Product;
import src.main.java.models.Seller;
import src.main.java.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "Hockey2550!");

            UserDAO userDAO = new UserDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nWelcome to the E-Commerce Platform");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> registerUser(scanner, userDAO);
                    case 2 -> loginUser(scanner, userDAO, productDAO);
                    case 3 -> {
                        System.out.println("Exiting the application. Goodbye!");
                        scanner.close();
                        connection.close();
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found. Ensure it is included in your dependencies.");
        }
    }

    private static void registerUser(Scanner scanner, UserDAO userDAO) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = MyBCrypt.hashpw(scanner.nextLine());
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter role (buyer/seller/admin): ");
            String role = scanner.nextLine().toLowerCase();

            User user;
            switch (role) {
                case "buyer" -> user = new Buyer();
                case "seller" -> user = new Seller();
                case "admin" -> user = new Admin();
                default -> {
                    System.out.println("Invalid role. Registration aborted.");
                    return;
                }
            }

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            userDAO.registerUser(user);
            System.out.println("Registration successful!");
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    private static void loginUser(Scanner scanner, UserDAO userDAO, ProductDAO productDAO) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userDAO.getUserByUsername(username);

            if (user != null && MyBCrypt.checkpw(password, user.getPassword())) {
                System.out.println("Login successful! Welcome, " + user.getUsername());
                switch (user.getRole()) {
                    case "buyer" -> buyerMenu(scanner, productDAO);
                    case "seller" -> sellerMenu(scanner, productDAO, user.getId());
                    case "admin" -> adminMenu(scanner, userDAO, productDAO);
                    default -> System.out.println("Invalid role detected. Access denied.");
                }
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
    }

    private static void buyerMenu(Scanner scanner, ProductDAO productDAO) throws SQLException {
        while (true) {
            System.out.println("\nBuyer Menu");
            System.out.println("1. Browse Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    List<Product> products = productDAO.getAllProducts();
                    products.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Enter product name to search: ");
                    String name = scanner.nextLine();
                    List<Product> products = productDAO.getProductsByName(name);
                    products.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void sellerMenu(Scanner scanner, ProductDAO productDAO, int sellerId) throws SQLException {
        
    }

    private static void adminMenu(Scanner scanner, UserDAO userDAO, ProductDAO productDAO) throws SQLException {
        
    }
}
