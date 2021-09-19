package com.gapfyl.services.lookup;

import com.gapfyl.dto.lookup.LookupRequest;
import com.gapfyl.dto.lookup.LookupResponse;
import com.gapfyl.enums.common.LookupFilterCode;
import com.gapfyl.models.lookup.LookupEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 21/05/21
 **/
public interface LookupService {

    LookupEntity createLookupFilter(LookupFilterCode lookupFilterCode, String lookupKey,
                                    String lookupValue, boolean isActive, UserEntity userEntity);

    LookupEntity createLookupFilter(LookupEntity reqLookupFilter, UserEntity userEntity);

    LookupResponse createLookupFilter(LookupRequest lookupRequest, UserEntity userEntity);

    List<LookupResponse> fetchAllLookupFilters();

    List<LookupResponse> fetchLookupFiltersByCode(String lookupFilterCode);

    LookupResponse updateLookupFilter(String lookupFilterCode, String lookupFilterKey, String lookupFilterValue, UserEntity userEntity);

    void deleteLookupFilter(String lookupFilterId, UserEntity userEntity);

    void deleteLookupFilter(String lookupFilterCode, String lookupFilterKey, UserEntity userEntity);
}
