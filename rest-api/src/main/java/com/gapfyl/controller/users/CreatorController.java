package com.gapfyl.controller.users;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.users.creators.CreatorRequest;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.services.users.creators.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@RestController
@RequestMapping("/api/1.0/creators")
public class CreatorController extends AbstractController {

    @Autowired
    CreatorService creatorService;

    @PostMapping("/create")
    ResponseEntity createCreator(@RequestBody CreatorRequest creatorRequest) throws NotFoundException {
        return ResponseEntity.ok().body(creatorService.createOrUpdateCreator(creatorRequest, getCurrentUser()));
    }

    @GetMapping("/fetch/user-creator-profile")
    ResponseEntity userCreatorProfile() {
        return ResponseEntity.ok().body(creatorService.fetchUserCreatorProfile(getCurrentUser()));
    }
}
