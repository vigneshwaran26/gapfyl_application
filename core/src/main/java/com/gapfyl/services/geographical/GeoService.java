package com.gapfyl.services.geographical;

import com.gapfyl.dto.users.GeographicalDataRequest;
import com.gapfyl.dto.users.RegistrationRequest;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;

public interface GeoService {
    void UserGeography(GeographicalDataRequest geographicalDataRequest) throws UserConflictException, AccountConflictException;
}
