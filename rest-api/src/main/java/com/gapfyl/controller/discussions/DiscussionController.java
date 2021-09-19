package com.gapfyl.controller.discussions;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.discussions.DiscussionRequest;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.discussions.DiscussionNotFoundException;
import com.gapfyl.exceptions.discussions.DiscussionUpdateFailedException;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.discussion.DiscussionService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author vignesh
 * Created on 06/05/21
 **/

@RestController
@RequestMapping("/api/1.0/discussions")
public class DiscussionController extends AbstractController {

    @Autowired
    DiscussionService discussionService;

    @PostMapping("/create")
    ResponseEntity createDiscussion(@RequestBody @Valid DiscussionRequest discussionRequest) {
        return ResponseEntity.ok().body(discussionService.createDiscussion(discussionRequest, getCurrentUser()));
    }

    @GetMapping("/fetch/{discussionId}")
    ResponseEntity fetchDiscussion(@PathVariable("discussionId") String discussionId)
            throws DiscussionNotFoundException {
        return ResponseEntity.ok().body(discussionService.fetchDiscussion(discussionId));
    }

    @GetMapping("/fetch/{categoryType}/{categoryId}")
    ResponseEntity fetchCategoryDiscussions(@PathVariable("categoryType") CategoryType categoryType,
                                            @PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok().body(discussionService.fetchCategoryDiscussions(categoryType, categoryId));
    }

    @GetMapping("/fetch/user/{categoryType}/{categoryId}")
    ResponseEntity fetchUserDiscussions(@PathVariable(value = "categoryType") CategoryType categoryType,
                                        @PathVariable(value = "categoryId") String categoryId){
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(discussionService.fetchUserCategoryDiscussions
                (categoryType, categoryId, userEntity));
    }

    @PutMapping("/update/{discussionId}")
    ResponseEntity updateDiscussion(@PathVariable("discussionId") String discussionId,
                                    @RequestBody @Valid DiscussionRequest discussionRequest)
            throws DiscussionNotFoundException, DiscussionUpdateFailedException {
        return ResponseEntity.ok().body(discussionService.updateDiscussion(discussionId, discussionRequest, getCurrentUser()));
    }

    @DeleteMapping("/delete/{discussionId}")
    ResponseEntity deleteDiscussion(@PathVariable("discussionId") String discussionId) throws DiscussionNotFoundException {
        discussionService.deleteDiscussion(discussionId, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }
}
