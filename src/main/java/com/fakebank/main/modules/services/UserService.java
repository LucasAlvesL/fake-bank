package com.fakebank.main.modules.services;

import com.fakebank.main.modules.dto.User.UserRequestDTO;
import com.fakebank.main.modules.entities.UserEntity;
import com.fakebank.main.modules.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity convertToEntity(UserRequestDTO userRequestDTO) {
        var userEntity = new UserEntity();
        BeanUtils.copyProperties(userRequestDTO, userEntity);
        return userEntity;
    }

    public UserEntity createUser(UserRequestDTO userRequestDTO) {
        userRepository.findByEmail(userRequestDTO.getEmail()).ifPresent(e -> {
            throw new RuntimeException("Some user already exists with this email");
        });
        userRepository.findByCpf(userRequestDTO.getCpf()).ifPresent(e -> {
            throw new RuntimeException("Some user already exists with this CPF");
        });
        UserEntity userEntity = convertToEntity(userRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        return userRepository.save(userEntity);
    }
}
