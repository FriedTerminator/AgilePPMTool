package io.nikitacherepanov.ppmtool.services;

import io.nikitacherepanov.ppmtool.domain.User;
import io.nikitacherepanov.ppmtool.exceptions.UsernameException;
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

            //make sure that password and confirmPassword match
            //We don't persist or show the confirmPassword
        } catch(Exception e) {
            throw new UsernameException("Username " + newUser.getUsername() + " already exists");
        }
        return userRepository.save(newUser);
    }
}
