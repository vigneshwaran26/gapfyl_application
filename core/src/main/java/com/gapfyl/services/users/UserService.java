package com.gapfyl.services.users;

import com.gapfyl.dto.users.ChangePasswordRequest;
import com.gapfyl.dto.users.ForgotPasswordRequest;
import com.gapfyl.dto.users.ResetPasswordRequest;
import com.gapfyl.dto.users.UserResponse;
import com.gapfyl.dto.users.UserUpdateRequest;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.exceptions.users.UserInvalidRecoveryToken;
import com.gapfyl.exceptions.users.UserNotFoundException;
import com.gapfyl.filter.users.UserFilterCriteria;
import com.gapfyl.models.users.Status;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 15/04/21
 **/

public interface UserService {

    UserResponse entityToResponse(UserEntity userEntity);

    UserResponse loggedInUserProfile(UserEntity userEntity);

    UserEntity fetchUserByEmail(String email);

    UserEntity fetchUserById(String userId);

    void changePassword(ChangePasswordRequest changePasswordRequest, UserEntity userEntity);

    List<UserEntity> fetchUsersWithFilters(UserFilterCriteria userFilterCriteria);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws UserNotFoundException;

    void resetPassword(String recoveryToken, ResetPasswordRequest resetPasswordRequest) throws UserInvalidRecoveryToken;

    void updateStatus(String userId, Status status, UserEntity userEntity) throws UserNotFoundException;

    void updateRoles(String email, List<String> roles, List<String> removeRoles, UserEntity userEntity) throws NotFoundException;

    void updateUser(UserUpdateRequest updateRequest, UserEntity userEntity);

    void changeProfileType(String userId, ProfileType changeProfileType, UserEntity userEntity);

}
