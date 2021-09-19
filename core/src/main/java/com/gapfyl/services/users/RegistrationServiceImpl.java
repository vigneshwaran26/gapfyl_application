package com.gapfyl.services.users;

import com.gapfyl.dto.users.RegistrationRequest;
import com.gapfyl.enums.common.LookupFilterCode;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;
import com.gapfyl.exceptions.users.UserNotFoundException;
import com.gapfyl.models.users.AccountEntity;
import com.gapfyl.models.users.Status;
import com.gapfyl.models.users.UserRole;
import com.gapfyl.repository.AccountRepository;
import com.gapfyl.repository.UserRepository;
import com.gapfyl.models.users.RegistrationEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.RegistrationRepository;
import com.gapfyl.services.email.EmailService;
import com.gapfyl.services.lookup.LookupService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vignesh
 * Created on 13/04/21
 **/

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    LookupService lookupService;

    @Value("${client.origin:no-client-origin}")
    private String clientOrigin;

    private AccountEntity createAccount(String name) throws AccountConflictException {
        log.info("creating user account {}", name);
        AccountEntity existingAccount = accountRepository.findOneByName(name);
        if (existingAccount != null) {
            log.error("account with name {} already exists in system", name);
            throw new AccountConflictException(name);
        }

        AccountEntity account = new AccountEntity();
        account.setName(name);
        account = accountRepository.save(account);
        log.info("created user account {} {}", name, account.getId());
        return account;
    }

    private void createUser(RegistrationRequest registrationRequest, AccountEntity account) {
        log.info("creating new user {}", registrationRequest.getEmail());
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);
        userEntity.setActivated(false);
        userEntity.setStatus(Status.registered);
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userEntity.setCreatedDate(Common.getCurrentUTCDate());
        userEntity.setMobile(registrationRequest.getMobile());
        userEntity.setModifiedDate(Common.getCurrentUTCDate());
        userEntity = userRepository.save(userEntity);
        log.info("created new user {} {}", userEntity.getEmail(), userEntity.getId());
    }

    @Override
    public void registerUser(RegistrationRequest registrationRequest) throws UserConflictException, AccountConflictException {

        log.debug("register user with email {}", registrationRequest.getEmail());
        RegistrationEntity exRegistration = registrationRepository.findOneByEmail(registrationRequest.getEmail());
        if (exRegistration != null) {
            log.error("user {} already registered, activated status {}", registrationRequest.getEmail(),
                    exRegistration.isActivated());
            throw new UserConflictException(exRegistration.getEmail());
        }

        String activationToken = Common.generateToken(registrationRequest.getEmail());
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setEmail(registrationRequest.getEmail());
        registrationEntity.setActivationToken(activationToken);
        registrationEntity.setActivated(false);

        registrationEntity.setCreatedDate(Common.getCurrentUTCDate());
        registrationRepository.save(registrationEntity);
        log.debug("user registered with email {}", registrationRequest.getEmail());

        createUser(registrationRequest, createAccount(registrationRequest.getEmail()));

        /** sending activation email to user to activate registered user account */
        String validationLink = clientOrigin + "/activate/user/" + activationToken;
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("activation_link", validationLink);
        emailService.sendUserOnBoarding(registrationRequest.getEmail(), emailModel);
    }

    @Override
    public void activateUser(String activationToken) throws UserNotFoundException, UserConflictException {

        log.info("activating user with activation token {}", activationToken);
        RegistrationEntity registrationEntity = registrationRepository.findOneByActivationToken(activationToken);
        if (registrationEntity == null) {
            log.error("registration request is not found with activation token {}", activationToken);
            throw new UserNotFoundException(activationToken);
        }

        if (registrationEntity.isActivated()) {
            log.error("found user as activated with activation token {}", activationToken);
            throw new UserConflictException(activationToken);
        }

        log.debug("updating registration entity table to set user {} as activated", registrationEntity.getEmail());
        registrationEntity.setActivated(true);
        registrationEntity.setModifiedDate(Common.getCurrentUTCDate());
        registrationRepository.save(registrationEntity);
        log.debug("updated user {} as activated", registrationEntity.getEmail());

        UserEntity user = userRepository.findOneByEmail(registrationEntity.getEmail());
        user.setActivated(true);
        user.setStatus(Status.active);
        user.setRoles(Arrays.asList(UserRole.ROLE_USER.name(), UserRole.ROLE_LEARNER.name()));
        user.setProfileType(ProfileType.learner);
        user.setCreatedDate(Common.getCurrentUTCDate());
        user.setModifiedDate(Common.getCurrentUTCDate());
        user = userRepository.save(user);
        log.debug("user {} added to users table", registrationEntity.getEmail());

        /** adding user to lookup table */
        // lookupService.createLookupFilter(LookupFilterCode.user, user.getId(), user.getName(), true, user);
    }
}
