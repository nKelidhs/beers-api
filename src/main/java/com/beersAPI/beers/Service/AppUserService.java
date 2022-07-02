package com.beersAPI.beers.Service;

import com.beersAPI.beers.Model.AppUser;
import com.beersAPI.beers.Repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
    }

    public AppUser create(AppUser user) {
        if (!isNameValid(user.getName())) throw new IllegalArgumentException("Name cannot be null or empty");
        if (!isUsernameValid(user.getUsername())) throw new IllegalArgumentException("Username cannot be null or empty");
        if (!isPasswordValid(user.getPassword())) throw new IllegalArgumentException("Password cannot be null or empty");

        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

    //region Validations

    private Boolean isNameValid(String name) {
        if (name == null) return false;
        if (name.isEmpty()) return false;
        return true;
    }

    private Boolean isUsernameValid(String username) {
        if (username == null) return false;
        if (username.isEmpty()) return false;
        return true;
    }

    private Boolean isPasswordValid(String password) {
        if (password == null) return false;
        if (password.isEmpty()) return false;
        return true;
    }

    //endregion
}
