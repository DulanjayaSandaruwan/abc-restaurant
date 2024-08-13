package com.project.abc.service.user;

import com.project.abc.commons.Check;
import com.project.abc.commons.Hash;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.UnauthorizeException;
import com.project.abc.commons.exceptions.http.UserNotFoundException;
import com.project.abc.commons.exceptions.user.UserExType;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.model.user.User;
import com.project.abc.repository.user.UserRepository;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            throw  new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
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

//    public User updateUser(UserUpdateDTO updateDTO, String userId) {
//        log.info("updated user id {}", userId);
//        Optional<User> userOptional = userRepository.findById(userId);
//        Check.throwIfEmpty(userOptional, new UserNotFoundException("user not found"));
//        User user = userOptional.get();
//        Optional<User> userName = userRepository.findFirstByUserName(updateDTO.getUserName());
//        if (userName.isPresent()) {
//            if (!updateDTO.getUserName().equals(user.getUserName())) {
//                throw new BadRequestException("username already exist", UserExType.USERNAME_ALREADY_EXIST);
//            }
//        }
//        user = User.initUpdate(updateDTO,user);
//        user = userRepository.save(user);
//        if(!updateDTO.getIntrest().isEmpty()){
//            log.info("Add Interest. userid = {} ", userId);
//            for (String interestIds : updateDTO.getIntrest()) {
//                UserInterest userInterest = new UserInterest();
//                userInterest.setId(UUID.randomUUID().toString());
//                userInterest.setInterestId(interestIds);
//                userInterest.setUser(user);
//                interestRepository.save(userInterest);
//            }
//        }
//        return user;
//    }
}
