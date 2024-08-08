package com.project.abc.dto.user;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.user.User;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserUpdateDTO extends BaseRequest {
    @Size(max = 40, min = 1, message = "Full name length should be more than 1 and less than 40")
    private String fullName;

    private String email;

    @Size(max = 15, min = 3, message = "phone length should be more than 3 and less than 15")
    private String phone;

    @Size(max = 30, min = 1, message = "Username length should be more than 1 and less than 40")
    private String userName;

    private User.UserStatus status;

    private User.UserRole role;
}
