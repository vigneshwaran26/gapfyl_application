package com.gapfyl.repository;

import com.gapfyl.models.checkouts.payments.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {

    PaymentEntity findOneByPaymentId(String paymentId);
}
