package components;

import java.util.List;

public interface UserDao {
    User getUserById(int user_id);
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
