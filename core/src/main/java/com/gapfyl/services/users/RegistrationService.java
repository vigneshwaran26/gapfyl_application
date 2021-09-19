package com.gapfyl.services.users;

import com.gapfyl.dto.users.RegistrationRequest;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;
import com.gapfyl.exceptions.users.UserNotFoundException;

/**
 * @author vignesh
 * Created on 13/04/21
 **/

public interface RegistrationService {

    void registerUser(RegistrationRequest registrationRequest) throws UserConflictException, AccountConflictException;

    void activateUser(String activationToken) throws UserNotFoundException,
            UserConflictException, AccountConflictException;

}
