package com.gapfyl.repository;

import com.gapfyl.models.lookup.LookupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

public interface LookupRepository extends MongoRepository<LookupEntity, String> {

    List<LookupEntity> findAllByLookupFilterCode(String lookupFilterCode);

    LookupEntity findOneByLookupFilterCodeAndLookupFilterKey(String lookupFilterCode, String lookupFilterKey);
}
