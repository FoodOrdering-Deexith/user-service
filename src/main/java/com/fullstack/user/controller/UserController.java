package com.fullstack.user.controller;

import com.fullstack.user.dto.UserDTO;
import com.fullstack.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUser(@PathVariable UUID uuid) {
        UserDTO user = userService.getUser(uuid);
        ResponseEntity responseEntity = null;
        if (Objects.nonNull(user))
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        else
            responseEntity = new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            Map<String, UUID> response = new HashMap<>();
            response.put("id", userService.addUser(userDTO));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
