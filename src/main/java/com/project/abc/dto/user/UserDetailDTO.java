package com.project.abc.dto.user;

import com.project.abc.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDTO {
    private String userName;

    private String fullName;

    private String email;

    private String phone;

    public static UserDetailDTO init(User user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserName(user.getUserName());
        userDetailDTO.setFullName(user.getFullName());
        userDetailDTO.setEmail(user.getEmail());
        userDetailDTO.setPhone(user.getPhone());
        return userDetailDTO;
    }
}
