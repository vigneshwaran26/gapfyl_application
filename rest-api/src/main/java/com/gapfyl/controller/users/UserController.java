package com.gapfyl.controller.users;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.users.ChangePasswordRequest;
import com.gapfyl.dto.users.ForgotPasswordRequest;
import com.gapfyl.dto.users.ResetPasswordRequest;
import com.gapfyl.dto.users.UserUpdateRequest;
import com.gapfyl.exceptions.users.UserInvalidRecoveryToken;
import com.gapfyl.exceptions.users.UserNotFoundException;
import com.gapfyl.models.users.Status;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.courses.UserCourseService;
import com.gapfyl.services.users.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 15/04/21
 **/

@RestController
@RequestMapping("/api/1.0/users")
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/me")
    ResponseEntity fetchLoggedInUserProfile() {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(userService.loggedInUserProfile(userEntity));
    }

    @PostMapping("/forgot-password")
    ResponseEntity userForgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws UserNotFoundException {
        userService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PostMapping("/change-password")
    ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PostMapping("/reset-password/{recoveryToken}")
    ResponseEntity userResetPassword(@PathVariable("recoveryToken") String recoveryToken,
                                     @RequestBody ResetPasswordRequest resetPasswordRequest) throws UserInvalidRecoveryToken {
        userService.resetPassword(recoveryToken, resetPasswordRequest);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/update")
    ResponseEntity updateUser(@RequestBody UserUpdateRequest updateRequest) {
        userService.updateUser(updateRequest, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/update/status/{userId}")
    ResponseEntity updateUserStatus(@PathVariable("userId") String userId, @RequestParam("status") Status status)
            throws UserNotFoundException {
        UserEntity userEntity = getCurrentUser();
        userService.updateStatus(userId, status, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }
}
