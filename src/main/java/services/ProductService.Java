import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void addProduct(Product product) {
        try {
            productDAO.addProduct(product);
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public List<Product> viewAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            return List.of();
        }
    }

    public List<Product> searchProductByName(String name) {
        try {
            return productDAO.getProductsByName(name);
        } catch (SQLException e) {
            System.out.println("Error searching for products: " + e.getMessage());
            return List.of();
        }
    }

    public List<Product> viewProductsBySeller(int sellerId) {
        try {
            return productDAO.getProductsBySeller(sellerId);
        } catch (SQLException e) {
            System.out.println("Error retrieving seller's products: " + e.getMessage());
            return List.of();
        }
    }

    public void updateProduct(Product product) {
        try {
            productDAO.updateProduct(product);
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    public void deleteProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    public List<String> viewAllProductsWithSellerInfo() {
        try {
            return productDAO.getAllProductsWithSellerInfo();
        } catch (SQLException e) {
            System.out.println("Error retrieving product info: " + e.getMessage());
            return List.of();
        }
    }
}
