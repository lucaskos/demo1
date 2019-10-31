package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.UserDTO;
import com.luke.filmdb.application.model.user.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User saveUser(RegisterDTO user);

    List<UserDTO> findAll();

    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDTO update(UserDTO userDto);

    Collection<String> findLoggedUserRoles();

}