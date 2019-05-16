package com.example.demo.security;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.UserMapper;
import com.example.demo.application.model.user.Role;
import com.example.demo.application.repository.RoleRepo;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.application.model.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Luke on 24.10.2018.
 */
@Service(value = "userService")
public class UsernamePasswordDetailsService implements UserService, UserDetailsService {

    private UserRepository userDao;
    private BCryptPasswordEncoder bcryptEncoder;
    private UserMapper userMapper;
    private RoleRepo roleRepo;

    public UsernamePasswordDetailsService(UserRepository userDao, BCryptPasswordEncoder bcryptEncoder, UserMapper userMapper, RoleRepo roleRepo) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
        this.userMapper = userMapper;
        this.roleRepo = roleRepo;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userDao.findAll().stream().forEach(user -> {
            list.add(userMapper.userToUserDto(user));
        });
        return list;
    }

    public void delete(Long id) {
        userDao.delete(findById(id));
    }

    public User findOne(String username) {
        return userDao.findByUsername(username).get();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    public UserDTO update(UserDTO UserDTO) {
        User user = findById(UserDTO.getId());
        if (user != null) {
            BeanUtils.copyProperties(UserDTO, user, "password");
            userDao.save(user);
        }
        return UserDTO;
    }

    public User saveNewUser(UserDTO user) {
        User newUser = userMapper.userDtoToUser(user);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singletonList(roleRepo.findRoleByRoleName("ROLE_USER")));
        return userDao.save(newUser);
    }

    @Override
    public User save(UserDTO user) {
        //todo
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            role.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(authorities::add);
        }

        return authorities;
    }
}
