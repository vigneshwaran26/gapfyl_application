package com.gapfyl.services.lookup;

import com.gapfyl.dto.lookup.LookupRequest;
import com.gapfyl.dto.lookup.LookupResponse;
import com.gapfyl.enums.common.LookupFilterCode;
import com.gapfyl.models.lookup.LookupEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.LookupRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@Slf4j
@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    LookupRepository lookupRepository;

    private LookupResponse entityToResponse(LookupEntity lookupEntity) {
        LookupResponse lookupResponse = new LookupResponse();
        lookupResponse.setLookupFilterCode(lookupEntity.getLookupFilterCode());
        lookupResponse.setLookupFilterKey(lookupEntity.getLookupFilterKey());
        lookupResponse.setLookupFilterValue(lookupEntity.getLookupFilterValue());
        lookupResponse.setLookupMapFilterKey(lookupEntity.getLookupMapFilterKey());
        lookupResponse.setLookupMapFilterValue(lookupEntity.getLookupMapFilterValue());
        lookupResponse.setIsActive(lookupEntity.isActive());
        return lookupResponse;
    }

    @Override
    public LookupEntity createLookupFilter(LookupFilterCode lookupFilterCode, String lookupKey,
                                           String lookupValue, boolean isActive, UserEntity userEntity) {
        LookupEntity lookupEntity = new LookupEntity();
        lookupEntity.setLookupFilterCode(lookupFilterCode);
        lookupEntity.setLookupFilterKey(lookupKey);
        lookupEntity.setLookupFilterValue(lookupValue);
        lookupEntity.setActive(isActive);
        return createLookupFilter(lookupEntity, userEntity);
    }

    @Override
    public LookupEntity createLookupFilter(LookupEntity reqLookupFilter, UserEntity userEntity) {
        reqLookupFilter.setCreatedDate(Common.getCurrentUTCDate());
        reqLookupFilter.setModifiedDate(Common.getCurrentUTCDate());
        reqLookupFilter.setCreatedBy(userEntity);
        reqLookupFilter.setModifiedBy(userEntity);
        return lookupRepository.save(reqLookupFilter);
    }

    @Override
    public LookupResponse createLookupFilter(LookupRequest lookupRequest, UserEntity userEntity) {
        log.info("user {} [{}] creating lookup filter {}", userEntity.getName(), userEntity.getId(), lookupRequest);
        LookupEntity lookupEntity = new LookupEntity();
        lookupEntity.setLookupFilterCode(lookupRequest.getLookupFilterCode());
        lookupEntity.setLookupFilterKey(lookupRequest.getLookupFilterKey());
        lookupEntity.setLookupFilterValue(lookupRequest.getLookupFilterValue());
        lookupEntity.setLookupMapFilterKey(lookupRequest.getLookupMapFilterKey());
        lookupEntity.setLookupMapFilterValue(lookupRequest.getLookupMapFilterValue());

        lookupEntity.setActive(true);
        lookupEntity = createLookupFilter(lookupEntity, userEntity);
        log.info("user {} [{}] created lookup filter {}", userEntity.getName(), userEntity.getId(), lookupEntity);

        return entityToResponse(lookupEntity);
    }

    @Override
    public List<LookupResponse> fetchAllLookupFilters() {
        List<LookupEntity> lookups = lookupRepository.findAll();
        return lookups.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public List<LookupResponse> fetchLookupFiltersByCode(String lookupFilterCode) {
        List<LookupEntity> lookups = lookupRepository.findAllByLookupFilterCode(lookupFilterCode);
        return lookups.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public LookupResponse updateLookupFilter(String lookupFilterCode, String lookupFilterKey,
                                            String lookupFilterValue, UserEntity userEntity) {

        log.info("user {} [{}] updating the lookup filter value code {}, key {}, value {}",
                userEntity.getName(), userEntity.getId(), lookupFilterCode, lookupFilterKey, lookupFilterValue);

        LookupEntity exLookupEntity = lookupRepository.findOneByLookupFilterCodeAndLookupFilterKey(lookupFilterCode, lookupFilterKey);
        if (exLookupEntity == null) {
            log.error("failed to delete lookup filter: lookup filter not found code {}, key {}",
                    lookupFilterCode, lookupFilterKey);
            return null;
        }

        exLookupEntity.setLookupFilterValue(lookupFilterValue);
        exLookupEntity.setModifiedDate(Common.getCurrentUTCDate());
        exLookupEntity.setModifiedBy(userEntity);
        exLookupEntity = lookupRepository.save(exLookupEntity);
        return entityToResponse(exLookupEntity);
    }

    @Override
    public void deleteLookupFilter(String lookupFilterId, UserEntity userEntity) {
        log.info("user {} [{}] deleting the lookup filter {}", userEntity.getName(), userEntity.getId(), lookupFilterId);
        lookupRepository.deleteById(lookupFilterId);
        log.info("user {} [{}] deleted the lookup filter {}", userEntity.getName(),
                userEntity.getId(), lookupFilterId);
    }

    @Override
    public void deleteLookupFilter(String lookupFilterCode, String lookupFilterKey, UserEntity userEntity) {
        log.info("user {} [{}] deleting the lookup filter code {}, key {}", userEntity.getName(),
                userEntity.getId(), lookupFilterCode, lookupFilterKey);

        LookupEntity exLookupEntity = lookupRepository
                .findOneByLookupFilterCodeAndLookupFilterKey(lookupFilterCode, lookupFilterKey);

        if (exLookupEntity == null) {
            log.error("failed to delete lookup filter: lookup filter not " +
                            "found code {}, key {}", lookupFilterCode, lookupFilterKey);
            return;
        }

        lookupRepository.delete(exLookupEntity);
        log.info("user {} [{}] deleted the lookup filter code {}, key {}", userEntity.getName(),
                userEntity.getId(), lookupFilterCode, lookupFilterKey);

    }
}
