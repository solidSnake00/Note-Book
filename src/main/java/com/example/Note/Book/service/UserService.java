package com.example.Note.Book.service;

import com.example.Note.Book.model.User;
import com.example.Note.Book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        Optional<User> optionalUser=userRepository.findById(id);
        return optionalUser.get();
    }

    public User getUserByUsername(String userName){
        Optional<User> optionalUser=userRepository.findUserByUsername(userName);
        return optionalUser.get();
    }

    public void deleteUser(long id){
        User user=this.getUserById(id);
        userRepository.delete(user);
    }

   /* public void deleteUser(String userName){
        User user=this.getUserByUsername(userName);
        userRepository.delete(user);
    }*/

    public Boolean createUser(User user){
        List<User> userList=this.getAllUsers();
        user.setCreationDate(LocalDate.now());
        for (User value : userList) {
            if (user.getUsername().equals(value.getUsername())) {
                return false;
            }
        }

        userRepository.save(user);
        return true;


    }

    public Boolean usernameExist(String username){
        List<User> users=this.getAllUsers();
        for (User value : users){
            if(value.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
