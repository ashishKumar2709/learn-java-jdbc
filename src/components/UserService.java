package components;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDaoImpl();

    public User getUserById(int Id){
       return userDao.getUserById(Id);
    }
    public void addUser(User user){
        userDao.saveUser(user);
    }
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
    public void updateUser(User user){
        userDao.updateUser(user);
    }
    public void deleteUser(User user){
        userDao.deleteUser(user);
    }
}
