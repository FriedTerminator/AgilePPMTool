package io.nikitacherepanov.ppmtool.services;

import io.nikitacherepanov.ppmtool.domain.User;
import io.nikitacherepanov.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.nikitacherepanov.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            //make sure that password and confirmPassword match
            //We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        } catch(Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
    }
}
