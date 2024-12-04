package src.main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.main.java.models.Product;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                products.add(product);
            }
        }
        return products;
    }

    public List<Product> getProductsByName(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name ILIKE ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setSellerId(rs.getInt("seller_id"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        String query = "SELECT * FROM products WHERE seller_id = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setSellerId(rs.getInt("seller_id"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    public List<String> getAllProductsWithSellerInfo() throws SQLException {
        String query = """
            SELECT p.id, p.name, p.price, p.quantity, u.username, u.email 
            FROM products p 
            JOIN users u ON p.seller_id = u.id
        """;
        List<String> products = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String productInfo = String.format("ID: %d, Name: %s, Price: %.2f, Quantity: %d, Seller: %s (%s)",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"),
                        rs.getString("username"), rs.getString("email"));
                products.add(productInfo);
            }
        }
        return products;
    }
}
