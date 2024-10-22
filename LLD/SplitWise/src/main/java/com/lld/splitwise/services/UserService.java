import com.lld.splitwise.repository.UserRepository;
import com.lld.splitwise.models.User;
class UserService {
    UserRepository userRepository;
    public UserService() {
        this.userRepository = new UserRepository();
    }
    int addUser(String username, String email) {
        User user = new User(username, email);
        return this.userRepository.addUser(user);
    }
}