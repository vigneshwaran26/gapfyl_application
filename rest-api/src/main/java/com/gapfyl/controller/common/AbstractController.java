package com.gapfyl.controller.common;

import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

public class AbstractController {

    @Autowired
    UserService userService;

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            System.out.println("Anonymous user login");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String userName = userDetails.getUsername();
        return userService.fetchUserByEmail(userName);
    }
}
