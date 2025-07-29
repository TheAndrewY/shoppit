package com.shoppit.service;

import com.shoppit.dto.UserDTO;
import com.shoppit.model.User;
import com.shoppit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    //TODO throw exceptions is operations fail which will then be handled by controller

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserDTO registerUser(UserDTO user){
        //If user already exists, throw exception
        if(user == null){
            return null;
        }
        User newUser = modelMapper.map(user, User.class);
        newUser.setCreatedOn(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);
    }

    public UserDTO getUser(String username){
        if(username == null) return null;
        UserDTO fetchedUser;
        if(userRepository.findById(username).isPresent()){
            fetchedUser = modelMapper.map(userRepository.findById(username), UserDTO.class);
        }else{
            fetchedUser = null;
        }
        return fetchedUser;
    }
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    public boolean deleteUser(String username){
        if(username == null) return false;
        if(userRepository.findById(username).isPresent()){
            userRepository.deleteById(username);
            return true;
        }
        return false;
    }

    public UserDTO updateUser(UserDTO userDTO){
        if(userDTO == null) return null;
        User updatedUser = modelMapper.map(userDTO, User.class);
        if(userRepository.findById(userDTO.getUsername()).isPresent()){
            userRepository.save(updatedUser);
            return modelMapper.map(userDTO, UserDTO.class);
        }else{
            return null;
        }
    }

}
