package com.gapfyl.services.users;

import com.gapfyl.dto.users.AccountResponse;
import com.gapfyl.dto.users.ChangePasswordRequest;
import com.gapfyl.dto.users.ForgotPasswordRequest;
import com.gapfyl.dto.users.ResetPasswordRequest;
import com.gapfyl.dto.users.UserUpdateRequest;
import com.gapfyl.dto.users.UserResponse;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.exceptions.users.UserInvalidRecoveryToken;
import com.gapfyl.exceptions.users.UserNotFoundException;
import com.gapfyl.filter.users.UserFilterCriteria;
import com.gapfyl.models.users.AccountEntity;
import com.gapfyl.models.users.Status;
import com.gapfyl.repository.UserCourseRepository;
import com.gapfyl.repository.UserRepository;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.courses.CourseService;
import com.gapfyl.services.email.EmailService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 15/04/21
 **/

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    CourseService courseService;

    @Value("${client.origin:no-bucket}")
    private String clientOrigin;

    @Override
    public UserResponse entityToResponse(UserEntity userEntity) {
        AccountResponse accountResponse = new AccountResponse();
        if (userEntity.getAccount() != null) {
            AccountEntity accountEntity = userEntity.getAccount();
            accountResponse.setId(accountEntity.getId());
            accountResponse.setName(accountEntity.getName());
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setName(userEntity.getName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setProfileType(userEntity.getProfileType());
        userResponse.setAccount(accountResponse);
        userResponse.setMobile(userEntity.getMobile());
        userResponse.setRoles(userEntity.getRoles());
        userResponse.setActivated(userEntity.getActivated());
        return userResponse;
    }

    @Override
    public UserResponse loggedInUserProfile(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        if (userEntity.getAccount() != null) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setId(userEntity.getAccount().getId());
            accountResponse.setName(userEntity.getAccount().getName());
            userResponse.setAccount(accountResponse);
        }

        userResponse.setUsername(userEntity.getUsername());
        userResponse.setName(userEntity.getName());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setMobile(userEntity.getMobile());
        userResponse.setProfileImageUrl(userEntity.getProfileImageUrl());
        userResponse.setProfileType(userEntity.getProfileType());
        userResponse.setRoles(userEntity.getRoles());
        userResponse.setActivated(userEntity.isActivated());
        return userResponse;
    }

    @Override
    public UserEntity fetchUserByEmail(String email) {
       return userRepository.findOneByEmail(email);
    }

    @Override
    public UserEntity fetchUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<UserEntity> fetchUsersWithFilters(UserFilterCriteria userFilterCriteria) {
        return userRepository.fetchUsers(userFilterCriteria);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, UserEntity userEntity) {
        log.debug("user {} [{}] changing password");
        userRepository.changePassword(changePasswordRequest, userEntity);
        log.debug("user {} [{}] changed password");
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findOneByEmail(forgotPasswordRequest.getEmail());
        if (userEntity == null) {
            log.error("user not found with email {}", forgotPasswordRequest.getEmail());
            throw new UserNotFoundException(forgotPasswordRequest.getEmail());
        }

        String recoveryToken = Common.generateToken(forgotPasswordRequest.getEmail());
        userEntity.setRecoveryToken(recoveryToken);
        userEntity.setModifiedDate(Common.getCurrentUTCDate());
        userRepository.save(userEntity);

        String resetPasswordLink = clientOrigin + "/reset-password/" + recoveryToken;
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("name", userEntity.getName());
        emailModel.put("reset_password_link", resetPasswordLink);
        emailService.sendResetPassword(forgotPasswordRequest.getEmail(), emailModel);
    }

    @Override
    public void resetPassword(String recoveryToken, ResetPasswordRequest resetPasswordRequest) throws UserInvalidRecoveryToken {
        log.info("fetch user information with recovery token {}", recoveryToken);
        UserEntity userEntity = userRepository.findOneByRecoveryToken(recoveryToken);
        if (userEntity == null) {
            log.error("user not found with recovery token {}", recoveryToken);
            throw new UserInvalidRecoveryToken(recoveryToken);
        }

        log.info("resetting user {} [{}] password", userEntity.getName(), userEntity.getId());
        userEntity.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userEntity.setModifiedDate(Common.getCurrentUTCDate());
        userRepository.save(userEntity);
        log.info("reset user {} [{}] password done", userEntity.getName(), userEntity.getId());
    }

    @Override
    public void updateStatus(String userId, Status status, UserEntity userEntity) throws UserNotFoundException {
        log.info("user {} [{}] updating user {} status as {}", userEntity.getName(), userEntity.getId(), userId, status);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.error("user {} not found", userId);
            throw new UserNotFoundException(userId);
        }

        userRepository.updateStatus(userId, status, userEntity);
        log.info("user {} [{}] updated user {} status {}", userEntity.getName(), userEntity.getId(), userId, status);
    }

    @Override
    public void updateRoles(String email, List<String> roles, List<String> removeRoles, UserEntity userEntity)
            throws NotFoundException {

        log.info("updating user {} roles {}", email, roles.toString());
        UserEntity user = fetchUserByEmail(email);
        if (user == null) {
            log.error("user {} not found", email);
            throw new NotFoundException("user.not_found", new Object[] { email });
        }

        List<String> userRoles = user.getRoles();
        if (!Common.isNullOrEmpty(removeRoles)) {
            userRoles = userRoles.stream()
                    .filter(item -> !removeRoles.contains(item))
                    .collect(Collectors.toList());
        }

        userRoles.addAll(roles);
        user.setRoles(userRoles);
        user.setModifiedBy(userEntity);
        user.setModifiedDate(Common.getCurrentUTCDate());
        userRepository.save(user);
        log.info("updated user {} roles", email);
    }

    @Override
    public void updateUser(UserUpdateRequest updateRequest, UserEntity userEntity) {
        log.info("user {} [{}] updating personal settings", userEntity.getName(), userEntity.getId());
        userRepository.updateProfile(updateRequest, userEntity);
        log.info("user {} [{}] updated personal settings", userEntity.getName(), userEntity.getId());
    }

    @Override
    public void changeProfileType(String userId, ProfileType changeProfileType, UserEntity userEntity) {
        userRepository.updateProfileType(userId, changeProfileType, userEntity);
    }
}
