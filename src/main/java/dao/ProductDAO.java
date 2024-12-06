package src.main.java.dao;

import src.main.java.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final Connection connection;

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
                products.add(mapRowToProduct(rs));
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
                    products.add(mapRowToProduct(rs));
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
                    products.add(mapRowToProduct(rs));
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

   
    public boolean removeProduct(int productId, int sellerId) throws SQLException {
        String query = "DELETE FROM products WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, sellerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Product> getPurchasedProductsByBuyer(int buyerId) throws SQLException {
        String query = """
                    SELECT p.id, p.name, p.price, p.quantity, p.seller_id
                    FROM purchases pu
                    JOIN products p ON pu.product_id = p.id
                    WHERE pu.buyer_id = ?
                """;
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            System.out.println("Executing query for buyer ID: " + buyerId); 
            stmt.setInt(1, buyerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    public void addPurchase(int buyerId, int productId, int quantity) throws SQLException {
        String sql = "INSERT INTO purchases (buyer_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, buyerId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    public List<String> getAllProductsWithSellerInfo() throws SQLException {
        String query = """
                    SELECT p.id, p.name, p.price, p.quantity, u.username AS seller_name, u.email AS seller_email
                    FROM products p
                    JOIN users u ON p.seller_id = u.id
                """;
        List<String> productDetails = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String details = String.format(
                        "Product ID: %d | Name: %s | Price: %.2f | Quantity: %d | Seller: %s (%s)",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("seller_name"),
                        rs.getString("seller_email"));
                productDetails.add(details);
            }
        }
        return productDetails;
    }

    public Product getProductById(int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }
    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getInt("seller_id"));
    }
}
