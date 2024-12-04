import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> viewAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteUser(int userId) {
        try {
            userDAO.deleteUser(userId);
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
}
