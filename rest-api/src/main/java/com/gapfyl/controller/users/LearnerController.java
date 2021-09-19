package com.gapfyl.controller.users;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.users.learners.LearnerRequest;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.services.users.learners.LearnerService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 13/08/21
 **/

@RestController
@RequestMapping("/api/1.0/learners")
public class LearnerController extends AbstractController {

    @Autowired
    LearnerService learnerService;

    @PostMapping("/create")
    ResponseEntity createLearner(@RequestBody LearnerRequest learnerRequest) throws NotFoundException {
        return ResponseEntity.ok().body(learnerService.createOrUpdateLearner(learnerRequest, getCurrentUser()));
    }

    @GetMapping("/fetch/user-learner-profile")
    ResponseEntity userLearnerProfile() {
        return ResponseEntity.ok().body(learnerService.fetchUserLearnerProfile(getCurrentUser()));
    }
}
