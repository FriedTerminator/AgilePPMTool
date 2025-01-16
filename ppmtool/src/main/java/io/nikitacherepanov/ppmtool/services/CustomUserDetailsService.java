package io.nikitacherepanov.ppmtool.services;

import io.nikitacherepanov.ppmtool.domain.User;
import io.nikitacherepanov.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    @Transactional
    public User loadUserById(Long id) {
        User user = userRepository.getById(id);
        logger.log(Level.INFO, "user logged {0}", user.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }

        return user;
    }
}
