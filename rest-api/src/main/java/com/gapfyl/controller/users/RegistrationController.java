package com.gapfyl.controller.users;

import com.gapfyl.dto.users.RegistrationRequest;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;
import com.gapfyl.exceptions.users.UserNotFoundException;
import com.gapfyl.services.users.RegistrationService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 13/04/21
 **/

@RestController
@RequestMapping("/api/1.0/registrations")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/user")
    ResponseEntity userRegistration(@RequestBody RegistrationRequest registrationRequest)
            throws UserConflictException, AccountConflictException {
        registrationService.registerUser(registrationRequest);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PostMapping("/activation/user/{activation_token}")
    ResponseEntity activateRegistration(@PathVariable("activation_token") String activationToken)
            throws UserNotFoundException, UserConflictException, AccountConflictException {
        registrationService.activateUser(activationToken);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }
}
