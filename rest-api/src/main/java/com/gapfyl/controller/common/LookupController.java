package com.gapfyl.controller.common;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.lookup.LookupRequest;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.lookup.LookupService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@RestController
@RequestMapping("/api/1.0/lookups")
public class LookupController extends AbstractController {

    @Autowired
    LookupService lookupService;

    @PostMapping("/create")
    ResponseEntity createLookupFilter(@RequestBody LookupRequest lookupRequest) {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(lookupService.createLookupFilter(lookupRequest, userEntity));
    }

    @GetMapping("/fetch")
    ResponseEntity allLookupFilters() {
        return ResponseEntity.ok().body(lookupService.fetchAllLookupFilters());
    }

    @GetMapping("/fetch/{lookupFilterCode}")
    ResponseEntity lookupFiltersWithCode(@PathVariable("lookupFilterCode") String lookupFilterCode) {
        return ResponseEntity.ok().body(lookupService.fetchLookupFiltersByCode(lookupFilterCode));
    }

    @PutMapping("/update/{lookupFilterCode}/{lookupFilterKey}")
    ResponseEntity updateLookupFilter(@PathVariable("lookupFilterCode") String lookupFilterCode,
                                      @PathVariable("lookupFilterKey") String lookupFilterKey,
                                      @RequestParam("lookupFilterValue") String lookupFilterValue) {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(lookupService
                .updateLookupFilter(lookupFilterCode, lookupFilterKey, lookupFilterValue, userEntity));

    }

    @DeleteMapping("/delete/{lookupFilterId}")
    ResponseEntity deleteLookupFilter(@PathVariable("lookupFilterId") String lookupFilterId) {
        UserEntity userEntity = getCurrentUser();
        lookupService.deleteLookupFilter(lookupFilterId, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @DeleteMapping("/delete/{lookupFilterCode}/{lookupFilterKey}")
    ResponseEntity deleteLookupFilter(@PathVariable("lookupFilterCode") String lookupFilterCode,
                                      @PathVariable("lookupFilterKey") String lookupFilterKey) {
        UserEntity userEntity = getCurrentUser();
        lookupService.deleteLookupFilter(lookupFilterCode, lookupFilterKey, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }
}
