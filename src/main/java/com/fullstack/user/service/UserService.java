package com.fullstack.user.service;

import com.fullstack.user.dto.UserDTO;
import com.fullstack.user.entity.User;
import com.fullstack.user.mapper.UserMapper;
import com.fullstack.user.repo.UserRepo;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserDTO getUser(UUID uuid) {
        Optional<User> user = userRepo.findById(uuid);
        return user.isPresent() ?
            UserMapper.INSTANCE.userToUserDTO(user.get()): null;

    }

    public UUID addUser(UserDTO userDTO) {
        User user = userRepo.saveAndFlush(UserMapper.INSTANCE.userDTOToUser(userDTO));
        return user.getId();
    }

    public List<UserDTO> fetchAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> allUsers = users.stream().map(UserMapper.INSTANCE::userToUserDTO).collect(Collectors.toList());
        return allUsers;
    }
}
