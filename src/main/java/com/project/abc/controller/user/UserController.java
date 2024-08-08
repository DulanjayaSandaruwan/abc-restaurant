package com.project.abc.controller.user;

import com.project.abc.commons.ServiceDtoService;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.model.user.User;
import com.project.abc.security.Session;
import com.project.abc.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceDtoService serviceDtoService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO.validate();
        User user = userService.createUser(userDTO);
        UserDTO createUserDTO = UserDTO.init(user);
        return ResponseEntity.ok(createUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDto){
        User user = userService.login(userDto);
        Session.setUser(user);
        UserDTO loginUserDTO = UserDTO.init(user);
        return ResponseEntity.ok(loginUserDTO);
    }

    @GetMapping()
    public ResponseEntity<UserDTO> userDetails(){
        User user = userService.getUserById(Session.getUser().getId());
        UserDTO userDTO = UserDTO.init(user);
        return ResponseEntity.ok(userDTO);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
//        User user = userService.getUserById(id);
//        UserDTO userDTO = UserDTO.init(user);
//        userDTO.setFollowingCount(followService.getTotalFollowingCount(user));
//        userDTO.setFollowersCount(followService.getTotalFollowersCount(user));
//        userDTO = serviceDtoService.addImageLink(userDTO);
//        return ResponseEntity.ok(userDTO);
//    }

//    @PutMapping("/update")
//    public ResponseEntity<UserDTO> update(@RequestBody UserUpdateDTO userUpdateDTO) {
//        userUpdateDTO.validate();
//        String userId=Session.getUser().getId();
//        User user = userService.updateUser(userUpdateDTO,userId);
//        UserDTO userDTO = UserDTO.init(user);
//        userDTO = serviceDtoService.addImageLink(userDTO);
//        return ResponseEntity.ok(userDTO);
//    }
}
