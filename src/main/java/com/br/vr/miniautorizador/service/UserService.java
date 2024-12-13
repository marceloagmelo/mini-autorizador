package com.br.vr.miniautorizador.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.br.vr.miniautorizador.dto.UserDTO;
import com.br.vr.miniautorizador.entity.User;
import com.br.vr.miniautorizador.model.AuthenticatedUser;
import com.br.vr.miniautorizador.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserDTO userDTO) {
        System.out.println("Request body: " + userDTO);

        User u = new User();
        u.setUsername(userDTO.getUsername());
        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        return new AuthenticatedUser(u);
    }
}
