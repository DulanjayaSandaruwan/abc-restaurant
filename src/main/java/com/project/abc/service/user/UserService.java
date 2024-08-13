package com.project.abc.service.user;

import com.project.abc.commons.Check;
import com.project.abc.commons.Hash;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.UnauthorizeException;
import com.project.abc.commons.exceptions.http.UserNotFoundException;
import com.project.abc.commons.exceptions.user.UserExType;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.dto.user.UserUpdateDTO;
import com.project.abc.model.user.User;
import com.project.abc.repository.user.UserRepository;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO dto) {
        log.info("create user email {}", dto.getEmail());
        User user = User.init(dto);
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new BadRequestException("phone already exist", UserExType.PHONE_ALREADY_EXIST);
        }
        user.setPassword(Hash.make(dto.getPassword()));
        user.setRole(dto.getRole());
        user = userRepository.save(user);
        return user;
    }

    public User login(UserDTO userDto) {
        log.info("login user email {}", userDto.getEmail());
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isEmpty()) {
            throw new UnauthorizeException("Invalid Credentials");
        }
        if (!Hash.match(userDto.getPassword(), user.get().getPassword())) {
            throw new UnauthorizeException("Invalid Credentials");
        }
        return user.get();
    }

    public Optional<User> findUserById(String userId) {
        Optional<User> result = this.userRepository.findById(userId);
        log.debug("get user by id = {}, is available = {}", userId, result.isPresent());
        return result;
    }

    public User getUserById(String userId){
        log.info("Get user by id = {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        Check.throwIfEmpty(userOptional, new UserNotFoundException("User not found with Id : " + userId));
        User user = userOptional.get();
        return user;
    }

    public User updateUser(UserUpdateDTO updateDTO, String userId) {
        log.info("updated user id {}", userId);
        User user = this.getUserById(userId);
        user.setFullName(updateDTO.getFullName());
        user.setEmail(updateDTO.getEmail());
        user.setPhone(updateDTO.getPhone());
        user = userRepository.save(user);
        return user;
    }
}
