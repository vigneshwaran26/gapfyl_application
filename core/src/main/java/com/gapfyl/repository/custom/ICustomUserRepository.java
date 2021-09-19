package com.gapfyl.repository.custom;

import com.gapfyl.dto.users.ChangePasswordRequest;
import com.gapfyl.dto.users.UserUpdateRequest;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.filter.users.UserFilterCriteria;
import com.gapfyl.models.users.Status;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/05/21
 **/

public interface ICustomUserRepository {

    List<UserEntity> fetchUsers(UserFilterCriteria userFilterCriteria);

    void updateStatus(String userId, Status status, UserEntity userEntity);

    void updateProfile(UserUpdateRequest updateRequest, UserEntity userEntity);

    UserEntity updateProfileType(String userId, ProfileType newProfileType, UserEntity userEntity);

    void changePassword(ChangePasswordRequest changePasswordRequest, UserEntity userEntity);
}
