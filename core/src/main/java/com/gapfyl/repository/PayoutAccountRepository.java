package com.gapfyl.repository;

import com.gapfyl.models.payouts.PayoutAccountEntity;
import com.gapfyl.repository.custom.ICustomPayoutAccountRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

public interface PayoutAccountRepository extends MongoRepository<PayoutAccountEntity, String>,
        ICustomPayoutAccountRepository {

    PayoutAccountEntity findOneByUserId(String userId);

}
