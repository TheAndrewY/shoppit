package com.shoppit.controller;

import com.shoppit.dto.UserDTO;
import com.shoppit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> users = userService.getAllUsers();
        if (users == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        UserDTO fetchedUser = userService.getUser(username);
        if (fetchedUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fetchedUser);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
        UserDTO savedUser = userService.registerUser(userDTO);
        if(userDTO != null){
            return ResponseEntity.ok(savedUser);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        if(userService.deleteUser(username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        if(userDTO == null){
            return ResponseEntity.badRequest().build();
        }

        UserDTO updatedUser =  userService.updateUser(userDTO);
        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
