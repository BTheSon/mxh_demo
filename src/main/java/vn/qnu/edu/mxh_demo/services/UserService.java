package vn.qnu.edu.mxh_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.qnu.edu.mxh_demo.models.User;
import vn.qnu.edu.mxh_demo.models.UserRepo;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public boolean isExistByUsername(String username) {
        var existUser = userRepo.findByUsername(username);
        return existUser != null;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean register(String username, String password) {
        if (isExistByUsername(username)) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");
        user.setCreated_at(LocalDate.now());
        return userRepo.save(user) > 0;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
