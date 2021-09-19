package com.gapfyl.controller.users;

import com.gapfyl.dto.users.GeographicalDataRequest;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;
import com.gapfyl.services.geographical.GeoService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeographicalController {

    @Autowired
    GeoService geoService;
    @RequestMapping("/geo-data")
    ResponseEntity geoData(@RequestBody GeographicalDataRequest geodatarequest)throws UserConflictException, AccountConflictException {
        geoService.UserGeography(geodatarequest);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }
}
