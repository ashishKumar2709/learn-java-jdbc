import components.DatabaseUtil;
import components.User;
import components.UserService;
public class App {
    public static void main(String[] args) throws Exception {
        // String query = "SELECT * FROM table_one";
        // String col_one_data = "row_eight";
        // int col_two_data = 8888;
        // String query1 = "INSERT INTO table_one(col_one, col_two) VALUES(?,?)";
        // DatabaseUtil.executeQuery(query);
        // DatabaseUtil.updateQuery(query1, col_one_data, col_two_data);
        // DatabaseUtil.executeQuery(query);

        UserService service = new UserService();

        //fetch user by id
        User user2 = service.getUserById(2);
        if(user2 != null){
            System.out.println("User id: "+user2.getUserId()+", Name: "+ user2.getUserName());
        }else{
            System.out.println("No user with metntioned Id");
        }

        //update existing user details
        User existingUser = service.getUserById(6);
        existingUser.setLastName("four");
        existingUser.setEmail("userfour@email.com");
        service.updateUser(existingUser);

        //delete user
        User userToDelete = service.getUserById(6);
        service.deleteUser(userToDelete);

        //Save new user
        User newUser = new User("user", "four", "userfour@email.com");
        service.addUser(newUser);

        //get all users
        service.getAllUsers().forEach(user -> System.out.println(user.getUserName()+": "+user.getEmail()));
    }
}
